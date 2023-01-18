package io.testcasemanager.author;

import io.testcasemanager.utils.TestCaseValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class AuthorService {
	private AuthorRepository authorRepository;
	private final TestCaseValidator testCaseValidator;

	public List<Author> findAllAuthors() {
		return authorRepository
				.findAll()
				.stream()
				.limit(1000)
				.collect(Collectors.toList());
	}

	public String createAuthor(Author author) {
		if (!testCaseValidator.test(author.getEmailAddress())) {
			throw new IllegalStateException("The email address was empty");
		}

		Optional<Author> findAuthor = authorRepository.findByEmailAddress(author.getEmailAddress());

		if (findAuthor.isPresent()) {
			log.info("This author already exists");
			return findAuthor.get().getEmailAddress();
		}

		author.setId(UUID.randomUUID());

		return authorRepository
				.save(author)
				.getEmailAddress();
	}
}
