package com.vw.drink.dispenser.domain.dispenser;

import org.springframework.context.ApplicationEvent;

public class DispenserStatusChangeEvent extends ApplicationEvent {

    public Dispenser.Status previousStatus;
    public Dispenser.Status currentStatus;

    public DispenserStatusChangeEvent(
        Dispenser dispenser,
        Dispenser.Status previousStatus,
        Dispenser.Status currentStatus
    ) {
        super(dispenser);
        this.previousStatus = previousStatus;
        this.currentStatus = currentStatus;
    }
}
