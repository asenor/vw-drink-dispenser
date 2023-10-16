package com.vw.drink.dispenser.configuration;

import com.vw.drink.dispenser.domain.time.Time;
import com.vw.drink.dispenser.domain.time.Timestamp;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.when;

@TestConfiguration
public class TimeConfiguration {
    public static long NOW_SECONDS = 10;

    @Bean
    public Time time() {
        var time = Mockito.mock(Time.class);
        when(time.now()).thenReturn(new Timestamp(NOW_SECONDS));
        return time;
    }
}
