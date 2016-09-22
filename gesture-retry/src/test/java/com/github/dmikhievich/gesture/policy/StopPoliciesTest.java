package com.github.dmikhievich.gesture.policy;

import com.github.dmikhievich.gesture.Duration;
import com.github.dmikhievich.gesture.RetryContext;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dzmitry Mikhievich
 */
@RunWith(JUnitParamsRunner.class)
public class StopPoliciesTest {

    @Test
    public void testStopOnTimeout_whenCalledForNonNullDuration_andRetryDurationIsEqualToOrMoreThanValueSpecified_theExecutionShouldBeStopped() {
        Duration timeoutMock = Duration.in(1, TimeUnit.SECONDS);
        Duration executionTimeMock = Duration.in(2, TimeUnit.SECONDS);
        RetryContext retryContextMock = mock(RetryContext.class);
        when(retryContextMock.getExecutionDuration()).thenReturn(executionTimeMock);

        StopPolicy stopPolicy = StopPolicies.stopOnTimeout(timeoutMock);
        assertThat(stopPolicy.shouldStopExecution(retryContextMock), is(true));
    }

    @Test
    public void testStopOnTimeout_whenCalledForNonNullDuration_andRetryDurationIsLessThanValueSpecified_theExecutionShouldNotBeStopped() {
        Duration timeout = Duration.in(2, TimeUnit.SECONDS);
        Duration executionTime = Duration.in(1, TimeUnit.SECONDS);
        RetryContext retryContextMock = mock(RetryContext.class);
        when(retryContextMock.getExecutionDuration()).thenReturn(executionTime);

        StopPolicy stopPolicy = StopPolicies.stopOnTimeout(timeout);
        assertThat(stopPolicy.shouldStopExecution(retryContextMock), is(false));
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
        assertThat(expectedResult, equalTo(stopPolicy.shouldStopExecution(retryContextMock)));
    }

    @Parameters({"0", "-1", "-14566"})
    @TestCaseName("{method} => {0}")
    @Test(expected = IllegalArgumentException.class)
    public void testStopOnAttempt_whenIllegalMaxAttemptValueIsPassed_thenIllegalArgumentExceptionShouldBeThrown(int attemptNumber) {
        StopPolicies.stopOnAttempt(attemptNumber);
    }
}
