package com.gestureframework.retry;

import lombok.*;

/**
 * Created by Dzmitry_Mikhievich.
 */
@ToString
@EqualsAndHashCode
public class AttemptResult<T> {
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private T result;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private Exception thrownException;
}
