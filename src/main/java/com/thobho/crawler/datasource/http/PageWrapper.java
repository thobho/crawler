package com.thobho.crawler.datasource.http;

class PageWrapper {
    private String pageSource;
    private long time;
    private Status status;

    PageWrapper(String pageSource, long time, Status status) {
        this.pageSource = pageSource;
        this.time = time;
        this.status = status;
    }

    PageWrapper(Status status) {
        this.status = status;
    }

    public String getPageSource() {
        return pageSource;
    }

    public long getTime() {
        return time;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isSuccessful(){
        return Status.SUCCESS.equals(this.status);
    }

    public static PageWrapper createFailMessage() {
        return new PageWrapper(Status.FAIL);
    }

    enum Status {
        FAIL, SUCCESS
    }
}
