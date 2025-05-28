package com.arpangroup.product_service.exception;

public class PurchaseException extends RuntimeException {
    public PurchaseException() {
        super();
    }

    public PurchaseException(String message) {
        super(message);
    }
}
