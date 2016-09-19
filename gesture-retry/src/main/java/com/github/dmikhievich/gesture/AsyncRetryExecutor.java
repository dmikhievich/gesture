package com.github.dmikhievich.gesture;

import com.github.dmikhievich.gesture.condition.Condition;
import com.github.dmikhievich.gesture.exception.RetryExecutionException;
import com.google.common.base.Preconditions;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Dzmitry Mikhievich
 */
public final class AsyncRetryExecutor {

    private final ExecutorService executorService;
    private final RetryExecutor retryExecutor;

    public AsyncRetryExecutor(ExecutorService executorService, RetryExecutor retryExecutor) {
        checkArgument(executorService != null, "Executor service can't be null");
        checkArgument(retryExecutor != null, "Retry executor can't be null");
        this.retryExecutor = retryExecutor;
        this.executorService = executorService;
    }

    public <T> Future<T> doWithRetry(Callable<T> action, Condition<AttemptResult<T>> acceptanceCriteria) throws RetryExecutionException {
        return executorService.submit(() -> retryExecutor.doWithRetry(action, acceptanceCriteria));
    }
}
