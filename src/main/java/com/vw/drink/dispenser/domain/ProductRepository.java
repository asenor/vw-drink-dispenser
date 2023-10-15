package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.exception.InvalidProduct;

public interface ProductRepository {

    void add(Product product) throws InvalidProduct;

    long stock(ProductType productType);
}
