package com.rubincomputers.sb_demo01.web.exception2;

public class NotFoundException extends GeneralException {

    public NotFoundException() {
        super("Entity not found");
    }
    public NotFoundException(String message) {
        super("Entity not found " + message);
    }
}
