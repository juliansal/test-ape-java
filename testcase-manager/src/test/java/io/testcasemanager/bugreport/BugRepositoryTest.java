package io.testcasemanager.bugreport;

import io.testcasemanager.tcase.TCaseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

@DataJpaTest(
		properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"}
)
@DisplayName("Bug Repository Test")
class BugRepositoryTest {

	@Autowired
	private BugRepository underTest;

	@AfterEach
	void tearDown() {
		underTest.deleteAll();
	}

	@Test
	@DisplayName("Should Find All Bugs By Description")
	void shouldFindAllBugsByDescription() {
		// Given
		Bug bug = Bug
				.builder()
				.bugStatus(BugStatus.ACTIVE)
				.authorEmail(anyString())
				.description("The first bug report")
				.expectedOutcome(anyString())
				.testcaseId(anyLong())
				.build();
		underTest.save(bug);

		// When
		List<Bug> results = underTest.findAllBy("The first bug report", BugStatus.ACTIVE);

		// Then
		assertThat(results)
				.asList()
				.isNotEmpty();

		assertThat(results)
				.hasSize(1);

		assertThat(results)
				.asList()
				.first()
				.hasFieldOrPropertyWithValue("description", "The first bug report");
	}

	@Test
	@DisplayName("Should Find All Active Bugs")
	void shouldFindAllActiveBugs() {
		// Given
		Bug bug = Bug
				.builder()
				.bugStatus(BugStatus.ACTIVE)
				.authorEmail(anyString())
				.description("The first bug report")
				.expectedOutcome(anyString())
				.testcaseId(anyLong())
				.build();
		underTest.save(bug);

		// When
		List<Bug> results = underTest.findAllBy(anyString(), BugStatus.ACTIVE);

		// Then
		assertThat(results)
				.hasSize(1);

	}

	@Test
	@DisplayName("Should Not Find Cancelled Bugs When There Are None")
	void shouldNotFindCancelledBugsWhenThereAreNone() {
		// Given
		Bug bug = Bug
				.builder()
				.bugStatus(BugStatus.ACTIVE)
				.authorEmail(anyString())
				.description("The first bug report")
				.expectedOutcome(anyString())
				.testcaseId(anyLong())
				.build();
		underTest.save(bug);

		// When
		List<Bug> results = underTest.findAllBy(anyString(), BugStatus.CANCELLED);

		// Then
		assertThat(results)
				.isEmpty();
	}

}
