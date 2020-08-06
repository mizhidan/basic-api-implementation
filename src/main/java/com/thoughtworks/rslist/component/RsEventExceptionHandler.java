package com.thoughtworks.rslist.component;

import com.thoughtworks.rslist.Exception.Error;
import com.thoughtworks.rslist.Exception.RsEventNotValidExcption;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RsEventExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, RsEventNotValidExcption.class})
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

        return ResponseEntity.badRequest().body(error);
    }
}
