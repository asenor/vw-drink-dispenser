package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.Coin;
import com.vw.drink.dispenser.domain.Dispenser;
import com.vw.drink.dispenser.domain.exception.ProductNotSelectedException;
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
