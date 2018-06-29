package com.thobho.crawler.datasource.http;

import java.util.concurrent.Callable;

interface PageWorker extends Callable<PageWrapper> {
}
