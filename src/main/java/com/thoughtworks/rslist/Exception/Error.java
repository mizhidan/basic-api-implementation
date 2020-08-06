package com.thoughtworks.rslist.Exception;

import lombok.Data;

@Data
public class Error {
    public String getError() {
        return error;
    }


    private String error;
}
