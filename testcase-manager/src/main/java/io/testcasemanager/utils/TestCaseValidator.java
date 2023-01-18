package io.testcasemanager.utils;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class TestCaseValidator implements Predicate<String> {

    @Override
    public boolean test(String field) {
        return field.length() > 1;
    }
}
