package challenge.interview.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document
public class SessionEntity {
    @Id
    String id;
    SubjectEntity subject;
    LocalDateTime closedTime;
}
