package com.gestureframework.retry.policy;

import com.gestureframework.retry.Duration;
import com.gestureframework.retry.RetryContext;

/**
 * Created by Dzmitry_Mikhievich.
 */
public interface WaitPolicy {

    Duration getDelayBeforeNextAttempt(RetryContext context);

}
