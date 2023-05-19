package com.rubincomputers.sb_demo01.web.exception2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.rubincomputers.sb_demo01.web.util.Util.getFullUrl;


@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadSortParameter.class)
    public ErrorInfo handleException(BadSortParameter ex, HttpServletRequest req) {
        return handleException(ex, req, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleExceptionNotFound(NotFoundException ex, HttpServletRequest req) {
        return handleException(ex, req, HttpStatus.NOT_FOUND);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleExceptionGlobal(Exception ex, HttpServletRequest req) {
        return handleException(ex, req, HttpStatus.BAD_REQUEST);
    }

    private static ErrorInfo handleException(Exception ex, HttpServletRequest req, HttpStatus httpStatus){
        String fullUrl = getFullUrl(req);
        ErrorInfo errorInfo = new ErrorInfo(httpStatus.toString(), fullUrl, ex.getClass().getName(), ex.getMessage());
        log.error("at request {} : cause: {}", fullUrl, ex.getMessage());
        return errorInfo;
    }


}
