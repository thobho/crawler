package com.thobho.crawler;

import com.thobho.crawler.datasource.http.DownloadService;
import com.thobho.crawler.extraction.jsoup.JsoupMessageExtractor;
import com.thobho.crawler.persist.file.FileMessageSaver;

public class Crawler {
    public static void main(String[] args) {

        new DefaultCrawlerRunner(
                new DownloadService(),
                new FileMessageSaver(),
                new JsoupMessageExtractor(),
                args
        ).start();
    }
}