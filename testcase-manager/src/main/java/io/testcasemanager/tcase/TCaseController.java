package io.testcasemanager.tcase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/test-cases")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TCaseController {
    private final TCaseService tCaseService;

    @GetMapping
    public List<TCase> getActiveTestCases() {
        log.info("Retrieve active test cases");
        return tCaseService
                .findAllTestCasesByStatus(Status.ACTIVE);
    }

    @GetMapping("all")
    public List<TCase> getTestCases() {
        log.info("Retrieve all test cases");
        return tCaseService
                .findAllTestCases();
    }

    @GetMapping("search")
    @ResponseBody
    public List<TCase> getTestCasesBy(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "author", required = false, defaultValue = "") String authorEmail,
            @RequestParam(name = "status", required = false, defaultValue = "") String status
    ) {
        log.info("Retrieving test case by {} {} {}", title, authorEmail, status);
        return tCaseService
                .findTestCases(title, authorEmail, status);
    }

    @GetMapping(path = "{caseNumber}")
    public Optional<TCase> getTestCaseByCaseNumber(@PathVariable("caseNumber") String caseNumber) {
        log.info("Retrieving test case {}", caseNumber);

        return tCaseService
                .findTestCaseById(Long.valueOf(caseNumber));
    }

    @PostMapping("new")
    @ResponseBody
    public Optional<TCase> createTestCase(@RequestBody TCase tCase) {
        TCase tCaseBuilder = TCase
                .builder()
                .title(tCase.getTitle())
                .description(tCase.getDescription())
                .authorEmail(tCase.getAuthorEmail())
                .preConditions(tCase.getPreConditions())
                .status(Status.ACTIVE)
                .build();

        return tCaseService.createTestCase(tCaseBuilder);
    }

    @DeleteMapping(path = "{caseNumber}")
    public ResponseEntity<String> deleteTestCase(@PathVariable("caseNumber") String caseNumber) {
        log.info("Removing test case {}", caseNumber);

        tCaseService.deactivateTestCase(Long.valueOf(caseNumber));

        return ResponseEntity.ok().body("Removing test case: " + caseNumber);
    }
}
