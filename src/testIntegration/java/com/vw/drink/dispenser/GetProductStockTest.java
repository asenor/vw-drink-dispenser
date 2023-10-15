package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.GetProductStock;
import com.vw.drink.dispenser.domain.Product;
import com.vw.drink.dispenser.domain.ProductType;
import com.vw.drink.dispenser.domain.Timestamp;
import com.vw.drink.dispenser.domain.exception.InvalidProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductStockTest extends BaseIntegrationTest {

    @Autowired
    private GetProductStock getStock;

    @Test
    public void testAddProduct() throws InvalidProduct {
        productRepository.add(Product.Factory.create(ProductType.COKE, new Timestamp(0)));
        productRepository.add(Product.Factory.create(ProductType.WATER, new Timestamp(0)));
        productRepository.add(Product.Factory.create(ProductType.WATER, new Timestamp(0)));

        assertEquals(0, getStock.byType(null));
        assertEquals(1, getStock.byType(ProductType.COKE));
        assertEquals(2, getStock.byType(ProductType.WATER));
    }
}
