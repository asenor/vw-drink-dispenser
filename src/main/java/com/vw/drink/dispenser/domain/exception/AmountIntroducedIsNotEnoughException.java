package com.vw.drink.dispenser.domain.exception;

import com.vw.drink.dispenser.domain.Money;
import com.vw.drink.dispenser.domain.ProductType;

public class AmountIntroducedIsNotEnoughException extends DispenseValidationException {

    public AmountIntroducedIsNotEnoughException(ProductType type, Money difference) {
        super(String.format("Amount introduced is not enough to buy a %s, %s euros are missing", type, difference));
    }
}
