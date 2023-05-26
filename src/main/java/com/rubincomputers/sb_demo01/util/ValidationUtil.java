package com.rubincomputers.sb_demo01.util;

import com.rubincomputers.sb_demo01.model.AbstractBaseEntity;
import com.rubincomputers.sb_demo01.util.exception.IllegalRequestDataException;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalRequestDataException(entity + " must be new (id=null)");
        }
    }

    public static void checkNotNew(AbstractBaseEntity entity) {
        if (entity.isNew()) {
            throw new IllegalRequestDataException(entity + " must be new with id");
        }
    }


    public static void checkNotFoundWithId(boolean found, long id) {
        if (!found){
            throw new NotFoundException("id="+id);
        }
    }
}
