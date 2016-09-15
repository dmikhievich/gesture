package com.github.dmikhievich.gesture;

import com.github.dmikhievich.gesture.condition.Condition;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;

import static com.github.dmikhievich.gesture.condition.AttemptResultConditions.exception;
import static com.github.dmikhievich.gesture.condition.AttemptResultConditions.result;
import static com.github.dmikhievich.gesture.condition.ExceptionConditions.isNotThrown;
import static com.github.dmikhievich.gesture.policy.StopPolicies.stopOnAttempt;
import static com.github.dmikhievich.gesture.policy.WaitPolicies.fixed;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Dzmitry_Mikhievich.
 */
public class Runner {

    public static int count = 0;

    public static void main(String[] args) {

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

        RetryExecutor retryExecutor = new RetryExecutorBuilder()
                .withWaitPolicy(fixed(Duration.in(1, SECONDS)))
                .withStopPolicy(stopOnAttempt(3))
                .build();

        retryExecutor.doWithRetry(action, result(isMoreThan2).and(exception(isNotThrown())));
    }


}
