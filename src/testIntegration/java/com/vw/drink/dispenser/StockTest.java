package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.Stock;
import com.vw.drink.dispenser.domain.InvalidProduct;
import com.vw.drink.dispenser.domain.Product;
import com.vw.drink.dispenser.domain.ProductType;
import com.vw.drink.dispenser.domain.Timestamp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest extends BaseIntegrationTest {

    @Autowired
    private Stock stock;

    @Test
    public void testAddProduct() throws InvalidProduct {
        dispenser.addProduct(Product.Factory.create(ProductType.COKE, new Timestamp(0)));
        dispenser.addProduct(Product.Factory.create(ProductType.WATER, new Timestamp(0)));
        dispenser.addProduct(Product.Factory.create(ProductType.WATER, new Timestamp(0)));

        assertEquals(0, stock.byProduct(null));
        assertEquals(1, stock.byProduct(ProductType.COKE));
        assertEquals(2, stock.byProduct(ProductType.WATER));
    }
}
