package com.gestureframework.retry;

import com.gestureframework.retry.condition.Condition;
import com.gestureframework.retry.policy.StopPolicy;
import com.gestureframework.retry.policy.WaitPolicy;
import com.google.common.util.concurrent.Uninterruptibles;

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
        boolean forceStop = false;
        while(!forceStop) {
            AttemptResult<T> attemptResult = makeAnAttempt(action);
            //check if acceptance criteria met
            if(acceptanceCriteria.matches(attemptResult)) {
                return attemptResult.getResult();
            }
            //update context
            context.incrementRetriesCount();
            context.setLatestAttemptResult(attemptResult);
            if(stopPolicy.shouldStopExecution(context)) {
                forceStop = true;
            }
            Duration waitDuration = waitPolicy.getDelayBeforeNextAttempt(context);
            Uninterruptibles.sleepUninterruptibly(waitDuration.getValue(), waitDuration.getTimeUnit());
        }
        throw new RetryExecutionException("");
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
