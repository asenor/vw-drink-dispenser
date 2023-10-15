package com.vw.drink.dispenser.domain;

public interface ProductRepository {

    void add(Product product) throws InvalidProduct;

    long stock(ProductType productType);
}
