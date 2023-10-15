package com.vw.drink.dispenser.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {

    private BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money(String amount) {
        this.amount = new BigDecimal(amount);
    }

    public Money plus(Money money) {
        return new Money(amount.add(money.amount));
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
