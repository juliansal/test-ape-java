package io.happykraken.testcasemanager.tcase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}
