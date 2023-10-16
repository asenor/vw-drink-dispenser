package com.vw.drink.dispenser.domain.exception;

import com.vw.drink.dispenser.domain.ProductType;

public class ProductWithoutStockException extends DispenseValidationException {

    public ProductWithoutStockException(ProductType type) {
        super(String.format("Product %s does not have stock", type.name()));
    }
}
