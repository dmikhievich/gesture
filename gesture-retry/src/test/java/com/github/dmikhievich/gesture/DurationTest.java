package com.github.dmikhievich.gesture;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static nl.jqno.equalsverifier.Warning.*;
import static org.apache.commons.lang3.reflect.FieldUtils.readDeclaredField;
import static org.apache.commons.lang3.reflect.FieldUtils.writeDeclaredField;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


/**
 * @author Dzmitry Mikhievich
 */
@RunWith(JUnitParamsRunner.class)
public class DurationTest {

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"-12", "0"})
    public void testIn_whenCalledWithInvalidValue_IllegalArgumentShouldBeThrown(long illegalValue) {
        Duration.in(illegalValue, TimeUnit.SECONDS);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIn_whenCalledWithValidValueAndNullTimeUnit_IllegalArgumentShouldBeThrown() {
        Duration.in(100, null);
    }


    @Test
    public void testIn_whenCalledWithValidValueAndTimeUnit_thenValidDurationShouldBeReturned() {
        TimeUnit timeUnit = TimeUnit.HOURS;
        long value = 12L;
        Duration duration = Duration.in(value, timeUnit);
        assertThat(duration.getTimeUnit(), equalTo(timeUnit));
        assertThat(duration.getValue(), equalTo(value));
    }

    @Test
    public void testToNanos_shouldReturnCorrectDurationInNanos() {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long value = 12L;
        long expectedNanos = 12000000L;
        assertThat(Duration.in(value, timeUnit).toNanos(), equalTo(expectedNanos));
    }

    @Test
    public void testToNanos_whenCalledFirstTime_thenValueShouldBeCached() throws IllegalAccessException {
        Duration duration = Duration.in(1, TimeUnit.SECONDS);
        long expectedNanos = 1000000000L;
        assertThat(expectedNanos, equalTo(duration.toNanos()));
        assertThat(readDeclaredField(duration, "valueInNanos", true), equalTo(expectedNanos));
    }

    @Test
    public void testToNanos_whenCalledSecondAndSubsequentTimes_thenCachedValueShouldBeReturned() throws IllegalAccessException {
        Duration duration = Duration.in(1, TimeUnit.SECONDS);
        long cachedDurationInNanos = 100L;
        writeDeclaredField(duration, "valueInNanos", cachedDurationInNanos, true);

        assertThat(cachedDurationInNanos, equalTo(duration.toNanos()));
    }

    @Test
    @Parameters({"1000|1000", "1000|999"})
    @TestCaseName("first: {0} nanos, second: {1} nanos")
    public void testIsMoreOrEquals_whenNanosDurationOfThisIsMoreOrEqualThanAnother_thenTrueShouldBeReturned(long one, long another) {
        Duration duration = Duration.in(one, TimeUnit.NANOSECONDS);
        Duration anotherDuration = Duration.in(another, TimeUnit.NANOSECONDS);

        assertThat(duration.isMoreOrEquals(anotherDuration), is(true));
    }

    @Test
    public void testIsMoreOrEquals_whenNanosDurationOfThisIsLessThanAnother_thenFalseShouldBeReturned() {
        long bearingValue = 100L;
        Duration duration = Duration.in(bearingValue, TimeUnit.NANOSECONDS);
        Duration anotherDuration = Duration.in(bearingValue + 1, TimeUnit.NANOSECONDS);

        assertThat(duration.isMoreOrEquals(anotherDuration), is(false));
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(Duration.class)
                .suppress(NONFINAL_FIELDS, NULL_FIELDS, ALL_FIELDS_SHOULD_BE_USED)
                .verify();
    }
}
