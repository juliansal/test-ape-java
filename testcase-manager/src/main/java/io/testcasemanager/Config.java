package io.testcasemanager;

import io.testcasemanager.tcase.Status;
import io.testcasemanager.tcase.TCase;
import io.testcasemanager.tcase.TCaseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
