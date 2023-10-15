package com.vw.drink.dispenser.domain;

import java.util.Map;

public class Product {

    private final ProductType type;
    private final Price price;
    private final Timestamp expirationDate;

    private Product(ProductType type, Price price, Timestamp expirationDate) {
        this.type = type;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    public ProductType type() {
        return type;
    }

    public Price getPrice() {
        return price;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public static class Factory {

        private static final Map<ProductType, Price> prices = Map.ofEntries(
                Map.entry(ProductType.COKE, new Price(2.0)),
                Map.entry(ProductType.ORANGE_JUICE, new Price(1.95)),
                Map.entry(ProductType.REDBULL, new Price(2.25)),
                Map.entry(ProductType.WATER, new Price(0.5))
        );

        public static Product create(ProductType type, Timestamp expirationDate) {
            return new Product(type, prices.get(type), expirationDate);
        }
    }
}
