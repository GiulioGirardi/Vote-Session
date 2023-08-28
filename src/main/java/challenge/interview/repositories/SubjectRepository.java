package challenge.interview.repositories;

import challenge.interview.entities.SubjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends MongoRepository<SubjectEntity, String> {
}

