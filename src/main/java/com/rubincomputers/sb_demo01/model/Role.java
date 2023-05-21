package com.rubincomputers.sb_demo01.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    USER,
    ADMIN;

    @JsonCreator
    public static Role fromText(String text){
        return Role.valueOf(text.toUpperCase());
    }
}
