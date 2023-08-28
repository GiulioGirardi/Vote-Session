package challenge.interview.repositories;

import challenge.interview.entities.VoteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<VoteEntity, String> {
}
