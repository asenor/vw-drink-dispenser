package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.Stock;
import com.vw.drink.dispenser.application.exception.InvalidProduct;
import com.vw.drink.dispenser.domain.Timestamp;
import com.vw.drink.dispenser.domain.product.Coke;
import com.vw.drink.dispenser.domain.product.Water;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest extends BaseIntegrationTest {

    @Autowired
    private Stock stock;

    @Test
    public void testAddProduct() throws InvalidProduct {
        dispenser.addProduct(new Coke(new Timestamp(0)));
        dispenser.addProduct(new Water(new Timestamp(0)));
        dispenser.addProduct(new Water(new Timestamp(0)));

        assertEquals(0, stock.byProduct(null));
        assertEquals(1, stock.byProduct(Coke.class));
        assertEquals(2, stock.byProduct(Water.class));
    }
}
