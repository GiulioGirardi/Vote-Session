package challenge.interview.services;

import challenge.interview.entities.SessionEntity;
import challenge.interview.entities.SubjectEntity;
import challenge.interview.exceptions.SessionTimeException;
import challenge.interview.exceptions.SubjectNotFoundException;
import challenge.interview.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionService {

    private static final Long TIME_DEFAULT = 1L;

    @Autowired
    SessionRepository sessionRepository;

    public Optional<SessionEntity> save(SessionEntity session) {
        return Optional.ofNullable(sessionRepository.save(session));
    }


    public Optional<SessionEntity> get(String sessionId) {
        return sessionRepository.findById(sessionId);
    }


    public ResponseEntity<SessionEntity> startSession(Long minutesOpened, SubjectEntity subjectEntity) {
        if (minutesOpened == null) {
            minutesOpened = TIME_DEFAULT;
        }

        SessionEntity sessionEntity = SessionEntity.builder()
                .subject(subjectEntity)
                .closedTime(LocalDateTime.now().plusMinutes(minutesOpened))
                .build();

        Optional<SessionEntity> optionalSessionEntity = save(sessionEntity);

        if (optionalSessionEntity.isPresent()) {
            return new ResponseEntity<>(optionalSessionEntity.get(), HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void validatePostSessionRequest(Optional<SubjectEntity> optionalSubjectEntity, Long minutesOpened) throws SubjectNotFoundException, SessionTimeException {
        if (!optionalSubjectEntity.isPresent()) {
            throw new SubjectNotFoundException();
        }

        if (ObjectUtils.isEmpty(minutesOpened) || minutesOpened < TIME_DEFAULT) {
            throw new SessionTimeException();

        }
    }
}
