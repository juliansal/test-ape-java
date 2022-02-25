package io.happykraken.testcasemanager.testcase;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;

    public List<TestCase> findTestCases() {
        return testCaseRepository
                .findAll()
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public List<TestCase> findTestCases(String title, String authorEmail, String createdAt, String status) {
        return testCaseRepository
                .findAllByParams(title, authorEmail, createdAt, status)
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public Optional<TestCase> findTestCaseById(String caseNumber) {
        return testCaseRepository
                .findById(caseNumber);
    }
}
