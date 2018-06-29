package com.thobho.crawler.extraction.jsoup;


import com.thobho.crawler.message.Message;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


public class JsoupMessageExtractorTest {
    
    private JsoupMessageExtractor jsoupMessageExtractor;
    
    @Test
    public void extractFromHtml_shouldExtractOne(){

        Stream<String> inputHtmlStream = Stream.of(pageFragment());

        jsoupMessageExtractor = new JsoupMessageExtractor();
        Stream<Message> messageStream = jsoupMessageExtractor.extractFromHtml(inputHtmlStream);
        List<Message> collect = messageStream.collect(Collectors.toList());
        Message message = new Message(
             "30 kwietnia 2018 12:02 #4863013 + 103 -   <aga> Pytanie do Panow. Jak pozbyc sie odoru rozlanego mleka w samochodzie? <fakin> polykaj :D",
                4863013,
                103
        );

        assertEquals(1, collect.size());
        assertEquals(message, collect.get(0));
    }
    
    private String pageFragment(){
        return "<div id=\"d4863013\" class=\"q post\">\n" +
                "\t\t\t<div class=\"bar\">\n" +
                "\t\t\t\t<div class=\"right\">\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t30 kwietnia 2018 12:02\n" +
                "\n" +
                "\t\t\t\t\t\n" +
                "\n" +
                "\t\t\t\t\t\n" +
                "\n" +
                "\t\t\t\t</div>\n" +
                "\n" +
                "\t\t\t\t<a class=\"qid click\" href=\"/4863013/\">#4863013</a>\n" +
                "\t\t\t\t<a class=\"click votes rox\" rel=\"nofollow\" href=\"/rox/4863013/\">+</a>\n" +
                "\t\t\t\t<span class=\" points\" style=\"visibility: hidden;\">103</span>\n" +
                "\t\t\t\t<a class=\"click votes sux\" rel=\"nofollow\" href=\"/sux/4863013/\">-</a><a class=\"fbshare\" href=\"http://www.facebook.com/sharer.php?u=http%3A%2F%2Fbash.org.pl%2F4863013%2F&amp;t=%0A%09%09%09%09%3Caga%3E%20Pytanie%20do%20Panow.%20Jak%20pozbyc%20sie%20odoru%20rozlanego%20mleka%20w%20samochodzie%3F%0A%0A%3Cfakin%3E%20polykaj%20%3AD%0A%09%09%09\"></a>\n" +
                "\t\t\t\t<span class=\"msg\">&nbsp;</span>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div class=\"quote post-content post-body\">\n" +
                "\t\t\t\t&lt;aga&gt; Pytanie do Panow. Jak pozbyc sie odoru rozlanego mleka w samochodzie?\n" +
                "<br>\n" +
                "&lt;fakin&gt; polykaj :D\n" +
                "\t\t\t</div>\n" +
                "\n" +
                "\t\t\t\n" +
                "\n" +
                "\t\t\t\n" +
                "\n" +
                "\t\t\t\n" +
                "\t\t</div>";
    }
}
