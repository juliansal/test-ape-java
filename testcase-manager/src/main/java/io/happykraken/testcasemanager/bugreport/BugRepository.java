package io.happykraken.testcasemanager.bugreport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug, BugId> {

    @Query(value = "SELECT * FROM bug " +
            "WHERE bug_status LIKE %:status% " +
            "AND description LIKE %:description%",
            nativeQuery = true
    )
    List<Bug> findAllBy(@Param("description") String description,
                        @Param("status") BugStatus status);
}
