package com.cloud.util;
import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncResult
{

    @FunctionalInterface
    public interface AsyncHandler
    {
        public void handle();
    }

    @FunctionalInterface
    public interface AsyncExceptionHandler
    {
        public void handle(Throwable u);
    }

    public static AsyncExceptionHandler LogOpHandler()
    {
        return (e) -> {
            if (e != null)
                log.info("async exception {}", e);
            else
                log.info("async operation successfully executed");
        };
    }

    public static CompletableFuture<Void> runAsync(AsyncHandler async, AsyncExceptionHandler doWhenException)
    {
        return CompletableFuture.runAsync(() -> async.handle()).whenComplete((v, u) -> doWhenException.handle(u));
    }

}