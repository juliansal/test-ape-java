package io.happykraken.testcasemanager.testcase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/test-cases")
@AllArgsConstructor
public class TestCaseController {
    private final TestCaseService testCaseService;

    @GetMapping
    public List<TestCase> getTestCases() {
        log.info("Retrieve all test cases");
        return testCaseService
                .findTestCases();
    }

    @GetMapping
    public List<TestCase> getTestCases(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "author", required = false, defaultValue = "") String authorEmail,
            @RequestParam(name = "createdAt", required = false, defaultValue = "") String createdAt,
            @RequestParam(name = "status", required = false, defaultValue = "") String status
    ) {
        log.info("Retrieving test case by {} {} {} {}", title, authorEmail, createdAt, status);
        return testCaseService
                .findTestCases(title, authorEmail, createdAt, status);
    }

    @GetMapping(path = "{caseNumber}")
    public Optional<TestCase> getTestCaseByCaseNumber(@PathVariable("caseNumber") String caseNumber) {
        log.info("Retrieving test case {}", caseNumber);
        return testCaseService
                .findTestCaseById(caseNumber);
    }
}
