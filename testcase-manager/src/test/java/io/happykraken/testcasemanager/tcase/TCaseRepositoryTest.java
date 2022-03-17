package io.happykraken.testcasemanager.tcase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"}
)
class TCaseRepositoryTest {

    @Autowired
    private TCaseRepository underTest;

    @Test
    void shouldReturnWhenFindingByTitleOnly() {
        // Given
        TCase tCase = new TCase(
                Long.valueOf("20010"),
                "First Test Case",
                "Description on this test case",
                "ann@email.com",
                "",
                LocalDateTime.now(),
                "Pending"
        );
        underTest.save(tCase);
        // When
        List<TCase> results = underTest
                .findAllByParams("First Test Case", "", "", "");
        // Then
        assertThat(results)
                .isNotEmpty()
                .size()
                .isEqualTo(1);

        assertThat(results)
                .hasOnlyElementsOfType(TCase.class)
                .element(0)
                .hasFieldOrPropertyWithValue("title", "First Test Case");
    }

}