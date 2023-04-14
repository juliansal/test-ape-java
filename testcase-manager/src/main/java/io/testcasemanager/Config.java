package io.testcasemanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.testcasemanager.author.Author;
import io.testcasemanager.author.AuthorService;
import io.testcasemanager.bugreport.Bug;
import io.testcasemanager.bugreport.BugReportService;
import io.testcasemanager.bugreport.BugStatus;
import io.testcasemanager.step.Step;
import io.testcasemanager.step.StepService;
import io.testcasemanager.tcase.Status;
import io.testcasemanager.tcase.TCase;
import io.testcasemanager.tcase.TCaseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	@Bean
	public CommandLineRunner makeTestCases(TCaseService tCaseService) {
		return args -> {
			TCase tCase1 = TCase
					.builder()
					.title("First Test Case 1")
					.description("Testing")
					.authorEmail("jane@mail.com")
					.preConditions("None")
					.status(Status.ACTIVE)
					.build();

			TCase tCase2 = TCase
					.builder()
					.title("First Test Case 2")
					.description("Testing something here")
					.authorEmail("carl@mail.com")
					.preConditions("None")
					.status(Status.ACTIVE)
					.build();

			TCase tCase3 = TCase
					.builder()
					.title("First Test Case 3")
					.description("Hello world")
					.authorEmail("john@mail.com")
					.preConditions("None")
					.status(Status.ACTIVE)
					.build();

			tCaseService.createTestCase(tCase1);
			tCaseService.createTestCase(tCase2);
			tCaseService.createTestCase(tCase3);
		};
	}

	@Bean
	public CommandLineRunner makeAuthors(AuthorService authorService) {
		return args -> {
			Author author1 = Author
					.builder()
					.name("Fred Weasley")
					.emailAddress("fred@email.com")
					.role("Admin")
					.build();

			Author author2 = Author
					.builder()
					.name("George Weasley")
					.emailAddress("george@email.com")
					.role("Admin")
					.build();

			Author author3 = Author
					.builder()
					.name("Ginny Weasley")
					.emailAddress("ginny@email.com")
					.role("User")
					.build();

			authorService.createAuthor(author1);
			authorService.createAuthor(author2);
			authorService.createAuthor(author3);
		};
	}

	@Bean
	public CommandLineRunner makeBugReports(BugReportService bugReportService) {
		return args -> {
			Bug bug1 = Bug
					.builder()
					.authorEmail("anakay@mail.com")
					.bugStatus(BugStatus.ACTIVE)
					.description("There is no bug here")
					.expectedOutcome("No special outcome")
					.testcaseId(Long.valueOf("10001"))
					.build();

			Bug bug2 = Bug
					.builder()
					.authorEmail("kkayla@mail.com")
					.bugStatus(BugStatus.ACTIVE)
					.description("There is no bug here")
					.expectedOutcome("No special outcome")
					.testcaseId(Long.valueOf("10002"))
					.build();

			Bug bug3 = Bug
					.builder()
					.authorEmail("minka@mail.com")
					.bugStatus(BugStatus.ACTIVE)
					.description("There is no bug here")
					.expectedOutcome("No special outcome")
					.testcaseId(Long.valueOf("10002"))
					.build();

			bugReportService.createBugReport(bug1);
			bugReportService.createBugReport(bug2);
			bugReportService.createBugReport(bug3);
		};
	}

	@Bean
	public CommandLineRunner makeSteps(StepService stepService) {
		return args -> {
			Step step1 = Step
					.builder()
					.stepOrder(0)
					.testCaseNumber(Long.valueOf("10001"))
					.action("Perform first action")
					.expectedResult("Expect first result")
					.hasBug(false)
					.build();

			Step step2 = Step
					.builder()
					.stepOrder(1)
					.testCaseNumber(Long.valueOf("10001"))
					.action("Perform second action")
					.expectedResult("Expect first result")
					.hasBug(false)
					.build();

			Step step3 = Step
					.builder()
					.stepOrder(2)
					.testCaseNumber(Long.valueOf("10002"))
					.action("Perform first action on this testcase")
					.expectedResult("Expect first result")
					.hasBug(false)
					.build();

			stepService.createStep(step1, Long.valueOf("10001"));
			stepService.createStep(step2, Long.valueOf("10001"));
			stepService.createStep(step3, Long.valueOf("10002"));
		};
	}

	@Bean
	RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(new ObjectMapper());
		restTemplate.getMessageConverters().add(converter);
		return restTemplate;
	}
}
