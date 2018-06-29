package com.thobho.crawler.configuration.configuration;

import com.thobho.crawler.exception.ParameterParseException;
import com.thobho.crawler.exception.ValidationException;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class Config {
    private final Logger log = Logger.getLogger(Config.class);
    private Map<ParamKey, StringParam> stringParameters = new HashMap<>();
    private Map<ParamKey, IntParam> intParameters = new HashMap<>();

    public int getInt(ParamKey key) {
        return intParameters.get(key).getValue();
    }

    public String getString(ParamKey key) {
        return stringParameters.get(key).getValue();
    }

    public void parseParameters() {
        stringParameters.entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(parameter -> {
                    try {
                        parameter.parse();
                    } catch (NumberFormatException e) {
                        throw new ParameterParseException(parameter.getValidationErrorMessage());
                    }
                });

        intParameters.entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(parameter -> {
                    try {
                        parameter.parse();
                    } catch (NumberFormatException e) {
                        throw new ParameterParseException(parameter.getValidationErrorMessage());
                    }
                });

    }

    public void getValidationErrors() {
        Stream<Optional<String>> stringErrorMessages = stringParameters.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(StringParam::validate);

        Stream<Optional<String>> intErrorMessages = intParameters.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(IntParam::validate);

        String validationErrors = Stream.concat(stringErrorMessages, intErrorMessages)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(joining("\n"));

        if (!"".equals(validationErrors)) {
            throw new ValidationException(validationErrors);
        }
    }

    public void addStringParam(ParamKey key, StringParam parameter) {
        if (stringParameters.containsKey(key)) {
            log.warn(key.getKey() + "already exist in params. Replaced!");
        }
        stringParameters.put(key, parameter);
    }

    public void addIntParam(ParamKey key, IntParam parameter) {
        if (stringParameters.containsKey(key)) {
            log.warn(key.getKey() + "already exist in params. Replaced!");
        }
        intParameters.put(key, parameter);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\n");
        stringParameters.forEach((key, value) -> sb.append(key.getKey() + " --> " + value.rawValue + "\n"));
        intParameters.forEach((key, value) -> sb.append(key.getKey() + " --> " + value.rawValue + "\n"));
        return sb.toString();
    }
}
