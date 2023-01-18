package io.testcasemanager.author;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/authors")
@AllArgsConstructor
public class AuthorController {

	private AuthorService authorService;

	@GetMapping("all")
	public List<Author> getAllAuthors() {
		return authorService.findAllAuthors();
	}

	@PostMapping("new")
	@ResponseBody
	public String createAuthor(@RequestBody Author author) {
		Author authorBuilder = Author
				.builder()
				.name(author.getName())
				.emailAddress(author.getEmailAddress())
				.role(author.getRole())
				.build();

		return authorService.createAuthor(authorBuilder);
	}
 }
