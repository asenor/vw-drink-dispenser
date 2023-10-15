package com.vw.drink.dispenser.domain;

import java.util.Map;

public class Product {

    private final ProductType type;
    private final Money money;
    private final Timestamp expirationDate;

    private Product(ProductType type, Money money, Timestamp expirationDate) {
        this.type = type;
        this.money = money;
        this.expirationDate = expirationDate;
    }

    public ProductType type() {
        return type;
    }

    public Money getPrice() {
        return money;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public static class Factory {

        private static final Map<ProductType, Money> prices = Map.ofEntries(
                Map.entry(ProductType.COKE, new Money("2.0")),
                Map.entry(ProductType.ORANGE_JUICE, new Money("1.95")),
                Map.entry(ProductType.REDBULL, new Money("2.25")),
                Map.entry(ProductType.WATER, new Money("0.5"))
        );

        public static Product create(ProductType type, Timestamp expirationDate) {
            return new Product(type, prices.get(type), expirationDate);
        }
    }
}
