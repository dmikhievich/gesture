package com.github.dmikhievich.gesture;

import com.github.dmikhievich.gesture.condition.Condition;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.mockito.Mockito.mock;

/**
 * Created by Dzmitry_Mikhievich
 */
@SuppressWarnings("unchecked")
public abstract class RetryExecutorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testDoWithRetry_actionIsNull() {
        getExecutorInstance().doWithRetry(null, mock(Condition.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoWithRetry_acceptanceCriteria() {
        getExecutorInstance().doWithRetry(mock(Callable.class), null);
    }

    protected abstract RetryExecutor getExecutorInstance();
}
