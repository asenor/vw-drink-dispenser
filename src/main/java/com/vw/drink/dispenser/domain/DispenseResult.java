package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.exception.DispenseValidationException;

public class DispenseResult {
    public boolean success;
    public Money amountReturned;
    public Product dispensedProduct;
    public DispenseValidationException errorCause;

    private DispenseResult(
        boolean success,
        Money amountReturned,
        Product dispensedProduct,
        DispenseValidationException errorCause
    ) {
        this.success = success;
        this.amountReturned = amountReturned;
        this.dispensedProduct = dispensedProduct;
        this.errorCause = errorCause;
    }

    public static DispenseResult ok(Money amountReturned, Product product) {
        return new DispenseResult(true, amountReturned, product, null);
    }

    public static DispenseResult error(Money amountReturned, DispenseValidationException errorCause) {
        return new DispenseResult(false, amountReturned, null, errorCause);
    }
}
