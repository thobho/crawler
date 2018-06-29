package com.thobho.crawler;

import com.thobho.crawler.configuration.configuration.Config;
import com.thobho.crawler.datasource.HtmlSource;
import com.thobho.crawler.exception.PageDownloadException;
import com.thobho.crawler.extraction.MessageExtractor;
import com.thobho.crawler.persist.MessageSaver;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.Optional;

public abstract class CrawlerRunner {

    private final Logger log = Logger.getLogger(CrawlerRunner.class);

    protected HtmlSource htmlSource;
    protected MessageSaver messageSaver;
    protected MessageExtractor messageExtractor;
    protected Config configuration;

    private CrawlerRunner() {
    }

    public CrawlerRunner(HtmlSource htmlSource, MessageSaver messageSaver, MessageExtractor messageExtractor, String[] cmdArgs) {
        this.htmlSource = htmlSource;
        this.messageSaver = messageSaver;
        this.messageExtractor = messageExtractor;
        this.configuration = createConfiguration(cmdArgs).orElse(null);
    }

    protected void start() {
        if (configuration == null) {
            return;
        }
        log.info("Running crawler with parameters:" + configuration.toString());
        try {
            messageSaver.save(
                    messageExtractor.extractFromHtml(
                            htmlSource.providePages(configuration)
                    ), configuration
            );
        } catch (PageDownloadException e) {
            log.error("Error during pages download");
        } catch (FileNotFoundException e) {
            log.error("Config file not found");
        }
    }

    abstract Optional<Config> createConfiguration(String[] cmdArgs);

}
