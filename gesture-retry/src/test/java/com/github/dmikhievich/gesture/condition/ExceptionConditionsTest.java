package com.github.dmikhievich.gesture.condition;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Dzmitry Mikhievich
 */
@RunWith(JUnitParamsRunner.class)
public class ExceptionConditionsTest {

    @Test
    public void testIsThrown_whenReturnedExceptionConditionIsAppliedForNullException_thenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isThrown();
        assertFalse(condition.matches(null));
    }

    @Test
    public void testIsThrown_whenReturnedExceptionConditionIsAppliedForNonNullException_thenTrueShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isThrown();
        assertTrue(condition.matches(new Exception()));
    }

    @Test
    public void testIsNotThrown_whenReturnedExceptionConditionIsAppliedForNullException_thenTrueShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isNotThrown();
        assertTrue(condition.matches(null));
    }

    @Test
    public void testIsNotThrown_whenReturnedExceptionConditionIsAppliedForNonNullException_thenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isNotThrown();
        assertFalse(condition.matches(new Exception()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstanceOf_whenNullExceptionTypeIsPassed_thenIllegalArgumentExceptionShouldBeThrown() {
        ExceptionConditions.instanceOf(null);
    }

    @Test
    public void testInstanceOf_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToNullValue_ThenFalseShouldBeReturned() {
        Condition<Exception> condition =ExceptionConditions.instanceOf(IllegalArgumentException.class);
        assertFalse(condition.matches(null));
    }

    @Test
    public void testInstanceOf_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueOfSpecifiedType_ThenTrueShouldBeReturned() {
        Condition<Exception> condition =ExceptionConditions.instanceOf(TestException.class);
        assertTrue(condition.matches(new TestException()));
    }

    @Test
    public void testInstanceOf_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueOfNotSpecifiedType_ThenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.instanceOf(TestException.class);
        assertFalse(condition.matches(new AnotherTestException()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsAssignableFrom_whenNullExceptionTypeIsPassed_thenIllegalArgumentExceptionShouldBeThrown() {
        ExceptionConditions.isAssignableFrom(null);
    }

    @Test
    public void testIsAssignableFrom_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToNullValue_ThenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertFalse(condition.matches(null));
    }

    @Test
    public void testIsAssignableFrom_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueOfSpecifiedType_ThenTrueShouldBeReturned() {
        Condition<Exception> condition =ExceptionConditions.isAssignableFrom(TestException.class);
        assertTrue(condition.matches(new TestException()));
    }

    @Test
    public void testIsAssignableFrom_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueOfNotSpecifiedType_ThenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertFalse(condition.matches(new AnotherTestException()));
    }

    @Test
    public void testIsAssignableFrom_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueWhichIsAssignableFromSpecifiedType_ThenTrueShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertTrue(condition.matches(new ChildTestException()));
    }


    private static class TestException extends Exception {}

    private static class ChildTestException extends TestException {}

    private static class AnotherTestException extends Exception {}
}
