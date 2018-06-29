package com.thobho.crawler.datasource;

import com.thobho.crawler.configuration.configuration.Config;

import java.util.stream.Stream;

public interface HtmlSource {
    Stream<String> providePages(Config configuration);
}
