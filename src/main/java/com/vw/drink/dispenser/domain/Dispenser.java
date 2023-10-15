package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.exception.ProductNotSelectedException;
import org.springframework.stereotype.Component;

@Component
public class Dispenser {

    private Status status = Status.AVAILABLE;
    private ProductType selectedProduct;
    private Money amountIntroduced = new Money("0.00");

    public void selectProduct(ProductType productType) {
        selectedProduct = productType;
        status = Status.PRODUCT_SELECTED;
    }

    public void introduceCoin(Coin coin) throws ProductNotSelectedException {
        if (status != Status.PRODUCT_SELECTED) {
            throw new ProductNotSelectedException();
        }

        amountIntroduced = amountIntroduced.plus(coin.amount);
    }

    public Status status() {
        return status;
    }

    public ProductType selectedProduct() {
        return selectedProduct;
    }

    public Money amountIntroduced() {
        return amountIntroduced;
    }

    public enum Status {
        AVAILABLE,
        PRODUCT_SELECTED,
        OUT_OF_ORDER;
    }
}
