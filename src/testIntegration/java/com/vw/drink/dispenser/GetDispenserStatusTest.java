package com.vw.drink.dispenser;

import com.vw.drink.dispenser.application.dispenser.GetDispenserStatus;
import com.vw.drink.dispenser.domain.dispenser.Dispenser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetDispenserStatusTest extends BaseIntegrationTest {

    @Autowired
    private GetDispenserStatus getDispenserStatus;

    @Test
    public void testDispenserStatus() {
        assertEquals(Dispenser.Status.AVAILABLE, getDispenserStatus.handle());
    }
}
