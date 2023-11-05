package com.demo.devsrc.filemanager.error;

import lombok.Getter;

@Getter
public class IllegalRequestDataException extends RuntimeException {

    public IllegalRequestDataException(String message) {
        super(message);
    }

    public IllegalRequestDataException(String message, Exception ex) {
        super(message, ex);
    }
}
