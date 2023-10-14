package com.vw.drink.dispenser.domain.product;

import com.vw.drink.dispenser.domain.Price;
import com.vw.drink.dispenser.domain.Timestamp;

public class OrangeJuice extends Product {

    private static final double PRICE = 1.95;

    public OrangeJuice(Timestamp expirationDate) {
        super(new Price(PRICE), expirationDate);
    }
}
