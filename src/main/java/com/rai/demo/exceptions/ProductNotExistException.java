package com.rai.demo.exceptions;

public class ProductNotExistException extends IllegalArgumentException {
    public ProductNotExistException(String msg) {
        super(msg);
    }
}
