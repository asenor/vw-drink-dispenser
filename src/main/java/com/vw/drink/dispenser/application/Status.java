package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.Dispenser;
import org.springframework.stereotype.Service;

@Service
public class Status {

    private final Dispenser dispenser;

    public Status(Dispenser dispenser) {
        this.dispenser = dispenser;
    }

    public Dispenser.Status status() {
        return dispenser.status();
    }
}
