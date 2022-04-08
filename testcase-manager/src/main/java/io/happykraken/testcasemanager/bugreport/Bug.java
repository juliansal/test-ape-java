package io.happykraken.testcasemanager.bugreport;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(BugId.class)
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
    @Id
    private Long testcaseId;
    private String authorEmail; // the email address
    private String description;
    private String expectedOutcome;
    private LocalDateTime createdAt;
    @NotNull
    @Enumerated(EnumType.STRING)
    private BugStatus bugStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bug bug = (Bug) o;
        return id != null && Objects.equals(id, bug.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
