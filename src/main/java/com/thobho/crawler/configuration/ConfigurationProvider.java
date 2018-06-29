package com.thobho.crawler.configuration;

import com.thobho.crawler.configuration.configuration.Config;
import com.thobho.crawler.configuration.configuration.IntParam;
import com.thobho.crawler.datasource.http.JsoupPageWorker;
import com.thobho.crawler.exception.ConfigNotFoundException;
import com.thobho.crawler.exception.ParameterParseException;
import com.thobho.crawler.exception.ValidationException;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.util.Optional;

import static com.thobho.crawler.configuration.configuration.DefaultParamKey.NUMBER;

public class ConfigurationProvider {
    private final Logger logger = Logger.getLogger(ConfigurationProvider.class);

    private static final ConfigurationProvider INSTANCE = new ConfigurationProvider();

    private static final CommandLineParser commandLineParser = new DefaultParser();
    private final Options options;
    private final FileConfigUploader fileConfigUploader;

    public ConfigurationProvider() {
        this.options = new Options();
        this.fileConfigUploader = new FileConfigUploader();
        options.addOption("n",
                "messageNumber",
                true,
                "Number of messages to download");
        options.addOption("c",
                "configFile",
                true,
                "Path to config file.");
        options.addOption("h",
                "help",
                false,
                "Print this help");
    }

    public static ConfigurationProvider getInstance() {
        return INSTANCE;
    }

    public Optional<Config> parse(String[] args) {
        Config config;
        try {
            CommandLine cmd = commandLineParser.parse(options, args);
            if (cmd.hasOption("c")) {
                config = fileConfigUploader.load(cmd.getOptionValue("c"))
                        .orElseThrow(ConfigNotFoundException::new);
            } else {
                config = fileConfigUploader.loadDefault()
                        .orElseThrow(ConfigNotFoundException::new);
            }

            if (cmd.hasOption("n")) {
                config.addIntParam(NUMBER,
                        new IntParam(number -> number > 1 && number < 500,
                                "Number must be between 1 and 500",
                                cmd.getOptionValue("n")));
            }

            if (cmd.hasOption("h")) {
                new HelpFormatter().printHelp("Main", options);
                return Optional.empty();
            }

            config.parseParameters();
            config.getValidationErrors();
        } catch (ParameterParseException e) {
            logger.error(e.getMessage());
            return Optional.empty();
        } catch (ParseException e) {
            logger.error("Error while parsing command line argument. " + e.getMessage());
            return Optional.empty();
        } catch (ConfigNotFoundException e) {
            logger.error("Config file not found. " + e.getMessage());
            return Optional.empty();
        } catch (ValidationException e) {
            logger.error("Validation Exeption. " + e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Something unexpected. Please fix it or write to me: tomasz.bar3@gmail.com");
            return Optional.empty();
        }
        return Optional.of(config);
    }

}
