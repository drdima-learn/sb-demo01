package com.rubincomputers.sb_demo01.web.exception2;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorInfo {
    private final String httpStatus;
    private final String url;
    private final String type;
    private final String details;


    public ErrorInfo(String httpStatus, String url, String type, String details) {
        this.httpStatus = httpStatus;
        this.url = url;
        this.type = type;
        this.details = details;

    }
}
