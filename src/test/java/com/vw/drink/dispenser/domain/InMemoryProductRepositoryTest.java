package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.exception.InvalidProductException;
import com.vw.drink.dispenser.domain.exception.NoUnexpiredProductException;
import com.vw.drink.dispenser.infrastructure.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InMemoryProductRepositoryTest {

    @Mock
    private Time time;

    private InMemoryProductRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository = new InMemoryProductRepository(List.of(), time);
    }

    @Test
    public void addProductsAndStock() throws InvalidProductException {
        repository.add(Product.Factory.create(ProductType.COKE, new Timestamp(0)));
        repository.add(Product.Factory.create(ProductType.COKE, new Timestamp(0)));
        repository.add(Product.Factory.create(ProductType.WATER, new Timestamp(0)));

        assertThrows(InvalidProductException.class, () -> {
            repository.add(null);
        });

        assertEquals(2, repository.stock(ProductType.COKE));
        assertEquals(1, repository.stock(ProductType.WATER));
        assertEquals(0, repository.stock(ProductType.ORANGE_JUICE));

        assertTrue(repository.hasStock(ProductType.COKE));
        assertTrue(repository.hasStock(ProductType.WATER));
        assertFalse(repository.hasStock(ProductType.ORANGE_JUICE));
    }

    @Test
    public void hasAnyUnexpired() throws InvalidProductException {
        repository.add(Product.Factory.create(ProductType.COKE, new Timestamp(1)));
        repository.add(Product.Factory.create(ProductType.COKE, new Timestamp(2)));
        repository.add(Product.Factory.create(ProductType.WATER, new Timestamp(1)));

        when(time.now()).thenReturn(new Timestamp(2));

        assertTrue(repository.hasAnyUnexpired(ProductType.COKE));
        assertFalse(repository.hasAnyUnexpired(ProductType.WATER));
        assertFalse(repository.hasAnyUnexpired(ProductType.ORANGE_JUICE));
    }

    @Test
    public void pickUnexpiredProduct() throws NoUnexpiredProductException, InvalidProductException {
        repository.add(Product.Factory.create(ProductType.WATER, new Timestamp(1)));
        repository.add(Product.Factory.create(ProductType.WATER, new Timestamp(3)));

        when(time.now()).thenReturn(new Timestamp(2));

        assertTrue(repository.hasStock(ProductType.WATER));
        assertTrue(repository.hasAnyUnexpired(ProductType.WATER));

        repository.pickUnexpiredProduct(ProductType.WATER);

        assertTrue(repository.hasStock(ProductType.WATER));
        assertFalse(repository.hasAnyUnexpired(ProductType.WATER));
    }
}
