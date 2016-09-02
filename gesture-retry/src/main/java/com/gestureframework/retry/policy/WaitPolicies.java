package com.gestureframework.retry.policy;

import com.gestureframework.retry.Duration;
import com.gestureframework.retry.RetryContext;
import com.google.common.collect.Range;

import java.util.concurrent.TimeUnit;

//TODO add implementations
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

        public FixedWaitPolicy(Duration duration) {
            this.duration = duration;
        }

        @Override
        public Duration getDelayBeforeNextAttempt(RetryContext context) {
            return duration;
        }
    }

    private static class RandomWaitPolicy implements WaitPolicy {

        private final TimeUnit timeUnit;
        private final Range<Long> value;

        public RandomWaitPolicy(TimeUnit timeUnit, Range<Long> value) {
            this.timeUnit = timeUnit;
            this.value = value;
        }

        @Override
        public Duration getDelayBeforeNextAttempt(RetryContext context) {
            return null;
        }
    }
}
