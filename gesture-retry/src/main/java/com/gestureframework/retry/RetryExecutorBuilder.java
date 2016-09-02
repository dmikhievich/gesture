package com.gestureframework.retry;

import com.gestureframework.retry.policy.StopPolicy;
import com.gestureframework.retry.policy.WaitPolicy;

/**
 * Created by Dzmitry_Mikhievich.
 */
public class RetryExecutorBuilder {

    private WaitPolicy waitPolicy;
    private StopPolicy stopPolicy;

    public RetryExecutorBuilder withWaitPolicy(WaitPolicy waitPolicy) {
        this.waitPolicy = waitPolicy;
        return this;
    }

    public RetryExecutorBuilder withStopPolicy(StopPolicy stopPolicy) {
        this.stopPolicy = stopPolicy;
        return this;
    }

    public RetryExecutor build() {
        return new RetryExecutorImpl(stopPolicy, waitPolicy);
    }
}
