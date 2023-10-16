package com.vw.drink.dispenser.domain.dispenser;

import com.vw.drink.dispenser.domain.dispenser.exception.AmountIntroducedIsNotEnoughException;
import com.vw.drink.dispenser.domain.dispenser.exception.DispenseValidationException;
import com.vw.drink.dispenser.domain.dispenser.exception.DispenserNotAvailableException;
import com.vw.drink.dispenser.domain.dispenser.exception.NotEnoughCashToGiveChangeException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductExpiratedException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductNotSelectedException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductWithoutStockException;
import com.vw.drink.dispenser.domain.money.Coin;
import com.vw.drink.dispenser.domain.money.Money;
import com.vw.drink.dispenser.domain.product.Product;
import com.vw.drink.dispenser.domain.product.ProductPrice;
import com.vw.drink.dispenser.domain.product.ProductRepository;
import com.vw.drink.dispenser.domain.product.ProductType;
import com.vw.drink.dispenser.domain.product.ProductWithoutStockEvent;
import com.vw.drink.dispenser.domain.product.exception.NoUnexpiredProductException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Dispenser {

    private static final int MAX_SECONDS_PER_STEP = 5;

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    private Status status;
    private Money cash;
    private ProductType selectedProductType;
    private Money amountIntroduced;

    public Dispenser(
        ProductRepository productRepository,
        ApplicationEventPublisher eventPublisher,
        Money cash
    ) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
        this.cash = cash;
        initialState();
    }

    public void selectProduct(ProductType productType) throws DispenserNotAvailableException {
        if (status != Status.AVAILABLE) throw new DispenserNotAvailableException();
        selectedProductType = productType;
        changeState(Status.PRODUCT_SELECTED);
    }

    public void insertCoin(Coin coin) throws ProductNotSelectedException {
        if (status != Status.PRODUCT_SELECTED) throw new ProductNotSelectedException();
        amountIntroduced = amountIntroduced.plus(coin.amount);
    }

    public DispenseResult dispense() throws ProductNotSelectedException {
        if (status != Status.PRODUCT_SELECTED) throw new ProductNotSelectedException();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Dispensed dispensed;
        try {
            var future = executorService.schedule(() -> { try { checkProductAvailability(); } catch (DispenseValidationException e) { throw new RuntimeException(e); }}, 0, TimeUnit.SECONDS);
            future.get(MAX_SECONDS_PER_STEP, TimeUnit.SECONDS);
            future = executorService.schedule(() -> { try { validateMoney(); } catch (DispenseValidationException e) { throw new RuntimeException(e); }}, 0, TimeUnit.SECONDS);
            future.get(MAX_SECONDS_PER_STEP, TimeUnit.SECONDS);
            Future<Dispensed> dispensedFuture = executorService.schedule(this::dispenseProduct, 0, TimeUnit.SECONDS);
            dispensed = dispensedFuture.get(MAX_SECONDS_PER_STEP, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            // 2 getCause calls because I needed to wrap DispenseValidationException into RuntimeException
            return cancel(e.getCause().getCause());
        } catch (Exception e) {
            return cancel(e);
        }

        changeState(Status.DISPENSE_PRODUCT);

        if (!productRepository.hasStock(selectedProductType)) {
            eventPublisher.publishEvent(new ProductWithoutStockEvent(this, selectedProductType));
        }

        initialState();

        return DispenseResult.ok(dispensed.amountToReturn, dispensed.product);
    }

    public DispenseResult reject() throws ProductNotSelectedException {
        if (status != Status.PRODUCT_SELECTED) throw new ProductNotSelectedException();

        changeState(Status.CANCEL);

        var amountToReturn = amountIntroduced;

        initialState();

        return DispenseResult.ok(amountToReturn);
    }

    private DispenseResult cancel(Throwable e) {
        var amountToReturn = amountIntroduced;
        initialState();
        return DispenseResult.error(amountToReturn, e);
    }

    private void checkProductAvailability() throws DispenseValidationException {
        changeState(Status.CHECK_PRODUCT_AVAILABILITY);

        if (!productRepository.hasStock(selectedProductType)) {
            throw new ProductWithoutStockException(selectedProductType);
        }
        if (!productRepository.hasAnyUnexpired(selectedProductType)) {
            throw new ProductExpiratedException(selectedProductType);
        }
    }

    private void validateMoney() throws DispenseValidationException {
        changeState(Status.VALIDATE_MONEY);

        if (!isAmountIntroducedEnough()) {
            throw new AmountIntroducedIsNotEnoughException(
                selectedProductType,
                ProductPrice.forType(selectedProductType)
            );
        }
        if (!isCashEnoughToGiveChange()) {
            throw new NotEnoughCashToGiveChangeException();
        }
    }

    private Dispensed dispenseProduct() throws NoUnexpiredProductException {
        var dispensedProduct = productRepository.pickUnexpiredProduct(selectedProductType);
        cash = cash.plus(dispensedProduct.price());

        return new Dispensed(
            // TODO: return amount as coins
            dispensedProduct.price().difference(amountIntroduced),
            dispensedProduct
        );
    }

    private boolean isAmountIntroducedEnough() {
        return amountIntroduced.isMoreThan(ProductPrice.forType(selectedProductType));
    }

    private boolean isCashEnoughToGiveChange() {
        return cash.isMoreThan(ProductPrice.forType(selectedProductType).difference(amountIntroduced));
    }

    private void initialState() {
        amountIntroduced = Money.ZERO;
        selectedProductType = null;
        changeState(Status.AVAILABLE);
    }

    private void changeState(Status newState) {
        var previousState = status;
        status = newState;
        eventPublisher.publishEvent(new DispenserStatusChangeEvent(this, previousState, status));
    }

    public Status status() {
        return status;
    }

    public Money amountIntroduced() {
        return amountIntroduced;
    }

    public enum Status {
        AVAILABLE,
        PRODUCT_SELECTED,
        CHECK_PRODUCT_AVAILABILITY,
        VALIDATE_MONEY,
        DISPENSE_PRODUCT,
        CANCEL;
    }

    private class Dispensed {
        public Money amountToReturn;
        public Product product;

        public Dispensed(Money amountToReturn, Product product) {
            this.amountToReturn = amountToReturn;
            this.product = product;
        }
    }
}
