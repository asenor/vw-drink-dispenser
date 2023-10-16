package com.vw.drink.dispenser.domain.exception;

public class NotEnoughCashToGiveChangeException extends DispenseValidationException {

    public NotEnoughCashToGiveChangeException() {
        super("There's not enough cash to give change");
    }
}
