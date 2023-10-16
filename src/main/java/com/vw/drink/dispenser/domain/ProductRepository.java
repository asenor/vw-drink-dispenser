package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.exception.InvalidProductException;
import com.vw.drink.dispenser.domain.exception.NoUnexpiredProductException;

public interface ProductRepository {

    void add(Product product) throws InvalidProductException;

    long stock(ProductType productType);

    boolean hasStock(ProductType type);

    boolean hasAnyUnexpired(ProductType type);

    Product pickUnexpiredProduct(ProductType type) throws NoUnexpiredProductException;
}
