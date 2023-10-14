package com.vw.drink.dispenser.domain.product;

import com.vw.drink.dispenser.domain.Price;
import com.vw.drink.dispenser.domain.Timestamp;

public class Water extends Product {

    private static final double PRICE = 0.5;

    public Water(Timestamp expirationDate) {
        super(new Price(PRICE), expirationDate);
    }
}
