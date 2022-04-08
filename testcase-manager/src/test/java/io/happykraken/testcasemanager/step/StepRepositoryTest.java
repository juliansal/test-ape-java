package io.happykraken.testcasemanager.step;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"}
)
class StepRepositoryTest {
    @Autowired
    private StepRepository underTest;

    @Test
    @DisplayName("Should find steps when finding by test case number")
    void shouldFindStepsWhenFindingByCaseNumber() {
        // Given
        Step step = Step
                .builder()
                .testCaseNumber(Long.valueOf("1000"))
                .stepOrder(0)
                .action("First Step")
                .build();
        underTest.save(step);
        // When
        List<Step> results = underTest
                .findByTestCase(Long.valueOf("1000"));

        assertThat(results)
                .isNotEmpty()
                .size()
                .isEqualTo(1);

        assertThat(results)
                .hasOnlyElementsOfType(Step.class)
                .element(0)
                .hasFieldOrPropertyWithValue("action", "First Step")
                .hasFieldOrPropertyWithValue("testCaseNumber", Long.valueOf("1000"));
    }

}