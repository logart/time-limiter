package com.github.logart;

import lombok.Value;

@Value
public class ExecutionResult<T> {
    private final T result;
    private final Throwable error;
    private final boolean isSuccessful;
    private final boolean isTimeout;
}
