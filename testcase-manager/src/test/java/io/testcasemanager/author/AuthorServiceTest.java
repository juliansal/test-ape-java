package io.testcasemanager.author;

import io.testcasemanager.utils.TestCaseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AuthorServiceTest {
	private AuthorRepository authorRepository;

	private AuthorService underTest;

	private TestCaseValidator testCaseValidator;

	@BeforeEach
	void setup() {
		authorRepository = Mockito.mock(AuthorRepository.class);
		testCaseValidator = Mockito.mock(TestCaseValidator.class);
		underTest = new AuthorService(authorRepository, testCaseValidator);
	}

	@Test
	@DisplayName("Should Find All Authors")
	void shouldFindAllAuthors() {
		// Given
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		Author author1 = Author
				.builder()
				.id(uuid1)
				.emailAddress("potterh@mail.com")
				.name("Harry Potter")
				.role("Admin")
				.build();
		Author author2 = Author
				.builder()
				.id(uuid2)
				.emailAddress("granger@mail.com")
				.name("Hermione Granger")
				.role("User")
				.build();
		given(authorRepository.findAll())
				.willReturn(List.of(author1, author2));
		// When
		List<Author> results = underTest.findAllAuthors();

		// Then
		assertThat(results)
				.isNotEmpty()
				.asList()
				.hasSize(2);
	}

	@Test
	@DisplayName("Should Create Author")
	void shouldCreateAuthor() {
		// Given
		UUID uuid1 = UUID.randomUUID();
		Author author1 = Author
				.builder()
				.id(uuid1)
				.emailAddress("potterh@mail.com")
				.name("Harry Potter")
				.role("Admin")
				.build();
		given(testCaseValidator.test("potterh@mail.com"))
				.willReturn(true);
		given(authorRepository.save(author1))
				.willReturn(author1);
		// When
		underTest.createAuthor(author1);
		// Then
		verify(authorRepository)
				.save(author1);
	}

	@Test
	@DisplayName("Should Not Create Author When They Already Exist")
	void shouldNotCreateAuthorWhenAlreadyExists() {
		// Given
		UUID uuid1 = UUID.randomUUID();
		Author author1 = Author
				.builder()
				.id(uuid1)
				.emailAddress("potterh@mail.com")
				.name("Harry Potter")
				.role("Admin")
				.build();
		given(testCaseValidator.test("potterh@mail.com"))
				.willReturn(true);
		given(authorRepository.save(author1))
				.willReturn(author1);
		given(authorRepository.findByEmailAddress(author1.getEmailAddress()))
				.willReturn(Optional.of(author1));

		// When
		underTest.createAuthor(author1);

		// Then
		verify(authorRepository, never())
				.save(author1);
	}

	@Test
	@DisplayName("Should Return Author After Creating It")
	void shouldReturnAuthorAfterCreatingIt() {
		// Given
		UUID uuid1 = UUID.randomUUID();
		Author author1 = Author
				.builder()
				.id(uuid1)
				.emailAddress("potterh@mail.com")
				.name("Harry Potter")
				.role("Admin")
				.build();
		given(testCaseValidator.test("potterh@mail.com"))
				.willReturn(true);
		given(authorRepository.save(author1))
				.willReturn(author1);

		// When
		String authorCreated = underTest.createAuthor(author1);

		// Then
		assertThat(authorCreated)
				.isNotNull()
				.isEqualTo(author1.getEmailAddress());

		verify(authorRepository)
				.save(author1);

	}
}