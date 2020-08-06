package com.thoughtworks.rslist.component;

import com.thoughtworks.rslist.Exception.Error;
import com.thoughtworks.rslist.Exception.RsEventNotValidExcption;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class ErrorExceptionHandler {

    private static final String logExceptionFormat = "Capture Exception By GlobalExceptionHandler - Detail: %s";
    private static Logger log = LoggerFactory.getLogger(ErrorExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MethodArgumentNotValidException.class, RsEventNotValidExcption.class})
    public ResponseEntity RsEventExceptionHandle(Exception e) {
        String errorMessage = "";
        if (e instanceof  MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            if (methodArgumentNotValidException.getBindingResult().getTarget() instanceof RsEvent) {
                errorMessage = "invalid param";
            }
            if (methodArgumentNotValidException.getBindingResult().getTarget() instanceof User) {
                errorMessage = "invalid user";
            }
        } else {
            errorMessage = e.getMessage();
        }

        Error error = new Error();
        error.setError(errorMessage);

        log.error(String.format(logExceptionFormat, errorMessage));

        return ResponseEntity.badRequest().body(error);
    }
}
