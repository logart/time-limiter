package com.github.logart;

import java.time.Duration;
import java.util.concurrent.*;

public class TimeLimiter implements AutoCloseable {
    private final ExecutorService timeLimiter = Executors.newCachedThreadPool();

    public <T> ExecutionResult<T> executeWithTimeLimit(Callable<T> timeLimitedFunction, Duration timeLimit) {
        Future<T> future = timeLimiter.submit(timeLimitedFunction);

        T result;
        try {
            result = future.get(timeLimit.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException e) {
            return new ExecutionResult<T>(null, e, true, false);
        } catch (TimeoutException e) {
            return new ExecutionResult<T>(null, e, true, true);
        }
        return new ExecutionResult<T>(result, null, false, false);
    }

    @Override
    public void close() throws Exception {
        timeLimiter.shutdown();
    }
}
