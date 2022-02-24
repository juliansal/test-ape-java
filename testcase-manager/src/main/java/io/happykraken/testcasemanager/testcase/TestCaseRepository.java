package io.happykraken.testcasemanager.testcase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    // TODO: find by title
}
