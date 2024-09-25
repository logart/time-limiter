package com.github.logart;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.*;

@Slf4j
public class RedirectBuilder {
    private final ExecutorService timeLimiter = Executors.newCachedThreadPool();

    public <T> ExecutionResult<T> executeWithTimeLimit(Callable<T> timeLimitedFunction, Duration timeLimit) {
        Future<T> future = timeLimiter.submit(timeLimitedFunction);

        T result;
        try {
            result = future.get(timeLimit.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            return new ExecutionResult<>(null, e, true, false);
        } catch (TimeoutException e) {
            return new ExecutionResult<>(null, e, true, true);
        }
        return new ExecutionResult<>(result, null, false, false);
    }
}
