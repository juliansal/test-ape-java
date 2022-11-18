package io.testcasemanager.tcase;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class TCase {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "case_number_seq"
    )
    @SequenceGenerator(
            name = "case_number_seq",
            initialValue = 10000,
            allocationSize = 1
    )
    private Long caseNumber;
    @NotBlank
    @Column(nullable = false)
    private String title;
    private String description;
    private String authorEmail;
    private String preConditions;
    private LocalDateTime createdAt;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TCase tCase = (TCase) o;
        return caseNumber != null && Objects.equals(caseNumber, tCase.caseNumber);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
