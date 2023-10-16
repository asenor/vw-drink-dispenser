package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.dispenser.Dispense;
import com.vw.drink.dispenser.application.dispenser.InsertCoin;
import com.vw.drink.dispenser.application.dispenser.SelectProduct;
import com.vw.drink.dispenser.domain.dispenser.Dispenser;
import com.vw.drink.dispenser.domain.dispenser.exception.DispenserNotAvailableException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductNotSelectedException;
import com.vw.drink.dispenser.domain.money.Coin;
import com.vw.drink.dispenser.domain.product.Product;
import com.vw.drink.dispenser.domain.product.ProductType;
import com.vw.drink.dispenser.domain.product.exception.InvalidProductException;
import com.vw.drink.dispenser.domain.product.exception.NoUnexpiredProductException;
import com.vw.drink.dispenser.domain.time.Timestamp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.vw.drink.dispenser.configuration.TimeConfiguration.NOW_SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DispenseTest extends BaseIntegrationTest {

    @Autowired
    private SelectProduct selectProduct;

    @Autowired
    private InsertCoin insertCoin;

    @Autowired
    private Dispense dispense;

    @Test
    public void dispenseTwoProductsAndExhaustStock() throws InvalidProductException, DispenserNotAvailableException, ProductNotSelectedException, NoUnexpiredProductException {
        productRepository.add(Product.Factory.create(ProductType.WATER, new Timestamp(NOW_SECONDS + 10)));
        productRepository.add(Product.Factory.create(ProductType.WATER, new Timestamp(NOW_SECONDS + 20)));

        selectProduct.handle(ProductType.WATER);

        insertCoin.handle(Coin.EURO_2);

        dispense.handle();

        assertTrue(productRepository.hasStock(ProductType.WATER));
        assertEquals(Dispenser.Status.AVAILABLE, dispenser.status());

        selectProduct.handle(ProductType.WATER);

        insertCoin.handle(Coin.EURO_1);

        dispense.handle();

        assertFalse(productRepository.hasStock(ProductType.WATER));
        assertEquals(Dispenser.Status.AVAILABLE, dispenser.status());
    }
}
