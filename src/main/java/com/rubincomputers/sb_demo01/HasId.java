package com.rubincomputers.sb_demo01;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface HasId {
    Long getId();

    void setId(Long id);

    @JsonIgnore
    default boolean isNew() {
        return getId() == null;
    }


}
