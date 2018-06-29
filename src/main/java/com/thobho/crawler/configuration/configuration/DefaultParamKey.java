package com.thobho.crawler.configuration.configuration;

public enum DefaultParamKey implements ParamKey {
    NUMBER("number"),
    THREADS("thread"),
    TIMEOUT("timeout"),
    ADDRESS("address"),
    POSTFIX("postfix"),
    OUTFILE("outfile");

    private String key;

    DefaultParamKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
