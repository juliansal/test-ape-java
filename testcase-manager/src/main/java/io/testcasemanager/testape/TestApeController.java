package io.testcasemanager.testape;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/test-ape")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class TestApeController {

}
