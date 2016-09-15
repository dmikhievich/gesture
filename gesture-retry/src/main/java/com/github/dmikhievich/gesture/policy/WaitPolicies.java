package com.github.dmikhievich.gesture.policy;

import com.github.dmikhievich.gesture.Duration;
import com.github.dmikhievich.gesture.RetryContext;
import com.google.common.collect.Range;
import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dzmitry_Mikhievich.
 */
public class WaitPolicies {

    public static WaitPolicy fixed(Duration delay) {
        return new FixedWaitPolicy(delay);
    }

    public static WaitPolicy randomInRange(TimeUnit timeUnit, Range<Long> value) {
        return new RandomWaitPolicy(timeUnit, value);
    }

    private static class FixedWaitPolicy implements WaitPolicy {

        private final Duration duration;

        FixedWaitPolicy(Duration duration) {
            this.duration = duration;
        }

        @Nonnull @Override
        public Duration getDelayBeforeNextAttempt(RetryContext context) {
            return duration;
        }
    }

    private static class RandomWaitPolicy implements WaitPolicy {

        private final TimeUnit timeUnit;
        private final Range<Long> value;

        RandomWaitPolicy(TimeUnit timeUnit, Range<Long> value) {
            this.timeUnit = timeUnit;
            this.value = value;
        }

        @Nonnull @Override
        public Duration getDelayBeforeNextAttempt(RetryContext context) {
            long duration = RandomUtils.nextLong(value.lowerEndpoint(), value.upperEndpoint() + 1);
            return Duration.in(duration, timeUnit);
        }
    }
}
