package com.github.dmikhievich.gesture.policy;

import com.github.dmikhievich.gesture.Duration;
import com.github.dmikhievich.gesture.RetryContext;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Dzmitry_Mikhievich.
 */
public final class StopPolicies {

    private StopPolicies() {}

    public static StopPolicy stopOnTimeout(Duration timeout) {
        checkArgument(timeout != null, "Timeout can't be null");
        return new StopOnTimeoutPolicy(timeout);
    }

    public static StopPolicy stopOnAttempt(int attemptNumber) {
        checkArgument(attemptNumber > 0, "Attempt number should be more than 1");
        return new StopOnAttemptPolicy(attemptNumber);
    }

    private static class StopOnTimeoutPolicy implements StopPolicy {

        private final Duration timeout;

        StopOnTimeoutPolicy(@Nonnull Duration timeout) {
            this.timeout = timeout;
        }

        @Override
        public boolean shouldStopExecution(@Nonnull RetryContext context) {
            Duration executionDuration = context.getExecutionDuration();
            return executionDuration.isMoreOrEquals(timeout);
        }
    }

    private static class StopOnAttemptPolicy implements StopPolicy {

        private final int attempt;

        StopOnAttemptPolicy(int attempt) {
            this.attempt = attempt;
        }

        @Override
        public boolean shouldStopExecution(@Nonnull RetryContext context) {
            return context.getRetriesCount() >= attempt;
        }
    }
}
