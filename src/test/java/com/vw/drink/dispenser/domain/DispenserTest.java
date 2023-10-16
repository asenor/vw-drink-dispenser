package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.exception.AmountIntroducedIsNotEnoughException;
import com.vw.drink.dispenser.domain.exception.DispenserNotAvailableException;
import com.vw.drink.dispenser.domain.exception.NoUnexpiredProductException;
import com.vw.drink.dispenser.domain.exception.NotEnoughCashToGiveChangeException;
import com.vw.drink.dispenser.domain.exception.ProductExpiratedException;
import com.vw.drink.dispenser.domain.exception.ProductNotSelectedException;
import com.vw.drink.dispenser.domain.exception.ProductWithoutStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DispenserTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private Dispenser dispenser;

    @BeforeEach
    public void beforeEach() {
        dispenser = new Dispenser(productRepository, new Money("1.00"));
    }

    @Test
    public void initialStatusIsAvailable() {
        assertEquals(Dispenser.Status.AVAILABLE, dispenser.status());
    }

    // SELECT PRODUCT

    @Test
    public void cantSelectProductTwice() throws DispenserNotAvailableException {
        dispenser.selectProduct(ProductType.WATER);
        assertThrows(DispenserNotAvailableException.class, () -> {
            dispenser.selectProduct(ProductType.COKE);
        });
    }

    @Test
    public void selectProduct() throws DispenserNotAvailableException {
        dispenser.selectProduct(ProductType.WATER);
        assertEquals(Dispenser.Status.PRODUCT_SELECTED, dispenser.status());
    }

    // INTRODUCE COINS

    @Test
    public void cantIntroduceCoinsWithoutSelectingProduct() {
        assertThrows(ProductNotSelectedException.class, () -> {
            dispenser.insertCoin(Coin.EURO_1);
        });
    }

    @Test
    public void introduceCoins() throws DispenserNotAvailableException, ProductNotSelectedException {
        dispenser.selectProduct(ProductType.ORANGE_JUICE);

        dispenser.insertCoin(Coin.CENTS_5);

        assertEquals(new Money("0.05"), dispenser.amountIntroduced());

        dispenser.insertCoin(Coin.CENTS_20);
        dispenser.insertCoin(Coin.EURO_1);

        assertEquals(new Money("1.25"), dispenser.amountIntroduced());
    }

    // DISPENSE

    @Test
    public void cantDispenseProductIfNoProductWasSelected() {
        assertThrows(ProductNotSelectedException.class, () -> {
            dispenser.dispense();
        });
    }

    @Test
    public void cantDispenseProductWithoutStock() throws DispenserNotAvailableException, NoUnexpiredProductException, ProductNotSelectedException {
        dispenser.selectProduct(ProductType.WATER);
        dispenser.insertCoin(Coin.EURO_1);

        when(productRepository.hasStock(ProductType.WATER)).thenReturn(false);

        var result = dispenser.dispense();

        assertFalse(result.success);
        assertEquals(new Money("1.00"), result.amountReturned);
        assertNull(result.dispensedProduct);
        assertInstanceOf(ProductWithoutStockException.class, result.errorCause);
    }

    @Test
    public void cantDispenseExpiredProduct() throws DispenserNotAvailableException, NoUnexpiredProductException, ProductNotSelectedException {
        dispenser.selectProduct(ProductType.COKE);

        when(productRepository.hasStock(ProductType.COKE)).thenReturn(true);
        when(productRepository.hasAnyUnexpired(ProductType.COKE)).thenReturn(false);

        var result = dispenser.dispense();

        assertFalse(result.success);
        assertEquals(new Money("0.00"), result.amountReturned);
        assertNull(result.dispensedProduct);
        assertInstanceOf(ProductExpiratedException.class, result.errorCause);
    }

    @Test
    public void cantDispenseProductIfAmountIntroducedIsLessThanPrice() throws DispenserNotAvailableException, ProductNotSelectedException, NoUnexpiredProductException {
        dispenser.selectProduct(ProductType.REDBULL);
        dispenser.insertCoin(Coin.EURO_2);

        when(productRepository.hasStock(ProductType.REDBULL)).thenReturn(true);
        when(productRepository.hasAnyUnexpired(ProductType.REDBULL)).thenReturn(true);

        var result = dispenser.dispense();

        assertFalse(result.success);
        assertEquals(new Money("2.00"), result.amountReturned);
        assertNull(result.dispensedProduct);
        assertInstanceOf(AmountIntroducedIsNotEnoughException.class, result.errorCause);
    }

    @Test
    public void cantDispenseProductIfCashIsNotEnoughToGiveChange() throws DispenserNotAvailableException, ProductNotSelectedException, NoUnexpiredProductException {
        dispenser.selectProduct(ProductType.REDBULL);
        dispenser.insertCoin(Coin.EURO_2);
        dispenser.insertCoin(Coin.EURO_2);

        when(productRepository.hasStock(ProductType.REDBULL)).thenReturn(true);
        when(productRepository.hasAnyUnexpired(ProductType.REDBULL)).thenReturn(true);

        var result = dispenser.dispense();

        assertFalse(result.success);
        assertEquals(new Money("4.00"), result.amountReturned);
        assertNull(result.dispensedProduct);
        assertInstanceOf(NotEnoughCashToGiveChangeException.class, result.errorCause);
    }

    @Test
    public void dispense() throws DispenserNotAvailableException, ProductNotSelectedException, NoUnexpiredProductException {
        dispenser.selectProduct(ProductType.WATER);
        dispenser.insertCoin(Coin.CENTS_20);
        dispenser.insertCoin(Coin.CENTS_20);
        dispenser.insertCoin(Coin.CENTS_20);

        when(productRepository.hasStock(ProductType.WATER)).thenReturn(true);
        when(productRepository.hasAnyUnexpired(ProductType.WATER)).thenReturn(true);

        var dispensedProduct = Product.Factory.create(ProductType.WATER, new Timestamp(21));
        when(productRepository.pickUnexpiredProduct(ProductType.WATER))
            .thenReturn(dispensedProduct);

        var result = dispenser.dispense();

        assertTrue(result.success);
        assertEquals(new Money("0.10"), result.amountReturned);
        assertSame(dispensedProduct, result.dispensedProduct);
        assertNull(result.errorCause);
    }
}
