package com.gestureframework.retry.condition;

import javax.annotation.Nullable;

/**
 * Created by Dzmitry_Mikhievich.
 */
public interface Condition<T>  {

    boolean matches(@Nullable T value);

    String getDescription();

    default Condition<T> and(@Nullable Condition<T> other) {
        String description = String.format("(%s and %s)", getDescription(), other.getDescription());
        return new Condition<T>() {
            @Override
            public boolean matches(@Nullable T value) {
                return matches(value) && other.matches(value);
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }

    default Condition<T> or(Condition<T> other) {
        String description = String.format("(%s or %s)", getDescription(), other.getDescription());
        return new Condition<T>() {
            @Override
            public boolean matches(@Nullable T value) {
                return matches(value) || other.matches(value);
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }
    //TODO find more appropriate class
    static <T> Condition<T> not(Condition<T> condition) {
        return new Condition<T>() {
            @Override
            public boolean matches(@Nullable T value) {
                return !condition.matches(value);
            }

            @Override
            public String getDescription() {
                return String.format("not (%s)", condition.getDescription());
            }
        };
    }
}
