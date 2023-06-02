package com.rubincomputers.sb_demo01.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.rubincomputers.sb_demo01.util.WebUtil.getFullUrl;

@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(Exception e, HttpServletRequest req) throws Exception {

        Throwable rootCause = e;

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String url = getFullUrl(req);
        ModelAndView mav = new ModelAndView("exception",
                Map.of("url", url,
                        "exception", rootCause,
                        "message", e.getClass().getName() + "   :::  " + e.getMessage(),
                        "status", httpStatus));
        mav.setStatus(httpStatus);


        return mav;
    }
}
