package com.gestureframework.retry;

import com.google.common.base.Stopwatch;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dzmitry_Mikhievich.
 */
@ToString
@EqualsAndHashCode
public class RetryContext {

    @Getter
    @Setter
    private AttemptResult latestAttemptResult;
    @Getter
    private int retriesCount = 0;

    private final Stopwatch stopwatch;

    private RetryContext() {
        this.stopwatch = Stopwatch.createStarted();
    }

    public void incrementRetriesCount() {
        retriesCount += 1;
    }

    public Duration getExecutionDuration() {
        TimeUnit timeUnit = TimeUnit.NANOSECONDS;
        return Duration.in(stopwatch.elapsed(timeUnit), timeUnit);
    }

    public static RetryContext create() {
        return new RetryContext();
    }
}
