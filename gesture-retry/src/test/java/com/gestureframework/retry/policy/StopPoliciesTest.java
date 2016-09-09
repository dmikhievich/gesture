package com.gestureframework.retry.policy;

import com.gestureframework.retry.Duration;
import com.gestureframework.retry.RetryContext;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dzmitry Mikhievich
 */
@RunWith(JUnitParamsRunner.class)
public class StopPoliciesTest {


    @Test
    public void testStopOnTimeout_whenCalledForNonNullDuration_andRetryDurationIsEqualToOrMoreThanValueSpecified_theExecutionShouldBeStopped() {
        Duration timeoutMock = mock(Duration.class);
        Duration executionTimeMock = mock(Duration.class);
        when(executionTimeMock.isMoreOrEquals(timeoutMock)).thenReturn(true);
        RetryContext retryContextMock = mock(RetryContext.class);
        when(retryContextMock.getExecutionDuration()).thenReturn(executionTimeMock);

        StopPolicy stopPolicy = StopPolicies.stopOnTimeout(timeoutMock);
        assertTrue(stopPolicy.shouldStopExecution(retryContextMock));
    }

    @Test
    public void testStopOnTimeout_whenCalledForNonNullDuration_andRetryDurationIsLessThanValueSpecified_theExecutionShouldNotBeStopped() {
        Duration timeoutMock = mock(Duration.class);
        Duration executionTimeMock = mock(Duration.class);
        when(executionTimeMock.isMoreOrEquals(timeoutMock)).thenReturn(false);
        RetryContext retryContextMock = mock(RetryContext.class);
        when(retryContextMock.getExecutionDuration()).thenReturn(executionTimeMock);

        StopPolicy stopPolicy = StopPolicies.stopOnTimeout(timeoutMock);
        assertFalse(stopPolicy.shouldStopExecution(retryContextMock));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStopOnTimeout_whenCalledForNullTimeoutDuration_thenIllegalArgumentExceptionShouldBeThrown() {
        StopPolicies.stopOnTimeout(null);
    }

    @Test
    @Parameters({"3, 3, true",
            "2, 1, false",
            "3, 4, true"})
    @TestCaseName("{method} => when expected attempt is {0} and current is {1}, then expect {2}")
    public void testStopOnAttempt_whenCalledWithValidAttemptNumber_andReturnedStopPolicyIsAppliedToTheContext_thenStopFlagShouldBeCalculatedCorrectly(int targetAttempt, int currentAttempt, boolean expectedResult) {
        RetryContext retryContextMock = mock(RetryContext.class);
        when(retryContextMock.getRetriesCount()).thenReturn(currentAttempt);

        StopPolicy stopPolicy = StopPolicies.stopOnAttempt(targetAttempt);
        assertEquals(expectedResult, stopPolicy.shouldStopExecution(retryContextMock));
    }

    @Parameters({"0", "-1", "-14566"})
    @TestCaseName("{method} => {0}")
    @Test(expected = IllegalArgumentException.class)
    public void testStopOnAttempt_whenIllegalMaxAttemptValueIsPassed_thenIllegalArgumentExceptionShouldBeThrown(int attemptNumber) {
        StopPolicies.stopOnAttempt(attemptNumber);
    }
}
