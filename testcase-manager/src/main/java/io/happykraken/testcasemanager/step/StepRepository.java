package io.happykraken.testcasemanager.step;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {

    // TODO: find by associated testcase
    @Query(value = "SELECT * FROM step " +
            "WHERE test_case_number = :caseNumber",
            nativeQuery = true
    )
    List<Step> findByTestCase(@Param("caseNumber") Long caseNumber);
}
