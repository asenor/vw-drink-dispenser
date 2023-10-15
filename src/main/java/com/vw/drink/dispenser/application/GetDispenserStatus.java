package com.vw.drink.dispenser.application;

import com.vw.drink.dispenser.domain.Dispenser;
import org.springframework.stereotype.Service;

@Service
public class GetDispenserStatus {

    private final Dispenser dispenser;

    public GetDispenserStatus(Dispenser dispenser) {
        this.dispenser = dispenser;
    }

    public Dispenser.Status handle() {
        return dispenser.status();
    }
}
