package com.thobho.crawler.extraction.jsoup;

import java.util.stream.Stream;

import com.thobho.crawler.extraction.MessageExtractor;
import com.thobho.crawler.message.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class JsoupMessageExtractor implements MessageExtractor {

    public JsoupMessageExtractor() {
    }

    @Override
    public Stream<Message> extractFromHtml(Stream<String> pagesStream) {
        return pagesStream.flatMap(page ->
                Jsoup.parse(page)
                        .getElementsByClass("post").stream()
                        .map(this::elementToMessage)
        );
    }

    private Message elementToMessage(Element element) {
        String id = element.id().substring(1, element.id().length());
        String points = element.getElementsByClass("points").text();
        String post = element.getElementsByClass("post").text();
        return new Message(post, Long.parseLong(id), Long.parseLong(points));
    }
}
