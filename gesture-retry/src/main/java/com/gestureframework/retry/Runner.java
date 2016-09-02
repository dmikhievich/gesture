package com.gestureframework.retry;
import java.util.concurrent.Callable;

import static com.gestureframework.retry.Duration.in;
import static com.gestureframework.retry.condition.AttemptResultConditions.exception;
import static com.gestureframework.retry.condition.ExceptionConditions.isNotThrown;
import static com.gestureframework.retry.policy.StopPolicies.*;
import static com.gestureframework.retry.policy.WaitPolicies.fixed;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Dzmitry_Mikhievich.
 */
public class Runner {

    public static int count = 1;

    public static void main(String[] args) {

        RetryExecutor retryExecutor = new RetryExecutorBuilder()
                                          .withWaitPolicy(fixed(in(1, SECONDS)))
                                          .withStopPolicy(stopOnAttempt(3))
                                          .build();

        Callable<Integer> action = () ->{
            count += 1;
            if(count < 3) {
                throw new RuntimeException();
            }
            return 3;
        };

        retryExecutor.doWithRetry(action, exception(isNotThrown()));
    }



}
