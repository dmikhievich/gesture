package com.github.dmikhievich.gesture;

import com.github.dmikhievich.gesture.condition.Condition;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author Dzmitry Mikhievich
 */
public class AsyncRetryExecutorTest {

    private RetryExecutor retryExecutorMock = mock(RetryExecutor.class);
    private ExecutorService executorServiceMock = mock(ExecutorService.class);


    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_executionServiceIsNull() {
        new AsyncRetryExecutor(null, retryExecutorMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_retryExecutorIsNull() {
        new AsyncRetryExecutor(executorServiceMock, null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDoWithRetry_whenCalled_thenExecutionShouldBeDelegatedToTheRetryExecutor_andSubmittedToTheExecutorService() {
        Callable<Object> action = () -> null;
        Condition<AttemptResult<Object>> conditionMock = (Condition<AttemptResult<Object>>) mock(Condition.class);
        when(executorServiceMock.submit((Callable) any())).thenAnswer(invocation -> ((Callable) invocation.getArgument(0)).call()
        );
        AsyncRetryExecutor asyncExecutor = new AsyncRetryExecutor(executorServiceMock, retryExecutorMock);
        asyncExecutor.doWithRetry(action, conditionMock);

        verify(retryExecutorMock, times(1)).doWithRetry(action, conditionMock);
    }
}
