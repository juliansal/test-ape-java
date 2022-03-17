package io.happykraken.testcasemanager.tcase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Test Case Service Test")
class TCaseServiceTest {
    @Mock
    private TCaseRepository caseRepository;

    private TCaseService underTest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        underTest = new TCaseService(caseRepository);
    }

    @Test
    @DisplayName("Should find all test cases")
    void shouldFindAllTestCases() {
        // Given
        TCase tCase = new TCase(
                Long.valueOf("20011"),
                "First Test Case",
                "Description on this test case",
                "ann@email.com",
                "",
                LocalDateTime.now(),
                "Pending"
        );
        given(caseRepository.findAll())
                .willReturn(List.of(tCase, tCase));
        // When
        List<TCase> results = underTest.findTestCases();
        // Then
        assertThat(results.size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("Should find test case by case number")
    void shouldFindTestCaseByCaseNumber() {
        // Given
        TCase tCase = new TCase(
                Long.valueOf("20011"),
                "First Test Case",
                "Description on this test case",
                "ann@email.com",
                "",
                LocalDateTime.now(),
                "Pending"
        );
        given(caseRepository.findById(Long.valueOf("20011")))
                .willReturn(Optional.of(tCase));
        // When
        Optional<TCase> result = underTest.findTestCaseById(Long.valueOf("20011"));
        // Then
        assertThat(result)
                .get()
                .isNotNull()
                .isExactlyInstanceOf(TCase.class)
                .hasFieldOrPropertyWithValue("authorEmail", "ann@email.com");

    }

    @Test
    @DisplayName("Should return empty test case list when wrong case number is provided")
    void shouldNotFindTestCaseWhenWrongCaseNumberIsProvided() {
        // Given
        TCase tCase = new TCase(
                Long.valueOf("20011"),
                "First Test Case",
                "Description on this test case",
                "ann@email.com",
                "",
                LocalDateTime.now(),
                "Pending"
        );
        given(caseRepository.findById(Long.valueOf("20011")))
                .willReturn(Optional.of(tCase));
        // When
        Optional<TCase> result = underTest.findTestCaseById(Long.valueOf("00000"));
        // Then
        assertThat(result)
                .isEmpty()
                .isNotNull();

    }
}