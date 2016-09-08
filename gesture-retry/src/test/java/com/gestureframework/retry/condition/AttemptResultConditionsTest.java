package com.gestureframework.retry.condition;

import com.gestureframework.retry.AttemptResult;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author Dzmitry Mikhievich
 */
@SuppressWarnings("unchecked")
public class AttemptResultConditionsTest {

    private final AttemptResult<Object> attemptResultMock = mock(AttemptResult.class);
    private final Condition<Exception> exceptionConditionMock = (Condition<Exception>) mock(Condition.class);
    private final Condition<Object> resultConditionMock = (Condition<Object>) mock(Condition.class);

    @Test
    public void testException_whenCalledWithNonNullExceptionCondition_andReturnedAttemptConditionIsAppliedForNullValue_thenNullValueShouldBePassedToTheOriginalExceptionCondition() {
        Condition<AttemptResult<Object>> aggregatedCondition = AttemptResultConditions.exception(exceptionConditionMock);
        aggregatedCondition.matches(null);

        verify(exceptionConditionMock, times(1)).matches(null);
    }

    @Test
    public void testException_whenCalledWithNonNullExceptionCondition_andReturnedAttemptConditionIsAppliedForNonNullValue_thenExceptionFromAttemptConditionShouldBePassedToTheOriginalExceptionCondition() {
        Exception exceptionMock = mock(Exception.class);
        when(attemptResultMock.getThrownException()).thenReturn(exceptionMock);

        Condition<AttemptResult<Object>> aggregatedCondition = AttemptResultConditions.exception(exceptionConditionMock);
        aggregatedCondition.matches(attemptResultMock);

        verify(exceptionConditionMock, times(1)).matches(exceptionMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException_whenCalledForNullCondition_thenIllegalArgumentExceptionShouldBeThrown() {
        AttemptResultConditions.exception(null);
    }

    @Test
    public void testResult_whenCalledWithNonNullResultCondition_andReturnedAttemptConditionIsAppliedForNullValue_thenNullValueShouldBePassedToTheOriginalResultCondition() {
        Condition<AttemptResult<Object>> aggregatedCondition = AttemptResultConditions.result(resultConditionMock);
        aggregatedCondition.matches(null);

        verify(resultConditionMock, times(1)).matches(null);
    }

    @Test
    public void testException_whenCalledWithNonNullResultCondition_andReturnedAttemptConditionIsAppliedForNonNullValue_thenResultFromAttemptConditionShouldBePassedToTheOriginalResultCondition() {
        Object resultMock = mock(Object.class);
        when(attemptResultMock.getResult()).thenReturn(resultMock);

        Condition<AttemptResult<Object>> aggregatedCondition = AttemptResultConditions.result(resultConditionMock);
        aggregatedCondition.matches(attemptResultMock);

        verify(resultConditionMock, times(1)).matches(resultMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResult_whenCalledForNullCondition_thenIllegalArgumentExceptionShouldBeThrown() {
        AttemptResultConditions.result(null);
    }
}
