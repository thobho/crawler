package com.thobho.crawler.persist.file;

import com.google.gson.GsonBuilder;
import com.thobho.crawler.configuration.configuration.Config;
import com.thobho.crawler.message.Message;
import com.thobho.crawler.persist.MessageSaver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.Stream;

import static com.thobho.crawler.configuration.configuration.DefaultParamKey.OUTFILE;
import static java.util.stream.Collectors.toList;

public class FileMessageSaver implements MessageSaver {
    @Override
    public void save(Stream<Message> messages, Config config) throws FileNotFoundException {
        String messagesToSave = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(messages.collect(toList()));
        try (PrintWriter out = new PrintWriter(config.getString(OUTFILE))) {
            out.println(messagesToSave);
        }
    }
}
