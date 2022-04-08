package io.happykraken.testcasemanager.bugreport;

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

    public List<Bug> findAllBugs() {
        return bugRepository
                .findAll()
                .stream()
                .limit(1000)
                .collect(Collectors.toList());

    }

    public List<Bug> findBugsByStatus(BugStatus status) {
        return bugRepository
                .findAllByStatus(status)
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public List<Bug> findBugsByDescription(String description, BugStatus status) {
        return bugRepository
                .findAllByDescription(description, status);
    }

    public Optional<Bug> findBugById(BugId bugId) {
        return bugRepository
                .findById(bugId);
    }

    public Optional<Bug> createBugReport(Bug bug) {

        LocalDateTime timestamp = LocalDateTime.now();
        bug.setCreatedAt(timestamp);

        Bug bugCreated = bugRepository
                .save(bug);
        log.info("Saving bug report... " + bugCreated.getId());

        return Optional.of(bugCreated);
    }

    public Optional<Bug> updateBugReport(Bug bugReport, BugId bugId) {
        final Bug[] bugUpdated = { bugReport };

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

    public void deleteBugReport(BugId bugId) {
        bugRepository
                .findById(bugId)
                .ifPresentOrElse(bug -> {
                    bugRepository.delete(bug);
                }, () -> {
                    throw new IllegalStateException("This test case does not exist");
                });
    }

}
