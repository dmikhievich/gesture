package com.github.dmikhievich.gesture.condition;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Dzmitry_Mikhievich.
 */
public interface Condition<T> {

    boolean matches(@Nullable T value);

    String getDescription();

    default Condition<T> and(Condition<T> other) {
        checkArgument(other != null, "Linkable condition can't be null");
        String description = String.format("(%s) and (%s)", getDescription(), other.getDescription());
        return new Condition<T>() {
            @Override
            public boolean matches(@Nullable T value) {
                return Condition.this.matches(value) && other.matches(value);
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }

    default Condition<T> or(Condition<T> other) {
        checkArgument(other != null, "Linkable condition can't be null");
        String description = String.format("(%s) or (%s)", getDescription(), other.getDescription());
        return new Condition<T>() {
            @Override
            public boolean matches(@Nullable T value) {
                return Condition.this.matches(value) || other.matches(value);
            }

            @Override
            public String getDescription() {
                return description;
            }
        };
    }

    //TODO find more appropriate class
    static <T> Condition<T> not(Condition<T> condition) {
        checkArgument(condition != null, "Linkable condition can't be null");
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
