package io.happykraken.testcasemanager.testcase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, String> {

    // TODO: find by title
    @Query(value = "SELECT * FROM testcase" +
            "WHERE title LIKE '%:title%'" +
            "AND authorEmail LIKE '%:authorEmail%'" +
            "AND createdAt LIKE '%:createdAt%'" +
            "AND status LIKE '%:status%'")
    List<TestCase> findAllByParams(@Param("title") String title,
                                   @Param("authorEmail") String authorEmail,
                                   @Param("createdAt") String createdAt,
                                   @Param("status") String status);
}

