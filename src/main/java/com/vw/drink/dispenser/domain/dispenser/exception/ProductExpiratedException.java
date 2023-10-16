package com.vw.drink.dispenser.domain.dispenser.exception;

import com.vw.drink.dispenser.domain.product.ProductType;

public class ProductExpiratedException extends DispenseValidationException {

    public ProductExpiratedException(ProductType type) {
        super(String.format("There is no %s available at this moment", type));
    }
}
