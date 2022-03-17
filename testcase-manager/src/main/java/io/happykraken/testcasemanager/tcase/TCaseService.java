package io.happykraken.testcasemanager.tcase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TCaseService {
    private final TCaseRepository tCaseRepository;

    public List<TCase> findTestCases() {

        return tCaseRepository
                .findAll()
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public List<TCase> findTestCases(String title, String authorEmail, String createdAt, String status) {
        return tCaseRepository
                .findAllByParams(title, authorEmail, createdAt, status)
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public Optional<TCase> findTestCaseById(Long caseNumber) {
        return tCaseRepository
                .findById(caseNumber);
    }

    public void createTestCase(TCase testcase) {
        tCaseRepository
                .save(testcase);
    }
}
