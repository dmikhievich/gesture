package com.gestureframework.retry.policy;

import com.gestureframework.retry.RetryContext;

import javax.annotation.Nonnull;

/**
 * Created by Dzmitry_Mikhievich.
 */
public interface StopPolicy {

    boolean shouldStopExecution(@Nonnull RetryContext context);
}
