package io.happykraken.testcasemanager.step;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class Step {

    @Id
    @SequenceGenerator(
            name = "step_id_sequence",
            sequenceName = "step_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "step_id_sequence"
    )
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private Long testcaseId;
    private Integer order;
    private String action;
    private String expectedResult;
    private String actualResult;
    private Boolean hasBug;

    public Step(Long id, Long testcaseId, Integer order, String action, String expectedResult, String actualResult, Boolean hasBug) {
        this.id = id;
        this.testcaseId = testcaseId;
        this.order = order;
        this.action = action;
        this.expectedResult = expectedResult;
        this.actualResult = actualResult;
        this.hasBug = hasBug;
    }

    public Step() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestcaseId() {
        return testcaseId;
    }

    public void setTestcaseId(Long testcaseId) {
        this.testcaseId = testcaseId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public Boolean getHasBug() {
        return hasBug;
    }

    public void setHasBug(Boolean hasBug) {
        this.hasBug = hasBug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return id.equals(step.id) && testcaseId.equals(step.testcaseId) && order.equals(step.order) && Objects.equals(action, step.action) && Objects.equals(expectedResult, step.expectedResult) && Objects.equals(actualResult, step.actualResult) && Objects.equals(hasBug, step.hasBug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, testcaseId, order, action, expectedResult, actualResult, hasBug);
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", testcaseId=" + testcaseId +
                ", order=" + order +
                ", action='" + action + '\'' +
                ", expectedResult='" + expectedResult + '\'' +
                ", actualResult='" + actualResult + '\'' +
                ", hasBug=" + hasBug +
                '}';
    }
}
