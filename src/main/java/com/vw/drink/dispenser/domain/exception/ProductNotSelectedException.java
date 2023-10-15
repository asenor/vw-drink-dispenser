package com.vw.drink.dispenser.domain.exception;

public class ProductNotSelectedException extends Exception {

    public ProductNotSelectedException() {
        super("You must select a product before introducing coins");
    }
}
