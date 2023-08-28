package challenge.interview.services;

import challenge.interview.dtos.VoteDTO;
import challenge.interview.entities.SessionEntity;
import challenge.interview.entities.VoteEntity;
import challenge.interview.repositories.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoteService {

    VoteRepository voteRepository;

    public Optional<VoteEntity> save(VoteEntity vote) {
        return Optional.ofNullable(voteRepository.save(vote));
    }

    public List<VoteEntity> findAll() {
        return voteRepository.findAll();
    }

    public ResponseEntity<VoteDTO> getVotes(String sessionId) {
        List<VoteEntity> selectedVotes = selectVotesPerSession(sessionId);
        Long totalVotesYes = 0L;
        Long totalVotesNo = 0L;

        for (VoteEntity vote : selectedVotes) {
            if (vote.getVote().equals(true)) {
                totalVotesYes++;

            } else {
                totalVotesNo++;

            }
        }
        VoteDTO voteDTO = VoteDTO.builder()
                .votesYes(String.valueOf(totalVotesYes))
                .votesNo(String.valueOf(totalVotesNo))
                .build();

        return new ResponseEntity<>(voteDTO, HttpStatus.OK);
    }

    public List<VoteEntity> selectVotesPerSession(String sessionId) {
        List<VoteEntity> listVoteEntity = findAll();

        return listVoteEntity
                .stream()
                .filter(e -> e.getSession().getId().equals(sessionId))
                .collect(Collectors.toList());
    }

    public ResponseEntity<VoteEntity> voteSession(Boolean associateVote, Long associateId, SessionEntity sessionEntity) {
        VoteEntity voteEntity = VoteEntity.builder()
                .vote(associateVote)
                .associateId(associateId)
                .session(sessionEntity)
                .build();

        Optional<VoteEntity> optionalVoteEntity = save(voteEntity);

        if (optionalVoteEntity.isPresent()) {
            return new ResponseEntity<>(optionalVoteEntity.get(), HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public Boolean validateAssociateAlreadyVote(SessionEntity optionalSessionEntity, Long associateId) {
        List<VoteEntity> listVoteEntity = findAll();

        return listVoteEntity
                .stream()
                .filter(e -> e.getSession().equals(optionalSessionEntity))
                .anyMatch(voteEntity -> voteEntity.getAssociateId().equals(associateId));
    }
}
