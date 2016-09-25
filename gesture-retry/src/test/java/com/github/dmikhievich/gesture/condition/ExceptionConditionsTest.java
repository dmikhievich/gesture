package com.github.dmikhievich.gesture.condition;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Dzmitry Mikhievich
 */
@RunWith(JUnitParamsRunner.class)
public class ExceptionConditionsTest {

    @Test
    public void testIsThrown_returnedConditionAppliedToNullException() {
        Condition<Exception> condition = ExceptionConditions.isThrown();
        assertThat(condition.matches(null), is(false));
    }

    @Test
    public void testIsThrown_returnedConditionAppliedToNonNullException() {
        Condition<Exception> condition = ExceptionConditions.isThrown();
        assertThat(condition.matches(new Exception()), is(true));
    }

    @Test
    public void testIsThrown_returnedConditionHasAppropriateDescription() {
        Condition<Exception> condition = ExceptionConditions.isThrown();
        assertThat(condition.getDescription(), equalTo("thrown"));
    }

    @Test
    public void testIsNotThrown_returnedConditionAppliedToNullException() {
        Condition<Exception> condition = ExceptionConditions.isNotThrown();
        assertThat(condition.matches(null), is(true));
    }

    @Test
    public void testIsNotThrown_returnedConditionAppliedToNonNullException() {
        Condition<Exception> condition = ExceptionConditions.isNotThrown();
        assertThat(condition.matches(new Exception()), is(false));
    }

    @Test
    public void testIsNotThrown_returnedConditionHasAppropriateDescription() {
        Condition<Exception> condition = ExceptionConditions.isNotThrown();
        assertThat(condition.getDescription(), equalTo("not thrown"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInstanceOf_nullExceptionType() {
        ExceptionConditions.instanceOf(null);
    }

    @Test
    public void testInstanceOf_returnedExceptionConditionIsAppliedToNullValue() {
        Condition<Exception> condition = ExceptionConditions.instanceOf(IllegalArgumentException.class);
        assertThat(condition.matches(null), is(false));
    }

    @Test
    public void testInstanceOf_returnedExceptionConditionIsAppliedToValueOfSpecifiedType() {
        Condition<Exception> condition = ExceptionConditions.instanceOf(TestException.class);
        assertThat(condition.matches(new TestException()), is(true));
    }

    @Test
    public void testInstanceOf_returnedExceptionConditionIsAppliedToValueOfNotSpecifiedType() {
        Condition<Exception> condition = ExceptionConditions.instanceOf(TestException.class);
        assertThat(condition.matches(new AnotherTestException()), is(false));
    }

    @Test
    public void testInstanceOf_returnedConditionHasAppropriateDescription() {
        Condition<Exception> condition = ExceptionConditions.instanceOf(TestException.class);
        assertThat(condition.getDescription(), equalTo("instance of " + TestException.class.getSimpleName()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIsAssignableFrom_nullExceptionType() {
        ExceptionConditions.isAssignableFrom(null);
    }

    @Test
    public void testIsAssignableFrom_returnedConditionHasAppropriateDescription() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertThat(condition.getDescription(), equalTo("assignable from " + TestException.class.getSimpleName()));
    }

    @Test
    public void testIsAssignableFrom_returnedExceptionConditionIsAppliedToNullValue() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertThat(condition.matches(null), is(false));
    }

    @Test
    public void testIsAssignableFrom_returnedExceptionConditionIsAppliedToValueOfSpecifiedType() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertThat(condition.matches(new TestException()), is(true));
    }

    @Test
    public void testIsAssignableFrom_returnedExceptionConditionIsAppliedToValueOfNotSpecifiedType() {
        Condition<Exception> condition = ExceptionConditions.isAssignableFrom(TestException.class);
        assertThat(condition.matches(new AnotherTestException()), is(false));
    }

    @Test
    public void testIsAssignableFrom_returnedExceptionConditionIsAppliedToValueWhichIsAssignableFromSpecifiedType() {
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
