package io.happykraken.testcasemanager.step;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
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
    private Long testCaseNumber;
    private Integer stepOrder;
    private String action;
    private String expectedResult;
    private String actualResult;
    private Boolean hasBug;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Step step = (Step) o;
        return stepId != null && Objects.equals(stepId, step.stepId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
