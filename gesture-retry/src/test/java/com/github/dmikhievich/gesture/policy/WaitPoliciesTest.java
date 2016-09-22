package com.github.dmikhievich.gesture.policy;

import com.github.dmikhievich.gesture.Duration;
import com.github.dmikhievich.gesture.RetryContext;
import com.google.common.collect.Range;
import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Dzmitry Mikhievich
 */
@RunWith(JUnitParamsRunner.class)
public class WaitPoliciesTest {

    @Test
    public void testFixed_whenCalledForValidDelay_thenCorrectPolicyShouldBeReturned() {
        Duration delay = Duration.in(1, TimeUnit.HOURS);
        WaitPolicy waitPolicy = WaitPolicies.fixed(delay);
        assertThat(delay, equalTo(waitPolicy.getDelayBeforeNextAttempt(RetryContext.create())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFixed_delayIsNull() {
        WaitPolicies.fixed(null);
    }

    @Test
    public void testRandomInRange_whenCalledForValidDelay_thenPolicyWhichGeneratesRandom() {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        Range<Long> range = Range.closed(1L, 5L);
        WaitPolicy policy = WaitPolicies.randomInRange(timeUnit, range.lowerEndpoint(), range.upperEndpoint());
        Duration delay = policy.getDelayBeforeNextAttempt(RetryContext.create());

        assertThat(timeUnit, equalTo(delay.getTimeUnit()));
        assertThat(range.contains(delay.getValue()), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomInRange_timeUnitIsNull() {
        WaitPolicies.randomInRange(null, 0, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomInRange_lowerValueIsBelowZero() {
        WaitPolicies.randomInRange(TimeUnit.SECONDS, -5, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRandomInRange_lowerValueIsMoreThanUpper() {
        WaitPolicies.randomInRange(TimeUnit.SECONDS, 5, 4);
    }
}
