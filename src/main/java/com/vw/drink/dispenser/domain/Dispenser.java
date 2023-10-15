package com.vw.drink.dispenser.domain;

import org.springframework.stereotype.Component;

@Component
public class Dispenser {

    private Status status = Status.AVAILABLE;

    public Status status() {
        return status;
    }

    public enum Status {
        AVAILABLE,
        OUT_OF_ORDER;
    }
}
