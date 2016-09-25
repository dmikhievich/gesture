package com.github.dmikhievich.gesture;

import com.github.dmikhievich.gesture.condition.Condition;
import com.github.dmikhievich.gesture.exception.RetryExecutionException;

import java.util.concurrent.Callable;

/**
 * Created by Dzmitry_Mikhievich.
 */
interface RetryExecutor {

    /**
     * Execute specified action till the acceptance criteria isn't met or execution isn't stopped
     *
     * @param action             action to retry
     * @param acceptanceCriteria
     * @param <T>
     * @return latest action result
     * @throws RetryExecutionException  acceptance criteria isn't met and execution process is finished
     * @throws IllegalArgumentException if action or acceptanceCriteria is null
     */
    <T> T doWithRetry(Callable<T> action, Condition<AttemptResult<T>> acceptanceCriteria) throws RetryExecutionException, IllegalArgumentException;
}
