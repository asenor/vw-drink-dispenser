package com.vw.drink.dispenser.domain.dispenser;

import com.vw.drink.dispenser.domain.money.Coin;
import com.vw.drink.dispenser.domain.money.Money;
import com.vw.drink.dispenser.domain.dispenser.exception.AmountIntroducedIsNotEnoughException;
import com.vw.drink.dispenser.domain.dispenser.exception.DispenseValidationException;
import com.vw.drink.dispenser.domain.dispenser.exception.DispenserNotAvailableException;
import com.vw.drink.dispenser.domain.product.exception.NoUnexpiredProductException;
import com.vw.drink.dispenser.domain.dispenser.exception.NotEnoughCashToGiveChangeException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductExpiratedException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductNotSelectedException;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductWithoutStockException;
import com.vw.drink.dispenser.domain.product.ProductPrice;
import com.vw.drink.dispenser.domain.product.ProductRepository;
import com.vw.drink.dispenser.domain.product.ProductType;
import org.springframework.stereotype.Component;

@Component
public class Dispenser {

    private final ProductRepository productRepository;

    private Status status;
    private Money cash;
    private ProductType selectedProductType;
    private Money amountIntroduced;

    public Dispenser(ProductRepository productRepository, Money cash) {
        this.productRepository = productRepository;
        this.cash = cash;
        initialState();
    }

    public void selectProduct(ProductType productType) throws DispenserNotAvailableException {
        if (status != Status.AVAILABLE) throw new DispenserNotAvailableException();
        selectedProductType = productType;
        status = Status.PRODUCT_SELECTED;
    }

    public void insertCoin(Coin coin) throws ProductNotSelectedException {
        if (status != Status.PRODUCT_SELECTED) throw new ProductNotSelectedException();
        amountIntroduced = amountIntroduced.plus(coin.amount);
    }

    public DispenseResult dispense() throws ProductNotSelectedException, NoUnexpiredProductException {
        if (status != Status.PRODUCT_SELECTED) throw new ProductNotSelectedException();

        try {
            checkProductAvailability();
            validateMoney();
        } catch (DispenseValidationException exception) {
            var amountToReturn = amountIntroduced;
            initialState();
            return DispenseResult.error(amountToReturn, exception);
        }

        var dispensedProduct = productRepository.pickUnexpiredProduct(selectedProductType);
        // TODO: return amount as coins
        var amountToReturn = dispensedProduct.price().difference(amountIntroduced);
        cash = cash.plus(dispensedProduct.price());
        initialState();

        return DispenseResult.ok(amountToReturn, dispensedProduct);
    }

    public DispenseResult reject() throws ProductNotSelectedException, NoUnexpiredProductException {
        if (status != Status.PRODUCT_SELECTED) throw new ProductNotSelectedException();

        var amountToReturn = amountIntroduced;
        initialState();
        return DispenseResult.ok(amountToReturn);
    }

    private void checkProductAvailability() throws ProductWithoutStockException, ProductExpiratedException {
        status = Status.CHECK_PRODUCT_AVAILABILITY;

        if (!productRepository.hasStock(selectedProductType)) {
            throw new ProductWithoutStockException(selectedProductType);
        }
        if (!productRepository.hasAnyUnexpired(selectedProductType)) {
            throw new ProductExpiratedException(selectedProductType);
        }
    }

    private void validateMoney() throws AmountIntroducedIsNotEnoughException, NotEnoughCashToGiveChangeException {
        status = Status.VALIDATE_MONEY;

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

    private boolean isAmountIntroducedEnough() {
        return amountIntroduced.isMoreThan(ProductPrice.forType(selectedProductType));
    }

    private boolean isCashEnoughToGiveChange() {
        return cash.isMoreThan(ProductPrice.forType(selectedProductType).difference(amountIntroduced));
    }

    private void initialState() {
        amountIntroduced = Money.ZERO;
        status = Status.AVAILABLE;
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
        VALIDATE_MONEY;
    }
}
