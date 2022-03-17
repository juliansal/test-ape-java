package io.happykraken.testcasemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TestApeApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApeApp.class, args);
    }
}
