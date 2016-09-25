package com.github.dmikhievich.gesture;

import com.github.dmikhievich.gesture.policy.StopPolicy;
import com.github.dmikhievich.gesture.policy.WaitPolicy;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dzmitry Mikhievich
 */
public class RetryExecutorImplTest extends RetryExecutorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_stopPolicyIsNull() {
        new RetryExecutorImpl(null, mock(WaitPolicy.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_waitPolicyIsNull() {
        new RetryExecutorImpl(mock(StopPolicy.class), null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMakeAnAttempt_actionThrowsAnException() throws Exception {
        Class<? extends Exception> exceptionType = Exception.class;
        Callable<Object> actionMock = (Callable<Object>) mock(Callable.class);
        when(actionMock.call()).thenThrow(exceptionType);

        RetryExecutorImpl retryExecutor = new RetryExecutorImpl(mock(StopPolicy.class), mock(WaitPolicy.class));
        AttemptResult result = retryExecutor.makeAnAttempt(actionMock);
        assertThat(result.getResult(), nullValue());
        assertThat(result.getThrownException(), instanceOf(exceptionType));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testMakeAnAttempt_actionReturnsResult() throws Exception {
        Object actionResult = new Object();
        Callable<Object> actionMock = (Callable<Object>) mock(Callable.class);
        when(actionMock.call()).thenReturn(actionResult);

        RetryExecutorImpl retryExecutor = new RetryExecutorImpl(mock(StopPolicy.class), mock(WaitPolicy.class));
        AttemptResult result = retryExecutor.makeAnAttempt(actionMock);
        assertThat(result.getResult(), equalTo(actionResult));
        assertThat(result.getThrownException(), nullValue());
    }

    @Override
    protected RetryExecutor getExecutorInstance() {
        return new RetryExecutorImpl(mock(StopPolicy.class), mock(WaitPolicy.class));
    }
}
