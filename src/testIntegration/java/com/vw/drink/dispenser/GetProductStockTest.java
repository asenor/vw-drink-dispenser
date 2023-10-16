package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.product.GetProductStock;
import com.vw.drink.dispenser.domain.time.Timestamp;
import com.vw.drink.dispenser.domain.product.exception.InvalidProductException;
import com.vw.drink.dispenser.domain.product.Product;
import com.vw.drink.dispenser.domain.product.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductStockTest extends BaseIntegrationTest {

    @Autowired
    private GetProductStock getStock;

    @Test
    public void testAddProduct() throws InvalidProductException {
        productRepository.add(Product.Factory.create(ProductType.COKE, new Timestamp(0)));
        productRepository.add(Product.Factory.create(ProductType.WATER, new Timestamp(0)));
        productRepository.add(Product.Factory.create(ProductType.WATER, new Timestamp(0)));

        assertEquals(0, getStock.byType(null));
        assertEquals(1, getStock.byType(ProductType.COKE));
        assertEquals(2, getStock.byType(ProductType.WATER));
    }
}
