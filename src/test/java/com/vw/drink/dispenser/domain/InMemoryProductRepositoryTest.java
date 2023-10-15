package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.infrastructure.InMemoryProductRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryProductRepositoryTest {

    @Test
    public void testAddProducts() throws InvalidProduct {
        var repository = new InMemoryProductRepository(List.of());

        repository.add(Product.Factory.create(ProductType.COKE, new Timestamp(0)));
        repository.add(Product.Factory.create(ProductType.COKE, new Timestamp(0)));

        assertEquals(2, repository.stock(ProductType.COKE));

        assertThrows(InvalidProduct.class, () -> {
           repository.add(null);
        });
    }

    @Test
    public void testProductStock() {
        var repository = new InMemoryProductRepository(List.of(
            Product.Factory.create(ProductType.COKE, new Timestamp(5)),
            Product.Factory.create(ProductType.COKE, new Timestamp(10)),
            Product.Factory.create(ProductType.COKE, new Timestamp(15)),
            Product.Factory.create(ProductType.WATER, new Timestamp(50)),
            Product.Factory.create(ProductType.WATER, new Timestamp(60))
        ));

        assertEquals(3, repository.stock(ProductType.COKE));
        assertEquals(2, repository.stock(ProductType.WATER));
    }
}
