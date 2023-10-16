package com.vw.drink.dispenser.domain.product.exception;

import com.vw.drink.dispenser.domain.product.ProductType;

public class NoUnexpiredProductException extends Exception {

    public NoUnexpiredProductException(ProductType type) {
        super(String.format("There's no unexpired %s ", type));
    }
}
