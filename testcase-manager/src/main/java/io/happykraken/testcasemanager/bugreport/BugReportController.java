package io.happykraken.testcasemanager.bugreport;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/bug-reports")
@AllArgsConstructor
public class BugReportController {
    private BugReportService bugReportService;

    @GetMapping("all")
    public List<Bug> getAllBugReports() {
        log.info("Getting all bug reports");
        return bugReportService
                .findAllBugs();
    }

    @GetMapping
    @ResponseBody
    public List<Bug> getBugReportsBy(
            @RequestParam(name = "description", required = false, defaultValue = "") String description,
            @RequestParam(name = "status", required = false, defaultValue = "ACTIVE") String status) {
        log.info("Getting bug reports with matching description");

        return bugReportService
                .findBugsByParams(description, status);
    }

    @GetMapping("bug")
    @ResponseBody
    public Optional<Bug> getBugReport(@RequestParam(name = "bug") String bugNumber,
                                      @RequestParam(name = "testcase") String testCaseNumber) {
        log.info("Getting bug report {}", bugNumber);

        return bugReportService
                .findBugById(Long.valueOf(bugNumber), Long.valueOf(testCaseNumber));
    }

    @PostMapping
    @ResponseBody
    public Optional<Bug> createBugReport(@RequestBody Bug bug) {
        Bug bugBuilder = Bug
                .builder()
                .bugStatus(BugStatus.ACTIVE)
                .authorEmail(bug.getAuthorEmail())
                .description(bug.getDescription())
                .expectedOutcome(bug.getExpectedOutcome())
                .testcaseId(bug.getTestcaseId())
                .stepId(bug.getStepId())
                .build();
        log.info("Creating bug report");

        return bugReportService
                .createBugReport(bugBuilder);
    }

    @PutMapping
    @ResponseBody
    public Optional<Bug> updateBugReport(@RequestBody Bug bug) {
        log.info("Updating bug report {}", bug.getId());

        return bugReportService
                .updateBugReport(bug);
    }

    @DeleteMapping
    @ResponseBody
    public void deletedBugReport(@RequestBody Bug bug) {
        log.info("Deleting bug report {}", bug.getId());

        bugReportService
                .deleteBugReport(bug);
    }

}
