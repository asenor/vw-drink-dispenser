package com.vw.drink.dispenser.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {

    private BigDecimal amount;

    public static final Money ZERO = new Money("0.00");

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public Money plus(Money money) {
        return new Money(amount.add(money.amount));
    }

    public boolean isMoreThan(Money money) {
        return amount.compareTo(money.amount) > 0;
    }

    public Money difference(Money money) {
        return new Money(money.amount.subtract(amount));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Money)) return false;

        Money other = (Money) obj;
        return Objects.equals(this.amount, other.amount);
    }

    @Override
    public final int hashCode() {
        return amount.hashCode();
    }
}
