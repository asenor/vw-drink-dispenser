package com.vw.drink.dispenser.domain.product;

import com.vw.drink.dispenser.domain.Price;
import com.vw.drink.dispenser.domain.Timestamp;

public class Redbull extends Product {

    private static final double PRICE = 2.25;

    public Redbull(Timestamp expirationDate) {
        super(new Price(PRICE), expirationDate);
    }
}
