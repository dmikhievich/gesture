package com.gestureframework.retry.condition;

import com.gestureframework.retry.AttemptResult;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * @author Dzmitry Mikhievich
 */
public class AttemptResultConditionsTest {

    private AttemptResult testCondition = mock(AttemptResult.class);

    @Test(expected = IllegalArgumentException.class)
    public void testException_whenCalledForNullCondition_thenIllegalArgumentExceptionShouldBeThrown() {
        AttemptResultConditions.exception(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResult_whenCalledForNullCondition_thenIllegalArgumentExceptionShouldBeThrown() {
        AttemptResultConditions.result(null);
    }
}
