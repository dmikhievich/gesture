package com.gestureframework.retry.condition;


import javax.annotation.Nullable;

/**
 * Created by Dzmitry_Mikhievich.
 */
//TODO remove
public class Conditions {

    //TODO move to the appropriate class
    public static <T> Condition<T> not(Condition<T> condition) {
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
