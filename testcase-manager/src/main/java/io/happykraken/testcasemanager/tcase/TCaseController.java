package io.happykraken.testcasemanager.tcase;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/test-cases")
@AllArgsConstructor
public class TCaseController {
    private final TCaseService tCaseService;

    @GetMapping
    public List<TCase> getTestCases() {
        log.info("Retrieve all test cases");
        return tCaseService
                .findTestCases();
    }

    @GetMapping("search")
    @ResponseBody
    public List<TCase> getTestCasesBy(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "author", required = false, defaultValue = "") String authorEmail,
            @RequestParam(name = "createdAt", required = false, defaultValue = "") String createdAt,
            @RequestParam(name = "status", required = false, defaultValue = "") String status
    ) {
        log.info("Retrieving test case by {} {} {} {}", title, authorEmail, createdAt, status);
        return tCaseService
                .findTestCases(title, authorEmail, createdAt, status);
    }

    @GetMapping(path = "{caseNumber}")
    public Optional<TCase> getTestCaseByCaseNumber(@PathVariable("caseNumber") String caseNumber) {
        log.info("Retrieving test case {}", caseNumber);

        return tCaseService
                .findTestCaseById(Long.valueOf(caseNumber));
    }

    @PostMapping("new")
    public void createTestCase(
            @RequestParam(name = "title", required = false, defaultValue = "") String title,
            @RequestParam(name = "description", required = false, defaultValue = "") String description,
            @RequestParam(name = "author", required = false, defaultValue = "") String authorEmail,
            @RequestParam(name = "preConditions", required = false, defaultValue = "") String preConditions,
            @RequestParam(name = "status", required = false, defaultValue = "") String status
    ) {
        TCase tCaseBuilder = TCase
                .builder()
                .title(title)
                .description(description)
                .authorEmail(authorEmail)
                .preConditions(preConditions)
                .status(status)
                .build();

        tCaseService.createTestCase(tCaseBuilder);
    }
}
