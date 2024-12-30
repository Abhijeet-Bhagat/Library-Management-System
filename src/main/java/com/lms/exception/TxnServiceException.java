package com.lms.exception;

public class TxnServiceException extends RuntimeException{

    public TxnServiceException(String message) {
        super(message);
    }
}
