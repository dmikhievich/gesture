package com.gestureframework.retry.policy;

import com.gestureframework.retry.Duration;
import com.gestureframework.retry.RetryContext;

/**
 * Created by Dzmitry_Mikhievich.
 */
public final class StopPolicies {

    public static StopPolicy stopOnTimeout(Duration timeout) {
        return new StopOnTimeoutPolicy(timeout);
    }

    public static StopPolicy stopOnAttempt(int maxAttempt) {
        return new StopOnAttemptPolicy(maxAttempt);
    }

    private static class StopOnTimeoutPolicy implements StopPolicy {

        private final Duration timeout;

        StopOnTimeoutPolicy(Duration timeout) {
            this.timeout = timeout;
        }

        @Override
        public boolean shouldStopExecution(RetryContext context) {
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
        public boolean shouldStopExecution(RetryContext context) {
            return context.getRetriesCount() >= attempt;
        }
    }
}
