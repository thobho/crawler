package com.thobho.crawler.datasource.http;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import com.thobho.crawler.configuration.configuration.Config;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static com.thobho.crawler.configuration.configuration.DefaultParamKey.ADDRESS;
import static com.thobho.crawler.configuration.configuration.DefaultParamKey.POSTFIX;

public class JsoupPageWorker implements PageWorker {

    private final Logger log = Logger.getLogger(JsoupPageWorker.class);
    private final Connection connection;

    JsoupPageWorker(Config config, int pageIndex) {
        String source = config.getString(ADDRESS) + config.getString(POSTFIX) + pageIndex;
        this.connection = Jsoup.connect(source);
    }

    public PageWrapper call() {
        try {
            Instant start = Instant.now();
            Document document = connection.get();
            Instant finish = Instant.now();
            return new PageWrapper(
                    document.html(),
                    Duration.between(start, finish).toMillis(),
                    PageWrapper.Status.SUCCESS
            );
        } catch (IOException e) {
            return PageWrapper.createFailMessage();
        }
    }
}
