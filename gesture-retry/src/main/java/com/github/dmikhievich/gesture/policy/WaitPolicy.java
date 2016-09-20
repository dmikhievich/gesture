package com.github.dmikhievich.gesture.policy;

import com.github.dmikhievich.gesture.Duration;
import com.github.dmikhievich.gesture.RetryContext;

import javax.annotation.Nonnull;

/**
 * Created by Dzmitry_Mikhievich.
 */
public interface WaitPolicy {

    @Nonnull
    Duration getDelayBeforeNextAttempt(@Nonnull RetryContext context);
}
