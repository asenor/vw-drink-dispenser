package com.vw.drink.dispenser.domain.dispenser.exception;

import com.vw.drink.dispenser.domain.product.ProductType;

public class ProductWithoutStockException extends DispenseValidationException {

    public ProductWithoutStockException(ProductType type) {
        super(String.format("Product %s does not have stock", type.name()));
    }
}
