package challenge.interview.repositories;

import challenge.interview.entities.SessionEntity;
import challenge.interview.entities.VoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends MongoRepository<VoteEntity, String> {
    List<VoteEntity> findAllBySession(SessionEntity sessionEntity);
}
