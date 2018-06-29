package com.thobho.crawler.configuration.configuration;

import java.util.Optional;
import java.util.function.Predicate;

public class IntParam extends Parameter {
    private int value;
    private Predicate<Integer> validationFunction;

    public IntParam(Predicate<Integer> validationFunction, String validationErrorMessage, String rawValue) {
        super(validationErrorMessage, rawValue);
        this.validationFunction = validationFunction;
    }

    Integer getValue() {
        return value;
    }

    Optional<String> validate(){
        return validationFunction.test(value) ? Optional.empty() : Optional.of(validationErrorMessage);
    }

    @Override
    void parse() {
        this.value = Integer.parseInt(rawValue);
    }
}
