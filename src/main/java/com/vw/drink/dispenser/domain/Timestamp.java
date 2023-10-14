package com.vw.drink.dispenser.domain;

public class Timestamp {

    private final long seconds;

    public Timestamp(long seconds) {
        this.seconds = seconds;
    }

    public long getSeconds() {
        return seconds;
    }
}
