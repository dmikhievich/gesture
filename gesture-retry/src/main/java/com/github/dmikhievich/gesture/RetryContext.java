package com.github.dmikhievich.gesture;

import com.google.common.base.Stopwatch;
import lombok.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dzmitry_Mikhievich.
 */
@ToString
@EqualsAndHashCode
public class RetryContext {

    @Getter
    @Setter(AccessLevel.PACKAGE)
    private AttemptResult latestAttemptResult;
    @Getter
    private int retriesCount = 0;

    private final Stopwatch stopwatch;

    private RetryContext() {
        this.stopwatch = Stopwatch.createStarted();
    }

    public Duration getExecutionDuration() {
        TimeUnit timeUnit = TimeUnit.NANOSECONDS;
        return Duration.in(stopwatch.elapsed(timeUnit), timeUnit);
    }

    void incrementRetriesCount() {
        retriesCount += 1;
    }

    static RetryContext create() {
        return new RetryContext();
    }
}
