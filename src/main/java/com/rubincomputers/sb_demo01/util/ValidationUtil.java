package com.rubincomputers.sb_demo01.util;

import com.rubincomputers.sb_demo01.HasId;
import com.rubincomputers.sb_demo01.service.dto.BaseDTO;
import com.rubincomputers.sb_demo01.model.AbstractBaseEntity;
import com.rubincomputers.sb_demo01.util.exception.IllegalRequestDataException;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void checkNotNew(HasId bean) {
        if (bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new with id");
        }
    }


    public static void checkNotFound(boolean found, long id) {
        if (!found) {
            throw new NotFoundException("id=" + id);
        }
    }

    public static void checkNotFound(boolean found, String identifier) {
        if (!found) {
            throw new NotFoundException("identifier=" + identifier);
        }
    }

    public static void assureIdConsistent(BaseDTO bean, long id) {
        // conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() !=null && bean.getId() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }
}
