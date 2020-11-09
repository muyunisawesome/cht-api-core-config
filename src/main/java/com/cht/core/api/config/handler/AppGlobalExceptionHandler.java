package com.cht.core.api.config.handler;

import com.cht.Response;
import com.cht.core.api.config.exception.AppGlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Order(1)
@ControllerAdvice
public class AppGlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(AppGlobalExceptionHandler.class);

    public AppGlobalExceptionHandler() {
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response validExceptionHandle(Exception ex) {
        String message;
        if (ex instanceof BindException) {
            BindException bingException = (BindException)ex;
            message = ((ObjectError)bingException.getAllErrors().get(0)).getDefaultMessage();
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException)ex;
            message = ((ObjectError)argumentNotValidException.getBindingResult().getAllErrors().get(0)).getDefaultMessage();
        } else if (ex instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException notReadableException = (HttpMessageNotReadableException)ex;
            message = notReadableException.getMessage();
        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException validationException = (ConstraintViolationException)ex;
            Set<ConstraintViolation<?>> violationErrors = validationException.getConstraintViolations();
            ConstraintViolation<?> error = (ConstraintViolation)violationErrors.iterator().next();
            message = error.getMessage();
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            HttpRequestMethodNotSupportedException methodNotSupportedException = (HttpRequestMethodNotSupportedException)ex;
            message = methodNotSupportedException.getMessage();
        } else {
            if (ex instanceof AppGlobalException) {
                AppGlobalException appGlobalException = (AppGlobalException)ex;
                return appGlobalException.getRes();
            }

            message = "系统异常500:" + ex.getMessage();
        }

        this.logger.error("系统异常500:", ex);
        return Response.createError(message);
    }
}
