package com.vw.drink.dispenser.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    @Test
    public void productIsExpired() {
        var product = Product.Factory.create(ProductType.WATER, new Timestamp(2));

        assertFalse(product.isExpired(new Timestamp(1)));
        assertFalse(product.isExpired(new Timestamp(2)));
        assertTrue(product.isExpired(new Timestamp(3)));
    }
}
