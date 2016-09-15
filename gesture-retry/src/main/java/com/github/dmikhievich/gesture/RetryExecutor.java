package com.github.dmikhievich.gesture;

import com.github.dmikhievich.gesture.condition.Condition;
import com.github.dmikhievich.gesture.exception.RetryExecutionException;

import java.util.concurrent.Callable;

/**
 * Created by Dzmitry_Mikhievich.
 */
interface RetryExecutor {

    <T> T doWithRetry(Callable<T> action, Condition<AttemptResult<T>> acceptanceCriteria) throws RetryExecutionException;
}
