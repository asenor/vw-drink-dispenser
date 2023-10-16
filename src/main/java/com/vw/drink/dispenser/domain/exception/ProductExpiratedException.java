package com.vw.drink.dispenser.domain.exception;

import com.vw.drink.dispenser.domain.ProductType;

public class ProductExpiratedException extends DispenseValidationException {

    public ProductExpiratedException(ProductType type) {
        super(String.format("There is no %s available at this moment", type));
    }
}
