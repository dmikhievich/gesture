package com.gestureframework.retry;

import com.gestureframework.retry.condition.Condition;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

import static com.gestureframework.retry.Duration.in;
import static com.gestureframework.retry.condition.AttemptResultConditions.exception;
import static com.gestureframework.retry.condition.AttemptResultConditions.result;
import static com.gestureframework.retry.condition.ExceptionConditions.isNotThrown;
import static com.gestureframework.retry.policy.StopPolicies.stopOnAttempt;
import static com.gestureframework.retry.policy.WaitPolicies.fixed;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Dzmitry_Mikhievich.
 */
public class Runner {

    public static int count = 0;

    public static void main(String[] args) {

        RetryExecutor retryExecutor = new RetryExecutorBuilder()
                .withWaitPolicy(fixed(in(1, SECONDS)))
                .withStopPolicy(stopOnAttempt(3))
                .build();

        Callable<Integer> action = () -> {
            count += 1;
            if (count < 2) {
                throw new RuntimeException();
            } else {
                return count;
            }
        };

        Condition<Integer> isMoreThan2 = new Condition<Integer>() {

            @Override
            public boolean matches(@Nullable Integer value) {
                return value != null && value > 2;
            }

            @Override
            public String getDescription() {
                return "more than 2";
            }
        };

        retryExecutor.doWithRetry(action, result(isMoreThan2).and(exception(isNotThrown())));
    }


}
