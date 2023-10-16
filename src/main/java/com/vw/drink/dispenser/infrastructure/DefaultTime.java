package com.vw.drink.dispenser.infrastructure;

import com.vw.drink.dispenser.domain.Time;
import com.vw.drink.dispenser.domain.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DefaultTime implements Time {

    @Override
    public Timestamp now() {
        return new Timestamp(Instant.now().getEpochSecond());
    }
}
