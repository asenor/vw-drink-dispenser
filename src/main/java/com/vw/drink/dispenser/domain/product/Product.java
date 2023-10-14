package com.vw.drink.dispenser.domain.product;

import com.vw.drink.dispenser.domain.Price;
import com.vw.drink.dispenser.domain.Timestamp;

public abstract class Product {

    private Price price;
    private Timestamp expirationDate;

    public Product(Price price, Timestamp expirationDate) {
        this.price = price;
        this.expirationDate = expirationDate;
    }
}
