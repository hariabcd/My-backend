package com.backend.localshare.exception;

import lombok.Getter;

@Getter
public class DatabaseOperationException extends RuntimeException {

    private final String operationDetail;

    public DatabaseOperationException(String message, String operationDetail) {
        super(message);
        this.operationDetail = operationDetail;
    }


}
