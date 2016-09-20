package com.github.dmikhievich.gesture;

import com.github.dmikhievich.gesture.policy.StopPolicy;
import com.github.dmikhievich.gesture.policy.WaitPolicy;

/**
 * Created by Dzmitry_Mikhievich.
 */
public final class RetryExecutorBuilder {

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
