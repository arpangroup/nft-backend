package com.arpangroup.user_service.transaction;

public class TransactionException extends RuntimeException{
    public TransactionException(String message) {
        super(message);
    }
}
