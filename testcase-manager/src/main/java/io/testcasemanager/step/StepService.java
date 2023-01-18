package io.testcasemanager.step;

import io.testcasemanager.tcase.TCaseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class StepService {
    private final StepRepository stepRepository;
    private final TCaseRepository tCaseRepository;

    public List<Step> findAllSteps() {
        return stepRepository
                .findAll()
                .stream()
                .limit(1000)
                .collect(Collectors.toList());
    }

    public List<Step> findAllStepsByTestCase(Long caseNumber) {
        return stepRepository
                .findByTestCase(caseNumber)
                .stream()
                .limit(100)
                .collect(Collectors.toList());
    }

    public Optional<Step> createStep(Step step, Long caseNumber) {
        boolean hasTestCase = tCaseRepository.findById(caseNumber).isPresent();
        if (!hasTestCase) { throw new IllegalStateException("This test case does not exist"); }

        Long stepId = stepRepository
                .save(step)
                .getStepId();
        log.info("Add a step to test case " + caseNumber);

        return stepRepository
                .findById(stepId);
    }
    
    public Optional<Step> editStep(Step step, Long caseNumber) {
        boolean hasTestCase = tCaseRepository.findById(caseNumber).isPresent();
        if (!hasTestCase) { throw new IllegalStateException("This test case does not exist"); }

        stepRepository
                .findById(step.getStepId())
                .ifPresent(s -> {
                    s.setAction(step.getAction());
                    s.setActualResult(step.getActualResult());
                    s.setStepOrder(step.getStepOrder());
                    s.setHasBug(step.getHasBug());
                    s.setStepId(step.getStepId());
                    
                    stepRepository
                            .save(s);

                    log.info("Update step " + s.getStepId());
                });

        return stepRepository
                .findById(step.getStepId());
        
    }

    public void deleteStep(Long stepId, Long caseNumber) {
        boolean hasTestCase = tCaseRepository.findById(caseNumber).isPresent();
        if (!hasTestCase) { throw new IllegalStateException("This test case does not exist"); }

        stepRepository
                .deleteById(stepId);
    }
}
