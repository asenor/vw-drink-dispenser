package com.vw.drink.dispenser.domain.dispenser.exception;

public class ProductNotSelectedException extends Exception {

    public ProductNotSelectedException() {
        super("You must select a product before introducing coins");
    }
}
