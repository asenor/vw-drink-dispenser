package com.vw.drink.dispenser.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DispenserTest {

    @Test
    public void testAddProducts() throws InvalidProduct {
        var dispenser = new Dispenser(List.of());

        dispenser.addProduct(Product.Factory.create(ProductType.COKE, new Timestamp(0)));
        dispenser.addProduct(Product.Factory.create(ProductType.COKE, new Timestamp(0)));

        assertEquals(2, dispenser.productStock(ProductType.COKE));

        assertThrows(InvalidProduct.class, () -> {
           dispenser.addProduct(null);
        });
    }

    @Test
    public void testProductStock() {
        var dispenser = new Dispenser(List.of(
            Product.Factory.create(ProductType.COKE, new Timestamp(5)),
            Product.Factory.create(ProductType.COKE, new Timestamp(10)),
            Product.Factory.create(ProductType.COKE, new Timestamp(15)),
            Product.Factory.create(ProductType.WATER, new Timestamp(50)),
            Product.Factory.create(ProductType.WATER, new Timestamp(60))
        ));

        assertEquals(3, dispenser.productStock(ProductType.COKE));
        assertEquals(2, dispenser.productStock(ProductType.WATER));
    }
}
