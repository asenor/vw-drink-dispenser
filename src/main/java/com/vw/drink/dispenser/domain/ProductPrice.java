package com.vw.drink.dispenser.domain;

import java.util.Map;

public class ProductPrice {

    private static final Map<ProductType, Money> prices = Map.ofEntries(
        Map.entry(ProductType.COKE, new Money("2.00")),
        Map.entry(ProductType.ORANGE_JUICE, new Money("1.95")),
        Map.entry(ProductType.REDBULL, new Money("2.25")),
        Map.entry(ProductType.WATER, new Money("0.50"))
    );

    public static Money forType(ProductType type) {
        return prices.get(type);
    }
}
