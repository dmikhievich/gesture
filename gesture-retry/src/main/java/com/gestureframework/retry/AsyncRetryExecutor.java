package com.gestureframework.retry;

import com.gestureframework.retry.condition.Condition;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Dzmitry Mikhievich
 */
public final class AsyncRetryExecutor {

    private final ExecutorService executorService;
    private final RetryExecutor retryExecutor;

    public AsyncRetryExecutor(ExecutorService executorService, RetryExecutor retryExecutor) {
        this.retryExecutor = retryExecutor;
        this.executorService = executorService;
    }

    public <T> Future<T> doWithRetry(Callable<T> action, Condition<AttemptResult<T>> acceptanceCriteria) throws RetryExecutionException {
        return executorService.submit(() -> retryExecutor.doWithRetry(action, acceptanceCriteria));
    }
}
