package io.happykraken.testcasemanager.bugreport;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends CrudRepository<Bug, Long> {

    // TODO: find by title
}
