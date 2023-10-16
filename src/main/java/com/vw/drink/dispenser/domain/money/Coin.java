package com.vw.drink.dispenser.domain.money;

public enum Coin {
    CENTS_5(new Money("0.05")),
    CENTS_10(new Money("0.10")),
    CENTS_20(new Money("0.20")),
    CENTS_50(new Money("0.50")),
    EURO_1(new Money("1.00")),
    EURO_2(new Money("2.00"));

    public final Money amount;

    Coin(Money amount) {
        this.amount = amount;
    }
}
