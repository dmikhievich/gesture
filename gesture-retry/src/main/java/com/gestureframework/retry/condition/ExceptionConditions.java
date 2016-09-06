package com.gestureframework.retry.condition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.gestureframework.retry.condition.Condition.not;

/**
 * @author Dzmitry Mikhievich
 */
public final class ExceptionConditions {

    private ExceptionConditions() {}

    public static Condition<Exception> isThrown() {
        return ExceptionIsThrownCondition.getInstance();
    }

    public static Condition<Exception> isNotThrown() {
        return not(ExceptionIsThrownCondition.getInstance());
    }

    public static Condition<Exception> instanceOf(Class<? extends Exception> exceptionType) {
        return new ExceptionIsInstanceOfCondition(exceptionType);
    }

    private static final class ExceptionIsInstanceOfCondition implements Condition<Exception> {

        private final Class<? extends Exception> exceptionType;

        ExceptionIsInstanceOfCondition(@Nonnull Class<? extends Exception> exceptionType) {
            this.exceptionType = exceptionType;
        }

        @Override
        public boolean matches(@Nullable Exception value) {
            return exceptionType.isInstance(value);
        }

        @Override
        public String getDescription() {
            String type = exceptionType.getSimpleName();
            return String.format("exception is instance of [%s]", type);
        }
    }

    private static final class ExceptionIsThrownCondition implements Condition<Exception> {

        private static ExceptionIsThrownCondition instance;

        private ExceptionIsThrownCondition() {}

        static synchronized ExceptionIsThrownCondition getInstance() {
            if(instance == null) {
                instance = new ExceptionIsThrownCondition();
            }
            return instance;
        }

        @Override
        public boolean matches(@Nullable Exception value) {
            return value != null;
        }

        @Override
        public String getDescription() {
            return "is thrown";
        }
    }
}
