package io.happykraken.testcasemanager.step;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {

    @Query(value = "SELECT * FROM step " +
            "WHERE test_case_number = :caseNumber",
            nativeQuery = true
    )
    @OrderBy("step_order ASC")
    List<Step> findByTestCase(@Param("caseNumber") Long caseNumber);
}
