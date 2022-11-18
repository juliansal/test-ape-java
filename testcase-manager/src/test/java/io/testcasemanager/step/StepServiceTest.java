package io.testcasemanager.step;

import io.testcasemanager.tcase.TCaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Step Service Test")
class StepServiceTest {
    private StepRepository stepRepository;
    private TCaseRepository tCaseRepository;

    private StepService underTest;

    @BeforeEach
    void setup() {
        stepRepository = Mockito.mock(StepRepository.class);
        tCaseRepository = Mockito.mock(TCaseRepository.class);
        underTest = new StepService(stepRepository, tCaseRepository);
    }

    @Test
    @DisplayName("Should find all steps")
    void shouldFindAllSteps() {
        // Given
        Step step1 = Step
                .builder()
                .stepId(Long.valueOf("20000"))
                .build();

        given(stepRepository.findAll())
                .willReturn(List.of(step1, step1));
        // When
        List<Step> results = underTest
                .findAllSteps();
        // Then
        assertThat(results.size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("Should find all steps belonging to test case")
    void shouldFindAllStepsBelongingToTestCase() {
        // Given
        Step step1 = Step
                .builder()
                .testCaseNumber(Long.valueOf("1000"))
                .action("First Step")
                .build();
        Step step2 = Step
                .builder()
                .testCaseNumber(Long.valueOf("1000"))
                .action("Second Step")
                .build();
        given(stepRepository.findByTestCase(Long.valueOf("1000")))
                .willReturn(List.of(step1, step2));
        // When
        List<Step> results = underTest
                .findAllStepsByTestCase(Long.valueOf("1000"));
        // Then
        assertThat(results.size())
                .isEqualTo(2);

        results = underTest
                .findAllStepsByTestCase(Long.valueOf("1002"));
        // Then
        assertThat(results.size())
                .isEqualTo(0);
    }
}