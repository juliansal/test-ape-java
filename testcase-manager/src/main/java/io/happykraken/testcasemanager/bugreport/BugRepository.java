package io.happykraken.testcasemanager.bugreport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug, BugId> {

    // TODO: find by status, title
    @Query(value = "SELECT * FROM bug " +
            "WHERE bug_status = :bugStatus",
            nativeQuery = true
    )
    List<Bug> findAllByStatus(@Param("status") BugStatus bugStatus);

    @Query(value = "SELECT * FROM bug " +
            "WHERE description LIKE %description% " +
            "AND bug_status = :bugStatus",
            nativeQuery = true
    )
    List<Bug> findAllByDescription(@Param("description") String description,
                                    @Param("status") BugStatus bugStatus);

}
