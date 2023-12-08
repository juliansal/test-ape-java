package io.testcasemanager.bugreport;

import io.testcasemanager.step.Step;
import io.testcasemanager.step.StepRepository;
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
public class BugReportService {
    private BugRepository bugRepository;
    private static final List<BugStatus> BUG_STATUSES = List.of(BugStatus.ACTIVE, BugStatus.CANCELLED, BugStatus.RESOLVED);

    private BugStatus getValidBugStatus(String status) {
        BugStatus validStatus;

        try {
            if (status.isEmpty() || BUG_STATUSES.contains(BugStatus.valueOf(status))) {
                validStatus = BugStatus.valueOf(status);
            } else {
                validStatus = BugStatus.ACTIVE;
            }
        } catch (IllegalArgumentException e) {
            log.info("Status provided was not accepted. Changing to ACTIVE");
            validStatus = BugStatus.ACTIVE;
        }

        return validStatus;
    }

    public List<Bug> findAllBugs() {
        return bugRepository
                .findAll()
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public List<Bug> findBugsByParams(String description, String status) {
        BugStatus validBugStatus = getValidBugStatus(status);

        return bugRepository
                .findAllBy(description, validBugStatus)
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public List<Bug> findBugsByTestcase(Long testCaseNumber) {

        return bugRepository
                .findAllByTestcaseId(testCaseNumber)
                .stream()
                .filter(bug -> bug.getBugStatus().equals(BugStatus.ACTIVE))
                .collect(Collectors.toList());
    }

    public Optional<Bug> findBugById(Long bugNumber, Long testCaseNumber) {
        BugId bugId = new BugId(bugNumber, testCaseNumber);

        return bugRepository
                .findById(bugId);
    }

    public Optional<Bug> createBugReport(Bug bug) {
        // TODO: add validation for Bug description
        LocalDateTime timestamp = LocalDateTime.now();
        bug.setCreatedAt(timestamp);

        Bug bugCreated = bugRepository
                    .save(bug);
        log.info("Saving bug report... " + bugCreated.getId());

        return Optional.of(bugCreated);
    }

    public Optional<Bug> updateBugReport(Bug bugReport) {
        final Bug[] bugUpdated = { bugReport };
        BugId bugId = new BugId(bugReport.getId(), bugReport.getTestcaseId());

        bugRepository
                .findById(bugId)
                .ifPresent(bug -> {
                    bug.setBugStatus(bugReport.getBugStatus());
                    bug.setDescription(bugReport.getDescription());
                    bug.setExpectedOutcome(bugReport.getExpectedOutcome());
                    bug.setAuthorEmail(bugReport.getAuthorEmail());

                    bugUpdated[0] = bugRepository.save(bug);
                    log.info("Update bug report " + bug.getId());
                });

        return Optional.of(bugUpdated[0]);
    }

    public void deleteBugReport(Bug bugReport) {
        BugId bugId = new BugId(bugReport.getId(), bugReport.getTestcaseId());

        bugRepository
                .findById(bugId)
                .ifPresentOrElse(bug -> {
                    bugRepository.delete(bug);
                }, () -> {
                    throw new IllegalStateException("This test case does not exist");
                });
    }
}
