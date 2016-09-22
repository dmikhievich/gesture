package com.github.dmikhievich.gesture.condition;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dzmitry Mikhievich
 */
@SuppressWarnings("unchecked")
@RunWith(JUnitParamsRunner.class)
public class ConditionTest {

    private final Condition<Object> testCondition = (Condition<Object>) mock(Condition.class);
    private final Object argMock = mock(Object.class);

    @Before
    public void setupMocks() {
        when(testCondition.and(any())).thenCallRealMethod();
        when(testCondition.or(any())).thenCallRealMethod();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnd_whenCalledWithNullCondition_thenIllegalArgumentExceptionShouldBeThrown() {
        Condition<Object> nullableCondition = null;
        testCondition.and(nullableCondition);
    }


    @Test
    @Parameters({"true, true",
            "false, true",
            "true, false",
            "false, false"})
    public void testAnd_whenCalledWithValidCondition_thenCorrectAggregatedConditionShouldBeReturned(boolean baseCondResult, boolean otherCondResult) {
        when(testCondition.matches(argMock)).thenReturn(baseCondResult);
        Condition<Object> otherCondition = (Condition<Object>) mock(Condition.class);
        when(otherCondition.matches(argMock)).thenReturn(otherCondResult);

        Condition<Object> aggregatedCondition = testCondition.and(otherCondition);
        boolean expectedResult = baseCondResult && otherCondResult;
        assertThat(aggregatedCondition.matches(argMock), equalTo(expectedResult));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testOr_whenCalledWithNullCondition_thenIllegalArgumentExceptionShouldBeThrown() {
        Condition<Object> nullableCondition = null;
        testCondition.or(nullableCondition);
    }

    @Test
    @Parameters({"true, true",
            "false, true",
            "true, false",
            "false, false"})
    public void testOr_whenCalledWithValidCondition_thenCorrectAggregatedConditionShouldBeReturned(boolean baseCondResult, boolean otherCondResult) {
        when(testCondition.matches(argMock)).thenReturn(baseCondResult);
        Condition<Object> otherCondition = (Condition<Object>) mock(Condition.class);
        when(otherCondition.matches(argMock)).thenReturn(otherCondResult);

        Condition<Object> aggregatedCondition = testCondition.or(otherCondition);
        boolean expectedResult = baseCondResult || otherCondResult;
        assertThat(aggregatedCondition.matches(argMock), equalTo(expectedResult));
    }

    @Test
    @Parameters({"true", "false"})
    public void testNot_whenCalledWithNonNullCondition__thenCorrectAggregatedConditionShouldBeReturned(boolean result) {
        when(testCondition.matches(argMock)).thenReturn(result);

        Condition<Object> aggregatedCondition = Condition.not(testCondition);
        assertThat(aggregatedCondition.matches(argMock), equalTo(!result));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNot_whenCalledWithNullCondition_thenIllegalArgumentExceptionShouldBeThrown() {
        Condition.not(null);
    }
}
