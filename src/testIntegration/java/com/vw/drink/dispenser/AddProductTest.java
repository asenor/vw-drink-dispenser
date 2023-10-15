package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.AddProduct;
import com.vw.drink.dispenser.domain.Timestamp;
import com.vw.drink.dispenser.domain.exception.InvalidProduct;
import com.vw.drink.dispenser.domain.product.Coke;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddProductTest extends BaseIntegrationTest {

    @Autowired
    private AddProduct addProduct;

    @Test
    public void testAddProduct() throws InvalidProduct {
        addProduct.addProduct(new Coke(new Timestamp(0)));

        assertEquals(1, dispenser.productStock(Coke.class));

        assertThrows(InvalidProduct.class, () -> {
            addProduct.addProduct(null);
        });
    }
}
