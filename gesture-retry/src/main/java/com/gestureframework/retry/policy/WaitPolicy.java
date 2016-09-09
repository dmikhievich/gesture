package com.gestureframework.retry.policy;

import com.gestureframework.retry.Duration;
import com.gestureframework.retry.RetryContext;

import javax.annotation.Nonnull;

/**
 * Created by Dzmitry_Mikhievich.
 */
public interface WaitPolicy {

    @Nonnull
    Duration getDelayBeforeNextAttempt(RetryContext context);
}
