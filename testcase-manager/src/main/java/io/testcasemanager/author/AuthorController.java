package io.testcasemanager.author;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/authors")
@AllArgsConstructor
public class AuthorController {

	@GetMapping("all")
	public List<Author> getAllAuthors() {
		return null;
	}
}
