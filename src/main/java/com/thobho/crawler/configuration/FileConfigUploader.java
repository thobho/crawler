package com.thobho.crawler.configuration;

import com.thobho.crawler.CrawlerRunner;
import com.thobho.crawler.configuration.configuration.Config;
import com.thobho.crawler.configuration.configuration.IntParam;
import com.thobho.crawler.configuration.configuration.StringParam;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static com.thobho.crawler.configuration.configuration.DefaultParamKey.*;
import static com.thobho.crawler.configuration.configuration.DefaultParamKey.OUTFILE;
import static com.thobho.crawler.configuration.configuration.DefaultParamKey.POSTFIX;

public class FileConfigUploader {
    private final Logger log = Logger.getLogger(FileConfigUploader.class);

    public static final String DEFAULT_CONFIG_FILENAME = "crawler.properties";
    private static final String urlRegexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private Properties props;

    public FileConfigUploader() {
        this.props = new Properties();
    }

    public Optional<Config> loadDefault(){
        return load(DEFAULT_CONFIG_FILENAME);
    }

    public Optional<Config> load(String filePath) {
        Config config = new Config();
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            props.load(inputStream);
            config.addIntParam(NUMBER,
                    new IntParam(number -> number > 1 && number < 500,
                            "Number must be between 1 and 500",
                            props.getProperty(NUMBER.getKey())));

            config.addIntParam(THREADS,
                    new IntParam(number -> number > 1 && number < 30,
                            "Threads pool size must be between 1 and 30",
                            props.getProperty(THREADS.getKey())));

            config.addIntParam(TIMEOUT,
                    new IntParam(number -> number > 1 && number < 60000,
                            "Timeout [ms] must be between 1 and 60000",
                            props.getProperty(TIMEOUT.getKey())));

            config.addStringParam(ADDRESS,
                    new StringParam(string -> string.matches(urlRegexp),
                            "Address is not a valid URL.",
                            props.getProperty(ADDRESS.getKey())));

            config.addStringParam(POSTFIX,
                    new StringParam(string -> true,
                            "",
                            props.getProperty(POSTFIX.getKey())));

            config.addStringParam(OUTFILE,
                    new StringParam(string -> string.length() < 30,
                            "Output filename must not be larger than 30 chars.",
                            props.getProperty(OUTFILE.getKey())));
        } catch (IOException e) {
            log.error("Config file not found under path: " + filePath);
            return Optional.empty();
        }
        return Optional.of(config);
    }

}
