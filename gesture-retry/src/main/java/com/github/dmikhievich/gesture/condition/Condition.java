package com.github.dmikhievich.gesture.condition;

import javax.annotation.Nullable;

import static com.github.dmikhievich.gesture.util.ConditionUtils.buildCompositeDescription;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Dzmitry_Mikhievich.
 */
public interface Condition<T> {

    boolean matches(@Nullable T value);

    String getDescription();

    default boolean isComposite() {
        return false;
    }

    default Condition<T> and(Condition<T> other) {
        checkArgument(other != null, "Linkable condition can't be null");
        String description = buildCompositeDescription(this, "and", other);
        return new Condition<T>() {
            @Override
            public boolean matches(@Nullable T value) {
                return Condition.this.matches(value) && other.matches(value);
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public boolean isComposite() {
                return true;
            }
        };
    }

    default Condition<T> or(Condition<T> other) {
        checkArgument(other != null, "Linkable condition can't be null");
        String description = buildCompositeDescription(this, "or", other);
        return new Condition<T>() {
            @Override
            public boolean matches(@Nullable T value) {
                return Condition.this.matches(value) || other.matches(value);
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public boolean isComposite() {
                return true;
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
                return buildCompositeDescription(null, "not", condition);
            }
        };
    }

}
