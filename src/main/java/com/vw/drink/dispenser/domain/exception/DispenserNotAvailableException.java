package com.vw.drink.dispenser.domain.exception;

public class DispenserNotAvailableException extends Exception {

    public DispenserNotAvailableException() {
        super("Dispenser is not available yet");
    }
}
