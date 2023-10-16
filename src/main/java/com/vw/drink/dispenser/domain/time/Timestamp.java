package com.vw.drink.dispenser.domain.time;

public class Timestamp {

    private final long seconds;

    public Timestamp(long seconds) {
        this.seconds = seconds;
    }

    public boolean isBefore(Timestamp other) {
        return seconds < other.seconds;
    }
}
