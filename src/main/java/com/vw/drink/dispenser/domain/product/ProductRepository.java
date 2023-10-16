package com.vw.drink.dispenser.domain.product;

import com.vw.drink.dispenser.domain.product.exception.InvalidProductException;
import com.vw.drink.dispenser.domain.product.exception.NoUnexpiredProductException;

public interface ProductRepository {

    void add(Product product) throws InvalidProductException;

    long stock(ProductType productType);

    boolean hasStock(ProductType type);

    boolean hasAnyUnexpired(ProductType type);

    Product pickUnexpiredProduct(ProductType type) throws NoUnexpiredProductException;
}
