package io.happykraken.testcasemanager.tcase;

import io.happykraken.testcasemanager.utils.TestCaseValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class TCaseService {
    private final TCaseRepository tCaseRepository;
    private final TestCaseValidator testCaseValidator;
    private static final List<Status> ACCEPTED_STATUS = List.of(Status.ACTIVE, Status.PENDING, Status.INACTIVE);

    public List<TCase> findAllTestCases() {

        return tCaseRepository
                .findAll()
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public List<TCase> findAllTestCasesByStatus(Status status) {
        return tCaseRepository
                .findAllByStatus(String.valueOf(status));
    }

    public List<TCase> findTestCases(String title, String authorEmail, String status) {
        Status validStatus;
        try {
            if (status.isEmpty() || ACCEPTED_STATUS.contains(Status.valueOf(status))) {
                validStatus = Status.valueOf(status);
            } else {
                validStatus = Status.ACTIVE;
            }
        } catch (IllegalArgumentException e) {
            log.info("Status provided was not accepted. Changing to ACTIVE");
            validStatus = Status.ACTIVE;
        }

        return tCaseRepository
                .findAllByParams(title, authorEmail, validStatus)
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public Optional<TCase> findTestCaseById(Long caseNumber) {
        return tCaseRepository
                .findById(caseNumber);
    }

    public Optional<TCase> createTestCase(TCase testcase) {
        if (!testCaseValidator.test(testcase.getTitle())) {
            throw new IllegalStateException(String.format("The test case title was empty"));
        }
        LocalDateTime timestamp = LocalDateTime.now();
        testcase.setCreatedAt(timestamp);

        Long caseNumber = tCaseRepository
                .save(testcase)
                .getCaseNumber();
        log.info("Saving test case... " + caseNumber);

        return tCaseRepository.findById(caseNumber);
    }

    public void deactivateTestCase(Long caseNumber) {
        tCaseRepository
                .findById(caseNumber)
                .ifPresent(tCase -> {
                    tCase.setStatus(Status.INACTIVE);
                    tCaseRepository.save(tCase);
                    log.info("Setting test case to inactive... " + caseNumber);
                });
    }
}
