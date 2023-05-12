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


    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(BadSortParameters.class)
    public ErrorInfo handleException(BadSortParameters ex, HttpServletRequest req) {
        String fullUrl = getFullUrl(req);

        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_GATEWAY.toString(), fullUrl, ex.getClass().getName(), ex.getMessage());
        log.error("at request {} : cause: {}", fullUrl, ex.getMessage());

        return errorInfo;
    }


}
