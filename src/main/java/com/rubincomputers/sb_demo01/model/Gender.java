package com.rubincomputers.sb_demo01.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Gender {
    MALE,
    FEMALE,
    NEUTRAL,
    NON_BINARY,
    TRANSGENDER;

    @JsonCreator
    public static Gender fromText(String text){
        return Gender.valueOf(text.toUpperCase());
    }

}
