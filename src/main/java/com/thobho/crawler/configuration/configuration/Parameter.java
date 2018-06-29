package com.thobho.crawler.configuration.configuration;

public abstract class Parameter {
    protected String rawValue;
    protected String validationErrorMessage;

    public Parameter(String validationErrorMessage, String rawValue) {
        this.validationErrorMessage = validationErrorMessage;
        this.rawValue = rawValue;
    }

    abstract void parse();

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getRawValue() {
        return rawValue;
    }

    public String getValidationErrorMessage() {
        return validationErrorMessage;
    }

}

