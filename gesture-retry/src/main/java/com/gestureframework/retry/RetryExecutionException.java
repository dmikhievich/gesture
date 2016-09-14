package com.gestureframework.retry;

/**
 * Created by Dzmitry_Mikhievich.
 */
class RetryExecutionException extends RuntimeException {

    RetryExecutionException(String message) {
        super(message);
    }
}
