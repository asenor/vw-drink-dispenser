package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.exception.ProductNotSelectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DispenserTest {

    private Dispenser dispenser;

    @BeforeEach
    public void beforeEach() {
        dispenser = new Dispenser();
    }

    @Test
    public void testInitialStatusIsAvailable() {
        assertEquals(Dispenser.Status.AVAILABLE, dispenser.status());
    }

    @Test
    public void testSelectProduct() {
        dispenser.selectProduct(ProductType.WATER);
        assertEquals(Dispenser.Status.PRODUCT_SELECTED, dispenser.status());
    }

    @Test
    public void testCantIntroduceCoinsWithoutSelectingProduct() {
        assertThrows(ProductNotSelectedException.class, () -> {
            dispenser.introduceCoin(Coin.EURO_1);
        });
    }

    @Test
    public void testIntroduceCoins() throws ProductNotSelectedException {
        dispenser.selectProduct(ProductType.WATER);

        dispenser.introduceCoin(Coin.CENTS_5);

        assertEquals(new Money("0.05"), dispenser.amountIntroduced());

        dispenser.introduceCoin(Coin.CENTS_20);
        dispenser.introduceCoin(Coin.EURO_1);

        assertEquals(new Money("1.25"), dispenser.amountIntroduced());
    }
}
