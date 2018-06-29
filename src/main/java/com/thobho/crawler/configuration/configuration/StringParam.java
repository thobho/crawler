package com.thobho.crawler.configuration.configuration;

import java.util.Optional;
import java.util.function.Predicate;

public class StringParam extends Parameter {
    private String value;
    private Predicate<String> validationFunction;

    public StringParam(Predicate<String> validationFunction, String validationErrorMessage, String rawValue) {
        super(validationErrorMessage, rawValue);
        this.validationFunction = validationFunction;
    }

    Optional<String> validate() {
        if (value == null) {
            return Optional.of("Value not parsed");
        }
        return validationFunction.test(value) ? Optional.empty() : Optional.of(validationErrorMessage);
    }

    String getValue() {
        return value;
    }

    @Override
    void parse() {
        value = rawValue.trim();
    }
}

