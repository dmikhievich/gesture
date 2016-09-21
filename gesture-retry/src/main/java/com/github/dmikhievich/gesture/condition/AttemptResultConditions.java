package com.github.dmikhievich.gesture.condition;

import com.github.dmikhievich.gesture.AttemptResult;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

/**
 * @author Dzmitry Mikhievich
 */
public final class AttemptResultConditions {

    private AttemptResultConditions() {}

    public static <T> Condition<AttemptResult<T>> exception(Condition<Exception> exceptionCondition) {
        checkArgument(exceptionCondition != null, "Condition can't be null");
        return new Condition<AttemptResult<T>>() {

            @Override
            public boolean matches(@Nullable AttemptResult<T> value) {
                Exception resultException = value != null ? value.getThrownException() : null;
                return exceptionCondition.matches(resultException);
            }

            @Override
            public String getDescription() {
                return format("exception is (%s)", exceptionCondition.getDescription());
            }
        };
    }

    public static <T> Condition<AttemptResult<T>> result(Condition<T> resultCondition) {
        checkArgument(resultCondition != null, "Condition can't be null");
        return new Condition<AttemptResult<T>>() {

            @Override
            public boolean matches(@Nullable AttemptResult<T> value) {
                T result = value != null ? value.getResult() : null;
                return resultCondition.matches(result);
            }

            @Override
            public String getDescription() {
                return format("result is (%s)", resultCondition.getDescription());
            }
        };
    }
}
