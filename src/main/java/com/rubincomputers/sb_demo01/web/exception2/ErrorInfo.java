package com.rubincomputers.sb_demo01.web.exception2;

import lombok.Data;

@Data
public class ErrorInfo {
    private final String url;
    private final String type;
    private final String details;

    public ErrorInfo(String url, String type, String details) {
        this.url = url;
        this.type = type;
        this.details = details;
    }
}
