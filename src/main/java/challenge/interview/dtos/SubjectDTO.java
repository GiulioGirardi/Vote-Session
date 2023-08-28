package challenge.interview.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubjectDTO {
    String id;
    String name;
    String title;
    String description;
}
