package com.gestureframework.retry;

import com.gestureframework.retry.condition.Condition;
import com.gestureframework.retry.policy.StopPolicy;
import com.gestureframework.retry.policy.WaitPolicy;

import java.util.concurrent.Callable;

/**
 * @author Dzmitry Mikhievich
 */
public class RetryExecutorImpl implements RetryExecutor {

    private final StopPolicy stopPolicy;
    private final WaitPolicy waitPolicy;

    RetryExecutorImpl(StopPolicy stopPolicy, WaitPolicy waitPolicy) {
        this.stopPolicy = stopPolicy;
        this.waitPolicy = waitPolicy;
    }

    @Override
    public <T> T doWithRetry(Callable<T> action, Condition<AttemptResult<T>> acceptanceCriteria) throws RetryExecutionException {
        RetryContext context = RetryContext.create();
        boolean shouldStop = false;
        while(!shouldStop) {
            AttemptResult<T> attemptResult = makeAnAttempt(action);
            //update context
            context.incrementRetriesCount();
            context.setLatestAttemptResult(attemptResult);
            if(stopPolicy.shouldStopExecution(context)) {
                shouldStop = true;
            }
            Duration waitDuration = waitPolicy.getDelayBeforeNextAttempt(context);

        }
        return null;
    }

    private <T> AttemptResult<T> makeAnAttempt(Callable<T> action) {
        AttemptResult<T> result = new AttemptResult<>();
        try {
            result.setResult(action.call());
        } catch (Exception exception) {
            result.setThrownException(exception);
        }
        return result;
    }
}
