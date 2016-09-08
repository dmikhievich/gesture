package com.gestureframework.retry.condition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.gestureframework.retry.condition.Condition.not;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Dzmitry Mikhievich
 */
public final class ExceptionConditions {

    private ExceptionConditions() {
    }

    public static Condition<Exception> isThrown() {
        return ExceptionIsThrownCondition.getInstance();
    }

    public static Condition<Exception> isNotThrown() {
        return not(ExceptionIsThrownCondition.getInstance());
    }

    public static Condition<Exception> instanceOf(Class<? extends Exception> exceptionType) {
        checkArgument(exceptionType != null, "Exception type can't be null");
        return new ExceptionIsInstanceOfCondition(exceptionType);
    }

    public static Condition<Exception> isAssignableFrom(Class<? extends Exception> parentType) {
        checkArgument(parentType != null, "Exception type can't be null");
        return new ExceptionIsAssignableFromCondition(parentType);
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
            return String.format("instance of [%s]", type);
        }
    }

    private static final class ExceptionIsThrownCondition implements Condition<Exception> {

        private static ExceptionIsThrownCondition instance;

        private ExceptionIsThrownCondition() {
        }

        static synchronized ExceptionIsThrownCondition getInstance() {
            if (instance == null) {
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
            return "thrown";
        }
    }

    private static final class ExceptionIsAssignableFromCondition implements Condition<Exception> {

        private final Class<? extends Exception> exceptionType;

        ExceptionIsAssignableFromCondition(@Nonnull Class<? extends Exception> exceptionType) {
            this.exceptionType = exceptionType;
        }

        @Override
        public boolean matches(@Nullable Exception value) {
            return value != null && exceptionType.isAssignableFrom(value.getClass());
          }

        @Override
        public String getDescription() {
            String type = exceptionType.getSimpleName();
            return String.format("assignable of [%s]", type);
        }
    }
}
