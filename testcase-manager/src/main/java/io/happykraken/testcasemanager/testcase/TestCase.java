package io.happykraken.testcasemanager.testcase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestCase {

    @Id
    @SequenceGenerator(
            name = "testcase_id_sequence",
            sequenceName = "testcase_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "testcase_id_sequence"
    )
    private Long id;
    private String title;
    private String description;
    private String authorEmail;
    private String preConditions;
    private LocalDateTime createdAt;
    private String status;

}
