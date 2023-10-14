package com.vw.drink.dispenser.application.exception;

import com.vw.drink.dispenser.domain.product.Product;

public class InvalidProduct extends Exception {

    public InvalidProduct(Product product) {
        super("InvalidProduct: " + product);
    }
}
