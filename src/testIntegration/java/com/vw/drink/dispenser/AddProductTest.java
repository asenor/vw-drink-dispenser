package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.AddProduct;
import com.vw.drink.dispenser.domain.InvalidProduct;
import com.vw.drink.dispenser.domain.Product;
import com.vw.drink.dispenser.domain.ProductType;
import com.vw.drink.dispenser.domain.Timestamp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddProductTest extends BaseIntegrationTest {

    @Autowired
    private AddProduct addProduct;

    @Test
    public void testAddProduct() throws InvalidProduct {
        addProduct.addProduct(Product.Factory.create(ProductType.COKE, new Timestamp(0)));

        assertEquals(1, productRepository.stock(ProductType.COKE));

        assertThrows(InvalidProduct.class, () -> {
            addProduct.addProduct(null);
        });
    }
}
