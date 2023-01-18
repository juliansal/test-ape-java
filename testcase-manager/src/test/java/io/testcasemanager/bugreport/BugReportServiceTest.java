package io.testcasemanager.bugreport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("Bug Report Service Test")
class BugReportServiceTest {
    private BugRepository bugRepository;

    private BugReportService underTest;

    @BeforeEach
    void setup() {
        bugRepository = Mockito.mock(BugRepository.class);
        underTest = new BugReportService(bugRepository);
    }

    @Test
    @DisplayName("Should find all bug reports")
    void shouldFindAllBugReports() {
        // Given
        Bug bugReport = Bug
                .builder()
                .id(Long.valueOf("1001"))
                .build();
        given(bugRepository.findAll())
                .willReturn(List.of(bugReport, bugReport));
        // When
        List<Bug> results = underTest
                .findAllBugs();
        // Then
        assertThat(results.size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("Should find bug report matching description")
    void shouldFindBugReportMatchingDescription() {
        Bug bugReport1 = Bug
                .builder()
                .bugStatus(BugStatus.ACTIVE)
                .description("The first bug report")
                .build();

        given(bugRepository.findAllBy(
                "The first bug report",
                        BugStatus.ACTIVE))
                .willReturn(List.of(bugReport1));
        // When
        List<Bug> results = underTest
                .findBugsByParams(
                        "The first bug report",
                        BugStatus.ACTIVE.name()
                );
        // Then
        assertThat(results.size())
                .isEqualTo(1);

        assertThat(results.stream().findFirst())
                .get()
                .hasFieldOrPropertyWithValue("description", "The first bug report");

    }

    @Test
    @DisplayName("Should find a bug report match bugId")
    void shouldFindByBugReportId() {
        // Given
        BugId bugId = new BugId(Long.valueOf("1010"), Long.valueOf("2001"));
        Bug bugReport1 = Bug
                .builder()
                .id(Long.valueOf("1010"))
                .testcaseId(Long.valueOf("2001"))
                .build();
        given(bugRepository.findById(bugId))
                .willReturn(Optional.ofNullable(bugReport1));
        // When
        Optional<Bug> result = underTest
                .findBugById(Long.valueOf("1010"), Long.valueOf("2001"));
        // Then
        assertThat(result)
                .contains(bugReport1);
    }

    @Test
    @DisplayName("Should create a bug report")
    void shouldCreateABugReport() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        Bug bugReport1 = Bug
                .builder()
                .id(Long.valueOf("1010"))
                .testcaseId(Long.valueOf("2001"))
                .createdAt(timestamp)
                .build();

        given(bugRepository.save(bugReport1))
                .willReturn(bugReport1);
        // When
        underTest.createBugReport(bugReport1);
        // Then
        verify(bugRepository)
                .save(bugReport1);
    }

    @Test
    @DisplayName("Should update bug report")
    void shouldUpdateBugReport() {
        BugId bugId = new BugId(Long.valueOf("1010"), Long.valueOf("2001"));
        LocalDateTime timestamp = LocalDateTime.now();
        Bug bugReport1 = Bug
                .builder()
                .id(bugId.getId())
                .testcaseId(bugId.getTestcaseId())
                .createdAt(timestamp)
                .build();
        Bug updatedBugReport = Bug
                .builder()
                .bugStatus(BugStatus.RESOLVED)
                .description("This was changed")
                .expectedOutcome("")
                .authorEmail("maryjean@mail.com")
                .build();
        given(bugRepository.save(bugReport1))
                .willReturn(bugReport1);

        Optional<Bug> result = underTest.updateBugReport(updatedBugReport);

        assertThat(result)
                .contains(updatedBugReport);
    }

    @Test
    @DisplayName("Should delete bug report")
    void shouldDeleteBugReport() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        Bug bugReport1 = Bug
                .builder()
                .id(Long.valueOf("1010"))
                .testcaseId(Long.valueOf("2001"))
                .createdAt(timestamp)
                .build();
        BugId bugId = new BugId(bugReport1.getId(), bugReport1.getTestcaseId());
        given(bugRepository.findById(bugId))
                .willReturn(Optional.of(bugReport1));

        // When
        doNothing()
                .when(mock(underTest.getClass()))
                .deleteBugReport(bugReport1);

        underTest
                .deleteBugReport(bugReport1);
        // Then
        verify(bugRepository)
                .delete(bugReport1);
    }

}