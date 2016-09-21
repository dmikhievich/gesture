package com.github.dmikhievich.gesture.policy;

import com.github.dmikhievich.gesture.Duration;
import com.github.dmikhievich.gesture.RetryContext;
import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Dzmitry_Mikhievich.
 */
public final class WaitPolicies {

    private WaitPolicies() {}

    public static WaitPolicy fixed(Duration delay) {
        checkArgument(delay != null, "Delay shouldn't  be null");
        return new FixedWaitPolicy(delay);
    }

    public static WaitPolicy randomInRange(TimeUnit timeUnit, long fromInclusive, long toInclusive) {
        checkArgument(timeUnit != null, "Time Unit can't be null");
        checkArgument(fromInclusive > 0, "Lower value shouldn't be less than 0");
        checkArgument(toInclusive >= fromInclusive, "Upper value shouldn't be less than lower");
        return new RandomWaitPolicy(timeUnit, fromInclusive, toInclusive);
    }

    private static final class FixedWaitPolicy implements WaitPolicy {

        private final Duration duration;

        FixedWaitPolicy(Duration duration) {
            this.duration = duration;
        }

        @Nonnull @Override
        public Duration getDelayBeforeNextAttempt(@Nonnull RetryContext context) {
            return duration;
        }
    }

    private static final class RandomWaitPolicy implements WaitPolicy {

        private final TimeUnit timeUnit;
        private final long from;
        private final long to;

        RandomWaitPolicy(TimeUnit timeUnit, long from, long to) {
            this.timeUnit = timeUnit;
            this.from = from;
            this.to = to;
        }

        @Nonnull @Override
        public Duration getDelayBeforeNextAttempt(@Nonnull RetryContext context) {
            long duration = RandomUtils.nextLong(from, to + 1);
            return Duration.in(duration, timeUnit);
        }
    }
}
