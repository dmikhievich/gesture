package com.gestureframework.retry;

import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dzmitry_Mikhievich.
 */
@ToString
//TODO optimize to avoid extra-conversions
public class Duration {

    @Getter
    private final TimeUnit timeUnit;
    @Getter
    private final long value;

    private Duration(long value, TimeUnit timeUnit) {
        //TODO add validation
        this.value = value;
        this.timeUnit = timeUnit;
    }

    public long toMillis() {
        return timeUnit.toMillis(value);
    }

    public long toNanos() {
        return timeUnit.toNanos(value);
    }

    //TODO add validation
    public boolean isMoreOrEquals(Duration other) {
        return toNanos() >= other.toNanos();
    }

    public static Duration in(long value, TimeUnit timeUnit) {
        return new Duration(value, timeUnit);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Duration duration = (Duration) other;
        return duration.toNanos() == toNanos();
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeUnit, value);
    }
}
