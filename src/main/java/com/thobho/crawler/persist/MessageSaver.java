package com.thobho.crawler.persist;

import com.thobho.crawler.configuration.configuration.Config;
import com.thobho.crawler.message.Message;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

public interface MessageSaver {
    void save(Stream<Message> toSave, Config configuration) throws FileNotFoundException;
}
