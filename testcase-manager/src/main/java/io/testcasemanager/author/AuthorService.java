package io.testcasemanager.author;

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

	public List<Author> findAllAuthors() {
		return authorRepository
				.findAll()
				.stream()
				.limit(1000)
				.collect(Collectors.toList());
	}

	public Optional<Author> createAuthor(Author author) {
		Optional<Author> findAuthor = authorRepository.findById(author.getId());
		if (findAuthor.isPresent()) {
			log.info("This author already exists");
			return findAuthor;
		}

		UUID savedID = authorRepository.save(author).getId();

		return authorRepository.findById(savedID);
	}
}
