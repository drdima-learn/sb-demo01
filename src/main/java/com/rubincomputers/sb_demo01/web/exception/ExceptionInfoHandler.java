package com.rubincomputers.sb_demo01.web.exception;

import com.rubincomputers.sb_demo01.util.exception.BadSortParameter;
import com.rubincomputers.sb_demo01.util.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.rubincomputers.sb_demo01.util.WebUtil.getFullUrl;


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

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(BindException.class)
    public ErrorInfo bindValidationError(BindException ex, HttpServletRequest req) {
        String[] details = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .toArray(String[]::new);

        return handleException(ex, req, HttpStatus.BAD_REQUEST, details);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleExceptionGlobal(Exception ex, HttpServletRequest req) {
        return handleException(ex, req, HttpStatus.BAD_REQUEST);
    }

    private static ErrorInfo handleException(Exception ex, HttpServletRequest req, HttpStatus httpStatus, String... details){
        String fullUrl = getFullUrl(req);
        ErrorInfo errorInfo = new ErrorInfo(httpStatus.toString(), fullUrl, ex.getClass().getName(),
                details.length != 0 ? details : new String[]{ex.getMessage()}
        );
        log.error("at request {} : cause: {}", fullUrl, ex.getMessage());
        return errorInfo;
    }


}
