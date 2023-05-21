package com.rubincomputers.sb_demo01.web.exception;

import lombok.Data;

@Data
public class ErrorInfo {
    private final String httpStatus;
    private final String url;
    private final String exception;
    private final String[] details;


    public ErrorInfo(String httpStatus, String url, String exception, String... details) {
        this.httpStatus = httpStatus;
        this.url = url;
        this.exception = exception;
        this.details = details;

    }
}
