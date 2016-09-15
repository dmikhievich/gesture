package com.github.dmikhievich.gesture.policy;

import com.github.dmikhievich.gesture.RetryContext;

import javax.annotation.Nonnull;

/**
 * Created by Dzmitry_Mikhievich.
 */
public interface StopPolicy {

    boolean shouldStopExecution(@Nonnull RetryContext context);
}
