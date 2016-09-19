package com.github.dmikhievich.gesture;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static nl.jqno.equalsverifier.Warning.ALL_FIELDS_SHOULD_BE_USED;
import static nl.jqno.equalsverifier.Warning.NONFINAL_FIELDS;
import static nl.jqno.equalsverifier.Warning.NULL_FIELDS;
import static org.apache.commons.lang3.reflect.FieldUtils.readDeclaredField;
import static org.apache.commons.lang3.reflect.FieldUtils.writeDeclaredField;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        assertEquals(duration.getTimeUnit(), timeUnit);
        assertEquals(duration.getValue(), value);
    }

    @Test
    public void testToNanos_shouldReturnCorrectDurationInNanos() {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long value = 12L;
        long expectedNanos = 12000000L;
        assertEquals(Duration.in(value, timeUnit).toNanos(), expectedNanos);
    }

    @Test
    public void testToNanos_whenCalledFirstTime_thenValueShouldBeCached() throws IllegalAccessException {
        Duration duration = Duration.in(1, TimeUnit.SECONDS);
        long expectedNanos = 1000000000L;
        assertEquals(expectedNanos, duration.toNanos());
        assertEquals(readDeclaredField(duration, "valueInNanos", true), expectedNanos);
    }

    @Test
    public void testToNanos_whenCalledSecondAndSubsequentTimes_thenCachedValueShouldBeReturned() throws IllegalAccessException {
        Duration duration = Duration.in(1, TimeUnit.SECONDS);
        long cachedDurationInNanos = 100L;
        writeDeclaredField(duration, "valueInNanos", cachedDurationInNanos, true);
        assertEquals(cachedDurationInNanos, duration.toNanos());
    }

    @Test
    @Parameters({"1000|1000", "1000|999"})
    @TestCaseName("first: {0} nanos, second: {1} nanos")
    public void testIsMoreOrEquals_whenNanosDurationOfThisIsMoreOrEqualThanAnother_thenTrueShouldBeReturned(long one, long another) {
        Duration duration = mock(Duration.class);
        when(duration.toNanos()).thenReturn(one);
        when(duration.isMoreOrEquals(any())).thenCallRealMethod();
        Duration anotherDuration = mock(Duration.class);
        when(anotherDuration.toNanos()).thenReturn(another);

        assertTrue(duration.isMoreOrEquals(anotherDuration));
    }

    @Test
    public void testIsMoreOrEquals_whenNanosDurationOfThisIsLessThanAnother_thenFalseShouldBeReturned() {
        long bearingValue = 100L;
        Duration duration = mock(Duration.class);
        when(duration.toNanos()).thenReturn(bearingValue);
        when(duration.isMoreOrEquals(any())).thenCallRealMethod();
        Duration anotherDuration = mock(Duration.class);
        when(anotherDuration.toNanos()).thenReturn(bearingValue + 1);

        assertFalse(duration.isMoreOrEquals(anotherDuration));
    }

    @Test
    public void testEqualsContract() {
        EqualsVerifier.forClass(Duration.class)
                .suppress(NONFINAL_FIELDS, NULL_FIELDS, ALL_FIELDS_SHOULD_BE_USED)
                .verify();
    }
}
