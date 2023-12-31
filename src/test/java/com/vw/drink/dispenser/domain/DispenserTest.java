package com.vw.drink.dispenser.domain;

import com.vw.drink.dispenser.domain.dispenser.Dispenser;
import com.vw.drink.dispenser.domain.dispenser.DispenserStatusChangeEvent;
import com.vw.drink.dispenser.domain.dispenser.exception.AmountIntroducedIsNotEnoughException;
import com.vw.drink.dispenser.domain.dispenser.exception.DispenserNotAvailableException;
import com.vw.drink.dispenser.domain.dispenser.exception.NotEnoughCashToGiveChangeException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductExpiratedException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductNotSelectedException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductWithoutStockException;
import com.vw.drink.dispenser.domain.money.Coin;
import com.vw.drink.dispenser.domain.money.Money;
import com.vw.drink.dispenser.domain.product.Product;
import com.vw.drink.dispenser.domain.product.ProductRepository;
import com.vw.drink.dispenser.domain.product.ProductType;
import com.vw.drink.dispenser.domain.product.ProductWithoutStockEvent;
import com.vw.drink.dispenser.domain.product.exception.NoUnexpiredProductException;
import com.vw.drink.dispenser.domain.time.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DispenserTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private Dispenser dispenser;

    @BeforeEach
    public void beforeEach() {
        dispenser = new Dispenser(productRepository, eventPublisher, new Money("1.00"));
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

        assertEquals(new Money("1.00"), result.amountReturned);
        assertNull(result.dispensedProduct);
        assertTrue(result.error);
        assertInstanceOf(ProductWithoutStockException.class, result.errorCause);
    }

    @Test
    public void cantDispenseExpiredProduct() throws DispenserNotAvailableException, NoUnexpiredProductException, ProductNotSelectedException {
        dispenser.selectProduct(ProductType.COKE);

        when(productRepository.hasStock(ProductType.COKE)).thenReturn(true);
        when(productRepository.hasAnyUnexpired(ProductType.COKE)).thenReturn(false);

        var result = dispenser.dispense();

        assertEquals(new Money("0.00"), result.amountReturned);
        assertNull(result.dispensedProduct);
        assertTrue(result.error);
        assertInstanceOf(ProductExpiratedException.class, result.errorCause);
    }

    @Test
    public void cantDispenseProductIfAmountIntroducedIsLessThanPrice() throws DispenserNotAvailableException, ProductNotSelectedException, NoUnexpiredProductException {
        dispenser.selectProduct(ProductType.REDBULL);
        dispenser.insertCoin(Coin.EURO_2);

        when(productRepository.hasStock(ProductType.REDBULL)).thenReturn(true);
        when(productRepository.hasAnyUnexpired(ProductType.REDBULL)).thenReturn(true);

        var result = dispenser.dispense();

        assertEquals(new Money("2.00"), result.amountReturned);
        assertNull(result.dispensedProduct);
        assertTrue(result.error);
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

        assertEquals(new Money("4.00"), result.amountReturned);
        assertNull(result.dispensedProduct);
        assertTrue(result.error);
        assertInstanceOf(NotEnoughCashToGiveChangeException.class, result.errorCause);
    }

    @Test
    public void dispenseAndExhaustProductStock() throws DispenserNotAvailableException, ProductNotSelectedException, NoUnexpiredProductException {
        var eventCaptor = ArgumentCaptor.forClass(ApplicationEvent.class);

        dispenser.selectProduct(ProductType.WATER);
        dispenser.insertCoin(Coin.CENTS_20);
        dispenser.insertCoin(Coin.CENTS_20);
        dispenser.insertCoin(Coin.CENTS_20);

        when(productRepository.hasStock(ProductType.WATER)).thenReturn(true, false);
        when(productRepository.hasAnyUnexpired(ProductType.WATER)).thenReturn(true);

        var dispensedProduct = Product.Factory.create(ProductType.WATER, new Timestamp(21));
        when(productRepository.pickUnexpiredProduct(ProductType.WATER))
            .thenReturn(dispensedProduct);

        // Check result
        var result = dispenser.dispense();

        assertEquals(new Money("0.10"), result.amountReturned);
        assertSame(dispensedProduct, result.dispensedProduct);
        assertFalse(result.error);
        assertNull(result.errorCause);

        // Check published events
        verify(eventPublisher, times(7)).publishEvent(eventCaptor.capture());

        List<ApplicationEvent> capturedEvents = eventCaptor.getAllValues();

        assertEquals(Dispenser.Status.AVAILABLE, ((DispenserStatusChangeEvent) capturedEvents.get(0)).currentStatus);
        assertEquals(Dispenser.Status.PRODUCT_SELECTED, ((DispenserStatusChangeEvent) capturedEvents.get(1)).currentStatus);
        assertEquals(Dispenser.Status.CHECK_PRODUCT_AVAILABILITY, ((DispenserStatusChangeEvent) capturedEvents.get(2)).currentStatus);
        assertEquals(Dispenser.Status.VALIDATE_MONEY, ((DispenserStatusChangeEvent) capturedEvents.get(3)).currentStatus);
        assertEquals(Dispenser.Status.DISPENSE_PRODUCT, ((DispenserStatusChangeEvent) capturedEvents.get(4)).currentStatus);

        assertEquals(ProductType.WATER, ((ProductWithoutStockEvent) capturedEvents.get(5)).type);

        assertEquals(Dispenser.Status.AVAILABLE, ((DispenserStatusChangeEvent) capturedEvents.get(6)).currentStatus);
    }
}
