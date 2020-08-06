package com.thoughtworks.rslist.Exception;

public class RsEventNotValidExcption extends RuntimeException {
    private String errorMessage;
    public RsEventNotValidExcption(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }
}
