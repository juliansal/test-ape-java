package io.happykraken.testcasemanager.bugreport;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bug {

    @Id
    @SequenceGenerator(
            name = "bug_id_sequence",
            sequenceName = "step_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bug_id_sequence"
    )
    private Long id;
    private Long stepId;
    private Long testcaseId;
    private String authorEmail; // the email address
    private String description;
    private String expectedOutcome;
    private LocalDateTime createdAt;

}
