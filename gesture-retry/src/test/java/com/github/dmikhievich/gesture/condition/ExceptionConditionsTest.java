package com.github.dmikhievich.gesture.condition;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Dzmitry Mikhievich
 */
@RunWith(JUnitParamsRunner.class)
public class ExceptionConditionsTest {

    @Test
    public void testIsThrown_whenReturnedExceptionConditionIsAppliedForNullException_thenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isThrown();
        assertThat(condition.matches(null), is(false));
    }

    @Test
    public void testIsThrown_whenReturnedExceptionConditionIsAppliedForNonNullException_thenTrueShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isThrown();
        assertThat(condition.matches(new Exception()), is(true));
    }

    @Test
    public void testIsNotThrown_whenReturnedExceptionConditionIsAppliedForNullException_thenTrueShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isNotThrown();
        assertThat(condition.matches(null), is(true));
    }

    @Test
    public void testIsNotThrown_whenReturnedExceptionConditionIsAppliedForNonNullException_thenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isNotThrown();
        assertThat(condition.matches(new Exception()), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstanceOf_whenNullExceptionTypeIsPassed_thenIllegalArgumentExceptionShouldBeThrown() {
        ExceptionConditions.instanceOf(null);
    }

    @Test
    public void testInstanceOf_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToNullValue_ThenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.instanceOf(IllegalArgumentException.class);
        assertThat(condition.matches(null), is(false));
    }

    @Test
    public void testInstanceOf_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueOfSpecifiedType_ThenTrueShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.instanceOf(TestException.class);
        assertThat(condition.matches(new TestException()), is(true));
    }

    @Test
    public void testInstanceOf_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueOfNotSpecifiedType_ThenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.instanceOf(TestException.class);
        assertThat(condition.matches(new AnotherTestException()), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsAssignableFrom_whenNullExceptionTypeIsPassed_thenIllegalArgumentExceptionShouldBeThrown() {
        ExceptionConditions.isAssignableFrom(null);
    }

    @Test
    public void testIsAssignableFrom_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToNullValue_ThenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertThat(condition.matches(null), is(false));
    }

    @Test
    public void testIsAssignableFrom_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueOfSpecifiedType_ThenTrueShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertThat(condition.matches(new TestException()), is(true));
    }

    @Test
    public void testIsAssignableFrom_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueOfNotSpecifiedType_ThenFalseShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertThat(condition.matches(new AnotherTestException()), is(false));
    }

    @Test
    public void testIsAssignableFrom_whenNonNullExceptionTypeIsPassed_andReturnedExceptionConditionIsAppliedToValueWhichIsAssignableFromSpecifiedType_ThenTrueShouldBeReturned() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertThat(condition.matches(new ChildTestException()), is(true));
    }


    private static class TestException extends Exception {
    }

    private static class ChildTestException extends TestException {
    }

    private static class AnotherTestException extends Exception {
    }
}
