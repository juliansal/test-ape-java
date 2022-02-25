package io.happykraken.testcasemanager.testcase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestCase {

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
    private String caseNumber;
    private String title;
    private String description;
    private String authorEmail;
    private String preConditions;
    private LocalDateTime createdAt;
    private String status;

}
