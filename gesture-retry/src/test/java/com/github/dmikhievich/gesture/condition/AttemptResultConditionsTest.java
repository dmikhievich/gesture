package com.github.dmikhievich.gesture.condition;

import com.github.dmikhievich.gesture.AttemptResult;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
    public void testException_returnedAttemptConditionIsAppliedForNullValue() {
        Condition<AttemptResult<Object>> aggregatedCondition = AttemptResultConditions.exception(exceptionConditionMock);
        aggregatedCondition.matches(null);

        verify(exceptionConditionMock, times(1)).matches(null);
    }

    @Test
    public void testException_returnedAttemptConditionIsAppliedForNonNullValue() {
        Exception exceptionMock = mock(Exception.class);
        when(attemptResultMock.getThrownException()).thenReturn(exceptionMock);

        Condition<AttemptResult<Object>> aggregatedCondition = AttemptResultConditions.exception(exceptionConditionMock);
        aggregatedCondition.matches(attemptResultMock);

        verify(exceptionConditionMock, times(1)).matches(exceptionMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException_nullCondition() {
        AttemptResultConditions.exception(null);
    }

    @Test
    public void testException_checkDescription() {
        String description = "abc";
        when(exceptionConditionMock.getDescription()).thenReturn(description);
        Condition condition = AttemptResultConditions.exception(exceptionConditionMock);
        assertThat(condition.getDescription(), equalTo("exception is " + description));
    }

    @Test
    public void testResult_returnedAttemptConditionIsAppliedForNullValue() {
        Condition aggregatedCondition = AttemptResultConditions.result(resultConditionMock);
        aggregatedCondition.matches(null);

        verify(resultConditionMock, times(1)).matches(null);
    }

    @Test
    public void testResult_returnedAttemptConditionIsAppliedForNonNullValue() {
        Object resultMock = mock(Object.class);
        when(attemptResultMock.getResult()).thenReturn(resultMock);

        Condition<AttemptResult<Object>> aggregatedCondition = AttemptResultConditions.result(resultConditionMock);
        aggregatedCondition.matches(attemptResultMock);

        verify(resultConditionMock, times(1)).matches(resultMock);
    }

    @Test
    public void testResult_checkDescription() {
        String description = "abc";
        when(exceptionConditionMock.getDescription()).thenReturn(description);
        Condition condition = AttemptResultConditions.result(exceptionConditionMock);
        assertThat(condition.getDescription(), equalTo("result is " + description));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResult_nullCondition() {
        AttemptResultConditions.result(null);
    }
}
