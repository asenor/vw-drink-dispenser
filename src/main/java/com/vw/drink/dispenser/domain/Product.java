package com.vw.drink.dispenser.domain;

public class Product {

    private final ProductType type;
    private final Money price;
    private final Timestamp expirationDate;

    private Product(ProductType type, Money price, Timestamp expirationDate) {
        this.type = type;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    public ProductType type() {
        return type;
    }

    public Money price() {
        return price;
    }

    public boolean isExpired(Timestamp now) {
        return expirationDate.isBefore(now);
    }

    public static class Factory {
        public static Product create(ProductType type, Timestamp expirationDate) {
            return new Product(type, ProductPrice.forType(type), expirationDate);
        }
    }
}
