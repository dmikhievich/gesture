package com.gestureframework.retry;

import com.gestureframework.retry.condition.Condition;

import java.util.concurrent.Callable;

/**
 * Created by Dzmitry_Mikhievich.
 */
interface RetryExecutor {

    <T> T doWithRetry(Callable<T> action, Condition<AttemptResult<T>> acceptanceCriteria) throws RetryExecutionException;
}
