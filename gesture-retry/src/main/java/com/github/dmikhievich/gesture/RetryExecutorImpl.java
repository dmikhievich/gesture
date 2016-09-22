package com.github.dmikhievich.gesture;

import com.github.dmikhievich.gesture.condition.Condition;
import com.github.dmikhievich.gesture.exception.RetryExecutionException;
import com.github.dmikhievich.gesture.policy.StopPolicy;
import com.github.dmikhievich.gesture.policy.WaitPolicy;
import com.google.common.annotations.VisibleForTesting;

import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly;

/**
 * @author Dzmitry Mikhievich
 */
final class RetryExecutorImpl implements RetryExecutor {

    private final StopPolicy stopPolicy;
    private final WaitPolicy waitPolicy;

    RetryExecutorImpl(StopPolicy stopPolicy, WaitPolicy waitPolicy) {
        checkArgument(stopPolicy != null, "Error during the retry executor creation: stop policy isn't defined");
        checkArgument(waitPolicy != null, "Error during the retry executor creation: wait policy isn't defined");
        this.stopPolicy = stopPolicy;
        this.waitPolicy = waitPolicy;
    }

    @Override
    public <T> T doWithRetry(Callable<T> action, Condition<AttemptResult<T>> acceptanceCriteria) throws RetryExecutionException {
        RetryContext context = RetryContext.create();
        boolean continueExecution = true;
        while (continueExecution) {
            AttemptResult<T> attemptResult = makeAnAttempt(action);
            //check if acceptance criteria met
            if (acceptanceCriteria.matches(attemptResult)) {
                return attemptResult.getResult();
            }
            //update context
            context.incrementRetriesCount()
                    .setLatestAttemptResult(attemptResult);
            if (stopPolicy.shouldStopExecution(context)) {
                continueExecution = false;
            }
            Duration waitDuration = waitPolicy.getDelayBeforeNextAttempt(context);
            sleepUninterruptibly(waitDuration.getValue(), waitDuration.getTimeUnit());
        }
        throw new RetryExecutionException("");
    }

    @VisibleForTesting
    <T> AttemptResult<T> makeAnAttempt(Callable<T> action) {
        AttemptResult<T> result = new AttemptResult<>();
        try {
            result.setResult(action.call());
        } catch (Exception exception) {
            result.setThrownException(exception);
        }
        return result;
    }
}
