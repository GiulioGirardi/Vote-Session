package challenge.interview.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document
public class VoteEntity {
    @Id
    String id;
    String vote;
    String associateId;
    SessionEntity session;
}
