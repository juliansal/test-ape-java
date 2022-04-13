package io.happykraken.testcasemanager.bugreport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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
                .containsSame(bugReport1);

    }
}