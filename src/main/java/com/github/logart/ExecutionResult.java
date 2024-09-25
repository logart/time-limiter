package com.github.logart;

import lombok.Value;

@Value
public class ExecutionResult<T> {
    private final T result;
    private final Throwable error;
    private final boolean isError;
    private final boolean isTimeout;
}
