package challenge.interview.services;

import challenge.interview.entities.SessionEntity;
import challenge.interview.entities.SubjectEntity;
import challenge.interview.entities.VoteEntity;
import challenge.interview.repositories.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    VoteRepository voteRepository;

    @InjectMocks
    VoteService voteService;


    @Test
    void shouldSelectVotesPerSession() {
        List<VoteEntity> expectedListVoteEntity = Collections.singletonList(buildVoteEntity());

        given(voteService.selectVotesPerSession("1")).willReturn(expectedListVoteEntity);
        List<VoteEntity> resultListVoteEntity = voteService.selectVotesPerSession("1");

        assertThat(expectedListVoteEntity).isEqualTo(resultListVoteEntity);
    }

    @Test
    void shouldReturnEmptyListOfVotesPerSession() {
        List<VoteEntity> resultListVoteEntity = voteService.selectVotesPerSession("0");
        assertThat(resultListVoteEntity).isEmpty();
    }

    @Test
    void associateAlreadyVote() {
        SessionEntity sessionEntity = buildSessionEntity();
        List<VoteEntity> expectedListVoteEntity = Collections.singletonList(buildVoteEntity());

        String associateId = "321";
        lenient().when(voteRepository.findAllBySession(sessionEntity)).thenReturn(expectedListVoteEntity);

        Boolean resultAlreadyVote = voteService.validateAssociateAlreadyVote(sessionEntity, associateId);
        assertThat(resultAlreadyVote).isTrue();
    }

    @Test
    void associateAlreadyNotVote() {
        SessionEntity sessionEntity = buildSessionEntity();
        List<VoteEntity> expectedListVoteEntity = Collections.emptyList();

        String associateId = "321";
        lenient().when(voteRepository.findAllBySession(sessionEntity)).thenReturn(expectedListVoteEntity);

        Boolean resultAlreadyVote = voteService.validateAssociateAlreadyVote(sessionEntity, associateId);
        assertThat(resultAlreadyVote).isFalse();
    }

    private VoteEntity buildVoteEntity() {
        return VoteEntity.builder()
                .id("123")
                .vote("SIM")
                .associateId("321")
                .session(buildSessionEntity())
                .build();
    }

    private SessionEntity buildSessionEntity() {
        return SessionEntity.builder()
                .id("1")
                .subject(buildSubjectEntity())
                .build();
    }

    private SubjectEntity buildSubjectEntity() {
        return SubjectEntity.builder()
                .id("123")
                .name("Test")
                .title("Unit Test")
                .description("Unit test execution")
                .build();
    }
}