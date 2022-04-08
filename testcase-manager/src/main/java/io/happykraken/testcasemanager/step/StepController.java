package io.happykraken.testcasemanager.step;

import io.happykraken.testcasemanager.tcase.TCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/steps")
@AllArgsConstructor
public class StepController {
    private final StepService stepService;

    @GetMapping(path = "{caseNumber}")
    public List<Step> getStepsFromTestCase(@PathVariable("caseNumber") String caseNumber) {
        log.info("Retrieve all steps in this test case " + caseNumber);
        return stepService
                .findAllStepsByTestCase(Long.valueOf(caseNumber));
    }

    @PostMapping
    public Optional<Step> addStepInTestCase(@RequestBody Step step) {
        Long caseNumber = step.getTestCaseNumber();

        log.info("Add a new step in this test case " + caseNumber);

        return stepService
                .createStep(step, caseNumber);
    }

    @PutMapping
    public Optional<Step> updateStepInTestCase(@RequestBody Step step) {
        Long caseNumber = step.getTestCaseNumber();

        log.info("Update step in this test case " + caseNumber);

        return stepService
                .editStep(step, caseNumber);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStepInTestCase(@RequestBody Step step) {
        Long caseNumber = step.getTestCaseNumber();
        Long stepId = step.getStepId();

        log.info("Delete step in this test case " + caseNumber);

        stepService
                .deleteStep(stepId, caseNumber);
        
        return ResponseEntity
                .ok()
                .body("Removing step: " + step.getStepOrder());
    }


}
