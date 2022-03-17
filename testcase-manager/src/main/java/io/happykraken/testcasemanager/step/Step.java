package io.happykraken.testcasemanager.step;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Step {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "step_id_seq"
    )
    @SequenceGenerator(
            name = "step_id_seq",
            initialValue = 10000,
            allocationSize = 1
    )
    private Long stepId;
    private Long testcaseId;
    private Integer stepOrder;
    private String action;
    private String expectedResult;
    private String actualResult;
    private Boolean hasBug;

}
