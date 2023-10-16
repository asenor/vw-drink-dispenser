package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.Dispense;
import com.vw.drink.dispenser.application.InsertCoin;
import com.vw.drink.dispenser.application.SelectProduct;
import com.vw.drink.dispenser.domain.Coin;
import com.vw.drink.dispenser.domain.Dispenser;
import com.vw.drink.dispenser.domain.Product;
import com.vw.drink.dispenser.domain.ProductType;
import com.vw.drink.dispenser.domain.Timestamp;
import com.vw.drink.dispenser.domain.exception.DispenserNotAvailableException;
import com.vw.drink.dispenser.domain.exception.InvalidProductException;
import com.vw.drink.dispenser.domain.exception.NoUnexpiredProductException;
import com.vw.drink.dispenser.domain.exception.ProductNotSelectedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.vw.drink.dispenser.configuration.TimeConfiguration.NOW_SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DispenseTest extends BaseIntegrationTest {

    @Autowired
    private SelectProduct selectProduct;

    @Autowired
    private InsertCoin insertCoin;

    @Autowired
    private Dispense dispense;

    @Test
    public void dispense() throws InvalidProductException, DispenserNotAvailableException, ProductNotSelectedException, NoUnexpiredProductException {
        productRepository.add(Product.Factory.create(ProductType.WATER, new Timestamp(NOW_SECONDS + 1)));

        selectProduct.handle(ProductType.WATER);

        insertCoin.handle(Coin.EURO_2);

        dispense.handle();

        assertFalse(productRepository.hasStock(ProductType.WATER));
        assertEquals(Dispenser.Status.AVAILABLE, dispenser.status());
    }
}
