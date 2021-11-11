package com.cloud.cache.app;

public interface DistributedLocker {
    void lockAndPerform(final String lockKey, Runnable fn);
}