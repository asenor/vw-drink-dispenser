package com.vw.drink.dispenser.infrastructure;

import com.vw.drink.dispenser.domain.dispenser.DispenserStatusChangeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LCDClient {
    @Async
    @EventListener
    public void onDispenserStatusChangeEvent(DispenserStatusChangeEvent event) {
        // Send notification to LCD
    }
}
