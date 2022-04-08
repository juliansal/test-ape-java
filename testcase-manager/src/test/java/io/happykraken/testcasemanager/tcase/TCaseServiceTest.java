package io.happykraken.testcasemanager.tcase;

import io.happykraken.testcasemanager.utils.TestCaseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@DisplayName("Test Case Service Test")
class TCaseServiceTest {
    @Mock
    private TCaseRepository caseRepository;

    @Mock
    private TestCaseValidator testCaseValidator;

    private TCaseService underTest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        underTest = new TCaseService(caseRepository, testCaseValidator);
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
                Status.PENDING
        );
        given(caseRepository.findAll())
                .willReturn(List.of(tCase, tCase));
        // When
        List<TCase> results = underTest.findAllTestCases();
        // Then
        assertThat(results.size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("Should find an active test case")
    void shouldFindAnActiveTestCase() {
        // Given
        TCase tCase1 = new TCase(
                Long.valueOf("20011"),
                "First Test Case",
                "Description on this test case",
                "ann@email.com",
                "",
                LocalDateTime.now(),
                Status.ACTIVE
        );
        given(caseRepository.findAllByStatus(Status.ACTIVE.name()))
                .willReturn(List.of(tCase1));
        // When
        List<TCase> results = underTest.findAllTestCasesByStatus(Status.ACTIVE);
        // Then
        assertThat(results.size())
                .isEqualTo(1);

        assertThat(results.get(0).getTitle())
                .isEqualTo("First Test Case");
    }

    @Test
    @DisplayName("Should find test case by matching title")
    void shouldFindTestCaseByTitle() {
        // Given
        TCase tCase1 = TCase
                .builder()
                .title("Find this test case")
                .status(Status.ACTIVE)
                .build();
        given(caseRepository.findAllByParams(eq("Find this test case"), any(), any(Status.class)))
                .willReturn(List.of(tCase1));
        // When
        List<TCase> results = underTest
                .findTestCases("Find this test case", "", "bad");
        // Then
        assertThat(results.size())
                .as("There should have been 1 matching test case")
                .isEqualTo(1);
        // When
        results = underTest
                .findTestCases("Not this test case", "", "");
        // Then
        assertThat(results.size())
                .as("There should not have been matching test cases with this title")
                .isEqualTo(0);
    }

    @Test
    @DisplayName("Should not return inactive test case when finding active ones")
    void shouldNotReturnInactiveTestCasesWhenFindingActiveOnes() {
        // Given
        TCase tCase1 = new TCase(
                Long.valueOf("20011"),
                "First Test Case",
                "Description on this test case",
                "ann@email.com",
                "",
                LocalDateTime.now(),
                Status.PENDING
        );
        given(caseRepository.findAllByStatus(Status.PENDING.name()))
                .willReturn(List.of(tCase1));
        // When
        List<TCase> results = underTest.findAllTestCasesByStatus(Status.ACTIVE);
        // Then
        assertThat(results.size())
                .isEqualTo(0);
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
                Status.PENDING
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
                Status.PENDING
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

    @Test
    @DisplayName("Should create a test case when title is provided")
    void shouldCreateATestCaseWhenTitleIsProvided() {
        // Given
        TCase tCase = TCase
                .builder()
                .title("Test Case 1")
                .build();

        given(testCaseValidator.test("Test Case 1"))
                .willReturn(true);
        given(caseRepository.save(tCase))
                .willReturn(tCase);
        given(mock(TCase.class).getCaseNumber())
                .willReturn(anyLong());
        // When
        when(underTest.createTestCase(tCase))
                .thenReturn(Optional.of(tCase));
        // Then
        verify(caseRepository)
                .save(any(TCase.class));
    }

    @Test
    @DisplayName("Should not create a test case when no arguments provided")
    void shouldNotCreateATestCaseWhenNoArgumentsProvided() {
        // Given
        TCase tCase = TCase
                .builder()
                .build();
        given(testCaseValidator.test(""))
                .willReturn(false);
        // When
        assertThatThrownBy(() -> underTest.createTestCase(tCase))
                .isInstanceOf(IllegalStateException.class);
        // Then
        then(caseRepository)
                .should(never())
                .save(any());
    }
}













