package io.testcasemanager.tcase;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(
        properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"}
)
@DisplayName("Testcase Repository Test")
class TCaseRepositoryTest {

    @Autowired
    private TCaseRepository underTest;

    @Test
    @DisplayName("Should return when finding by title only")
    void shouldReturnWhenFindingByTitleOnly() {
        // Given
        TCase tCase = new TCase(
                Long.valueOf("20010"),
                "First Test Case",
                "Description on this test case",
                "ann@email.com",
                "",
                LocalDateTime.now(),
                Status.PENDING
        );
        underTest.save(tCase);
        // When
        List<TCase> results = underTest
                .findAllByParams("First Test Case", "", Status.PENDING);
        // Then
        Assertions.assertThat(results)
                .isNotEmpty()
                .size()
                .isEqualTo(1);

        Assertions.assertThat(results)
                .hasOnlyElementsOfType(TCase.class)
                .element(0)
                .hasFieldOrPropertyWithValue("title", "First Test Case");
    }

    @Test
    @DisplayName("Should return only active test cases")
    void shouldReturnOnlyActiveTestCases() {
        // Given
        TCase tCase1 = TCase
                .builder()
                .title("First Test Case")
                .status(Status.PENDING)
                .build();

        TCase tCase2 = TCase
                .builder()
                .title("Second Test Case")
                .status(Status.ACTIVE)
                .build();

        TCase tCase3 = TCase
                .builder()
                .title("Third Test Case")
                .status(Status.INACTIVE)
                .build();

        underTest.saveAll(List.of(tCase1, tCase2, tCase3));
        // When
        List<TCase> results = underTest
                .findAllByStatus(Status.ACTIVE.name());
        // Then
        Assertions.assertThat(results)
                .isNotEmpty()
                .size()
                .isEqualTo(1);

        Assertions.assertThat(results)
                .hasOnlyElementsOfType(TCase.class)
                .element(0)
                .hasFieldOrPropertyWithValue("title", "Second Test Case");
    }

    @Test
    @DisplayName("Should not save test case when title is empty")
    void shouldNotSaveTestCaseWhenTitleIsEmpty() {
        // Given
        TCase tCase = TCase
                .builder()
                .status(Status.ACTIVE)
                .build();

        assertThatThrownBy(() -> underTest.save(tCase))
                .isInstanceOf(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> underTest.findAll())
                .isInstanceOf(DataIntegrityViolationException.class);

    }

}