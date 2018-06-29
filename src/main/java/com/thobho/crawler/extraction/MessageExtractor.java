package com.thobho.crawler.extraction;

import com.thobho.crawler.message.Message;

import java.util.stream.Stream;

public interface MessageExtractor {
	 Stream<Message> extractFromHtml(Stream<String> pages);
}
