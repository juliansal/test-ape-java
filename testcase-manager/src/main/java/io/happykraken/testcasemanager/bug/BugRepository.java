package io.happykraken.testcasemanager.bug;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug, Long> {

    // TODO: find by title
}
