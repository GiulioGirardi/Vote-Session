package challenge.interview.services;

import challenge.interview.entities.SubjectEntity;
import challenge.interview.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public Optional<SubjectEntity> save(SubjectEntity subject) {
        return Optional.ofNullable(subjectRepository.save(subject));
    }

    public Optional<SubjectEntity> get(String subjectId) {
        return subjectRepository.findById(subjectId);
    }
}
