package challenge.interview.controllers;

import challenge.interview.dtos.SubjectDTO;
import challenge.interview.dtos.VoteDTO;
import challenge.interview.entities.SessionEntity;
import challenge.interview.entities.SubjectEntity;
import challenge.interview.entities.VoteEntity;
import challenge.interview.exceptions.SessionTimeException;
import challenge.interview.exceptions.SubjectNotFoundException;
import challenge.interview.services.SessionService;
import challenge.interview.services.SubjectService;
import challenge.interview.services.VoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("vote-session/v1")
public class VoteSessionController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private VoteService voteService;


    @PostMapping("/subject")
    public ResponseEntity<SubjectEntity> saveSubject(@RequestBody SubjectDTO subjectDTO) {
        SubjectEntity subjectEntity = SubjectEntity.builder().build();

        BeanUtils.copyProperties(subjectDTO, subjectEntity);
        Optional<SubjectEntity> optionalSubjectEntity = subjectService.save(subjectEntity);

        if (optionalSubjectEntity.isPresent()) {
            return new ResponseEntity<>(optionalSubjectEntity.get(), HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/session/subject/{subject_id}/time/{minutes_opened}")
    public ResponseEntity<SessionEntity> saveSession(@PathVariable(value = "minutes_opened")
                                                     @DefaultValue(value = "1") Long minutesOpened,
                                                     @PathVariable(value = "subject_id") String subjectId
    ) throws SubjectNotFoundException, SessionTimeException {

        Optional<SubjectEntity> optionalSubjectEntity = subjectService.get(subjectId);
        sessionService.validatePostSessionRequest(optionalSubjectEntity, minutesOpened);

        return sessionService.startSession(minutesOpened, optionalSubjectEntity.get());
    }

    @PostMapping("/session/{session_id}/subject/associate/{associate_id}/vote/{associate_vote}")
    public ResponseEntity<VoteEntity> saveVote(@PathVariable(value = "session_id") String sessionId,
                                               @PathVariable(value = "associate_id") String associateId,
                                               @PathVariable(value = "associate_vote") String associateVote
    ) {

        Optional<SessionEntity> optionalSessionEntity = sessionService.get(sessionId);

        if (voteService.validateAssociateAlreadyVote(optionalSessionEntity.get(), associateId).equals(false)) {
            return voteService.voteSession(associateVote, associateId, optionalSessionEntity.get());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/session/{session_id}/subject/vote/result")
    public ResponseEntity<VoteDTO> resultVotes(@PathVariable(value = "session_id") String sessionId) {
        return voteService.getVotes(sessionId);
    }
}
