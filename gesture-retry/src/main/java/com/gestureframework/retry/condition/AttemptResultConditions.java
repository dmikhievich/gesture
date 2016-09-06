package com.gestureframework.retry.condition;

import com.gestureframework.retry.AttemptResult;

import javax.annotation.Nullable;

import static java.lang.String.format;

/**
 * @author Dzmitry Mikhievich
 */
public final class AttemptResultConditions {

    private AttemptResultConditions() {}

    public static <T> Condition<AttemptResult<T>> exception(@Nullable Condition<Exception> exceptionCondition) {
        return new Condition<AttemptResult<T>>() {

            @Override
            public boolean matches(@Nullable AttemptResult<T> value) {
                return exceptionCondition.matches(value.getThrownException());
            }

            @Override
            public String getDescription() {
                return format("exception (%s)", exceptionCondition.getDescription());
            }
        };
    }

    public static <T> Condition<AttemptResult<T>> result(Condition<T> resultCondition) {
        return new Condition<AttemptResult<T>>() {

            @Override
            public boolean matches(@Nullable AttemptResult<T> value) {
                return resultCondition.matches(value.getResult());
            }

            @Override
            public String getDescription() {
                return format("result (%s)", resultCondition.getDescription());
            }
        };
    }
}
