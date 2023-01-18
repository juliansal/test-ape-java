package io.testcasemanager.tcase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TCaseRepository extends JpaRepository<TCase, Long> {

    // TODO: find by title
    @Query(value = "SELECT * FROM tcase " +
            "WHERE title LIKE %:title% " +
            "AND author_email LIKE %:authorEmail% " +
            "AND status LIKE %:status%",
            nativeQuery = true
    )
    List<TCase> findAllByParams(@Param("title") String title,
                                @Param("authorEmail") String authorEmail,
                                @Param("status") Status status);

    @Query(value = "SELECT * FROM tcase " +
            "WHERE status = :status",
            nativeQuery = true
    )
    List<TCase> findAllByStatus(@Param("status") String status);
}

