package com.vw.drink.dispenser.domain.dispenser.exception;

import com.vw.drink.dispenser.domain.money.Money;
import com.vw.drink.dispenser.domain.product.ProductType;

public class AmountIntroducedIsNotEnoughException extends DispenseValidationException {

    public AmountIntroducedIsNotEnoughException(ProductType type, Money difference) {
        super(String.format("Amount introduced is not enough to buy a %s, %s euros are missing", type, difference));
    }
}
