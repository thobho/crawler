package com.thobho.crawler.datasource.http;

import com.thobho.crawler.configuration.configuration.Config;
import com.thobho.crawler.datasource.HtmlSource;
import com.thobho.crawler.exception.PageDownloadException;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.thobho.crawler.configuration.configuration.DefaultParamKey.NUMBER;
import static com.thobho.crawler.configuration.configuration.DefaultParamKey.THREADS;
import static com.thobho.crawler.configuration.configuration.DefaultParamKey.TIMEOUT;
import static java.util.stream.Collectors.toList;

public class DownloadService implements HtmlSource {

    private final Logger log = Logger.getLogger(DownloadService.class);

    @Override
    public Stream<String> providePages(Config config) {
        return downloadPages(config).stream()
                .map(this::extractValue)
                .filter(PageWrapper::isSuccessful)
                .map(PageWrapper::getPageSource);
    }

    private List<Future<PageWrapper>> downloadPages(Config config) {
        ExecutorService executorService = Executors.newFixedThreadPool(config.getInt(THREADS));
        try {
            Instant start = Instant.now();
            List<Future<PageWrapper>> futures = executorService.invokeAll(
                    getWorkers(config),
                    config.getInt(TIMEOUT),
                    TimeUnit.MILLISECONDS);
            boolean downloadInProgress = true;
            while (downloadInProgress) {
                downloadInProgress = !futures.stream()
                        .allMatch(Future::isDone);
            }
            Instant end = Instant.now();
            logging(futures, Duration.between(start, end));
            return futures;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.debug(String.format("Thread %s interrupted", Thread.currentThread().getName()));
            throw new PageDownloadException("Thread interrupted");
        } finally {
            executorService.shutdown();
        }
    }

    private List<PageWorker> getWorkers(Config config) {
        return IntStream.rangeClosed(1, config.getInt(NUMBER))
                .mapToObj(pageIndex -> new JsoupPageWorker(config, pageIndex))
                .collect(toList());
    }

    private PageWrapper extractValue(Future<PageWrapper> future) {
        try {
            return future.isCancelled() ? PageWrapper.createFailMessage() : future.get();
        } catch (InterruptedException | ExecutionException e) {
            return PageWrapper.createFailMessage();
        }
    }

    private void logging(List<Future<PageWrapper>> futures, Duration totalDuration) {
        Map<PageWrapper.Status, List<PageWrapper>> stats = futures.stream()
                .map(this::extractValue)
                .collect(Collectors.groupingBy(PageWrapper::getStatus));
        log.info(String.format(
                "%nTotal download time %d ms. " +
                        "%nDowloaded %d pages" +
                        "%nTime/page ratio %d ms" +
                        "%nFailed download %d",
                totalDuration.toMillis(),
                stats.get(PageWrapper.Status.SUCCESS).size(),
                totalDuration.toMillis() / stats.get(PageWrapper.Status.SUCCESS).size(),
                stats.containsKey(PageWrapper.Status.FAIL) ? stats.get(PageWrapper.Status.FAIL).size() : 0));
    }
}
