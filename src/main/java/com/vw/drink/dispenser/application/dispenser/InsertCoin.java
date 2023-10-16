package com.vw.drink.dispenser.application.dispenser;

import com.vw.drink.dispenser.domain.money.Coin;
import com.vw.drink.dispenser.domain.dispenser.Dispenser;
import com.vw.drink.dispenser.domain.dispenser.exception.ProductNotSelectedException;
import org.springframework.stereotype.Service;

@Service
public class InsertCoin {

    private final Dispenser dispenser;

    public InsertCoin(Dispenser dispenser) {
        this.dispenser = dispenser;
    }

    public void handle(Coin coin) throws ProductNotSelectedException {
        dispenser.insertCoin(coin);
    }
}
