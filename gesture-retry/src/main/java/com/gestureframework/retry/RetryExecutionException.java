package com.gestureframework.retry;

/**
 * Created by Dzmitry_Mikhievich.
 */
public class RetryExecutionException extends RuntimeException {

    public RetryExecutionException(String message) {
        super(message);
    }
}
