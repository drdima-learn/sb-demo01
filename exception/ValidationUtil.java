package com.rubincomputers.sb_demo01.exception;

import org.slf4j.Logger;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;

public class ValidationUtil {


    private ValidationUtil() {
    }


    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest req, Exception e, boolean logStackTrace, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logStackTrace) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }
}