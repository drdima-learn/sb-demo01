package com.rubincomputers.sb_demo01.util;

import com.rubincomputers.sb_demo01.model.AbstractBaseEntity;
import com.rubincomputers.sb_demo01.util.exception.IllegalRequestDataException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity + " must be new (id=null)");
        }
    }
}
