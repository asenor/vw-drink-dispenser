package com.vw.drink.dispenser.domain.product;

import com.vw.drink.dispenser.domain.Price;
import com.vw.drink.dispenser.domain.Timestamp;

public class Coke extends Product {

    private static final double PRICE = 2.0;

    public Coke(Timestamp expirationDate) {
        super(new Price(PRICE), expirationDate);
    }
}
