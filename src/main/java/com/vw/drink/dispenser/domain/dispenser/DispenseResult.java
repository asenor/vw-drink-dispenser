package com.vw.drink.dispenser.domain.dispenser;

import com.vw.drink.dispenser.domain.money.Money;
import com.vw.drink.dispenser.domain.product.Product;

public class DispenseResult {
    public Money amountReturned;
    public Product dispensedProduct;
    public boolean error;
    public Throwable errorCause;

    private DispenseResult(
        Money amountReturned,
        Product dispensedProduct,
        boolean error,
        Throwable errorCause
    ) {
        this.amountReturned = amountReturned;
        this.dispensedProduct = dispensedProduct;
        this.error = error;
        this.errorCause = errorCause;
    }

    public static DispenseResult ok(Money amountReturned, Product product) {
        return new DispenseResult(amountReturned, product, false, null);
    }

    public static DispenseResult ok(Money amountReturned) {
        return new DispenseResult(amountReturned, null, false, null);
    }

    public static DispenseResult error(Money amountReturned, Throwable errorCause) {
        return new DispenseResult(amountReturned, null, true, errorCause);
    }
}
