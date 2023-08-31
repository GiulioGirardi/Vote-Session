package challenge.interview.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VoteDTO {
    String votesYes;
    String votesNo;
}
