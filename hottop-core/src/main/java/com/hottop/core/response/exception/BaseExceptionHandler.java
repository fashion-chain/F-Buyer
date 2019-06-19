package com.hottop.core.response.exception;

import com.google.gson.JsonParseException;
import com.hottop.core.request.argument.field.exception.FieldExistsException;
import com.hottop.core.request.argument.field.exception.FieldNotFoundException;
import com.hottop.core.request.argument.view.exception.ViewExistsException;
import com.hottop.core.request.argument.view.exception.ViewNotFoundException;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import com.hottop.core.security.validate.code.ValidateCodeException;
import com.hottop.core.utils.CommonUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class BaseExceptionHandler {
//        extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    private ResponseEntity<Response> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_INTERVAL)
                .simpleMessage(ex.getMessage())
                //.error(CommonUtil.printStackTraceElements(ex.getStackTrace()))
                .create(), HttpStatus.OK);
    }


    //validate ExceptionHandler 处理验证码异常
    @ExceptionHandler({ValidateCodeException.class})
    private ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(ValidateCodeException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_INTERVAL)
                .message(ex.getMessage())
                .error(CommonUtil.printStackTraceElements(ex.getStackTrace()))
                .create(), HttpStatus.OK);
    }

    //NonceExpiredException 处理token失效异常
    @ExceptionHandler({NonceExpiredException.class})
    private ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(NonceExpiredException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_INTERVAL)
                .message(ex.getMessage())
                .error(CommonUtil.printStackTraceElements(ex.getStackTrace()))
                .create(), HttpStatus.OK);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    private ResponseEntity<Response> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_INTERVAL)
                .message(ex.getMessage())
                .error(CommonUtil.printStackTraceElements(ex.getStackTrace()))
                .create(), HttpStatus.OK);
    }

    @ExceptionHandler({PersistenceException.class})
    private ResponseEntity<Response> handlePersistenceException(PersistenceException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_INTERVAL)
                .simpleMessage(String.format("%s : %s", ex.getCause().getMessage(), ex.getCause().getCause().getMessage()))
                .error(CommonUtil.printStackTraceElements(ex.getStackTrace()))
                .create(), HttpStatus.OK);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    private ResponseEntity<Response> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_INTERVAL)
                .message()
                .error(ex.getMessage())
                .create(),
                HttpStatus.OK);
    }

    @ExceptionHandler({JsonParseException.class})
    private ResponseEntity<Response> handleJsonParseException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_REQUEST_ARGUMENT_PARSE)
                .message(ex.getMessage())
                .error(CommonUtil.printStackTraceElements(ex.getStackTrace()))
                .create(),
                HttpStatus.OK);
    }

    @ExceptionHandler({NotFoundException.class})
    private ResponseEntity<Response> handleNotFoundException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_INTERVAL_NOT_FOUND)
                .message(ex.getMessage())
                .error(CommonUtil.printStackTraceElements(ex.getStackTrace()))
                .create(),
                HttpStatus.OK);
    }

    @ExceptionHandler({FieldExistsException.class, FieldNotFoundException.class, ViewNotFoundException.class, ViewExistsException.class})
    private ResponseEntity<Response> handleViewExceptions(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_REQUEST_ARGUMENT_FILTER)
                .message(ex.getMessage())
                .error(CommonUtil.printStackTraceElements(ex.getStackTrace()))
                .create(),
                HttpStatus.OK);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    private ResponseEntity<Response> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        ex.printStackTrace();
        return new ResponseEntity<Response>(Response.ResponseBuilder
                .result(EResponseResult.ERROR_REQUEST_ARGUMENT_FILTER)
                .message(ex.getMessage())
                .error(CommonUtil.printStackTraceElements(ex.getStackTrace()))
                .create(),
                HttpStatus.OK);
    }
}
