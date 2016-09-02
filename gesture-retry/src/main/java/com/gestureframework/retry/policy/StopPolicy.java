package com.gestureframework.retry.policy;

import com.gestureframework.retry.RetryContext;

/**
 * Created by Dzmitry_Mikhievich.
 */
public interface StopPolicy {

    boolean shouldStopExecution(RetryContext context);

}
