package com.github.dmikhievich.gesture;

import com.google.common.annotations.VisibleForTesting;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Dzmitry_Mikhievich.
 */
@ToString
public class Duration {

    @Getter
    private final TimeUnit timeUnit;
    @Getter
    private final long value;

    //local value, which uses for optimization of toNanos() method
    private Long valueInNanos;

    private Duration(long value, TimeUnit timeUnit) {
        this.value = value;
        this.timeUnit = timeUnit;
    }

    public boolean isMoreOrEquals(@Nonnull Duration other) {
        return toNanos() >= other.toNanos();
    }

    public static Duration in(long value, TimeUnit timeUnit) {
        checkArgument(value > 0, "Duration value should be more than 0");
        checkArgument(timeUnit != null, "Time unit should be specified");
        return new Duration(value, timeUnit);
    }

    @VisibleForTesting
    protected long toNanos() {
        if (valueInNanos == null) {
            valueInNanos = timeUnit.toNanos(value);
        }
        return valueInNanos;
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
        return Objects.hash(toNanos());
    }
}
