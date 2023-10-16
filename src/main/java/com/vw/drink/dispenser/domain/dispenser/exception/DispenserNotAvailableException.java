package com.vw.drink.dispenser.domain.dispenser.exception;

public class DispenserNotAvailableException extends Exception {

    public DispenserNotAvailableException() {
        super("Dispenser is not available yet");
    }
}
