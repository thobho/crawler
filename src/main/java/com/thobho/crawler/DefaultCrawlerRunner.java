package com.thobho.crawler;

import com.thobho.crawler.configuration.ConfigurationProvider;
import com.thobho.crawler.configuration.configuration.Config;
import com.thobho.crawler.datasource.HtmlSource;
import com.thobho.crawler.extraction.MessageExtractor;
import com.thobho.crawler.persist.MessageSaver;

import java.util.Optional;

class DefaultCrawlerRunner extends CrawlerRunner {

    DefaultCrawlerRunner(HtmlSource htmlSource, MessageSaver messageSaver, MessageExtractor messageExtractor, String[] cmdArgs) {
        super(htmlSource, messageSaver, messageExtractor, cmdArgs);
    }

    @Override
    Optional<Config> createConfiguration(String[] cmdArgs) {
        return ConfigurationProvider.getInstance().parse(cmdArgs);
    }

}
