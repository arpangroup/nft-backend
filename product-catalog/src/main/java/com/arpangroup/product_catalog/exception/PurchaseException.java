package com.arpangroup.product_catalog.exception;

public class PurchaseException extends RuntimeException {
    public PurchaseException() {
        super();
    }

    public PurchaseException(String message) {
        super(message);
    }
}
