package com.cloud.cache.app;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class HazelcastDistributedLocker implements DistributedLocker {
    private final IMap<Object, Object> locks;
 
    public HazelcastDistributedLocker(HazelcastInstance hazelcast) {
        locks = hazelcast.getMap("lock");
    }
 
    @Override
    public void lockAndPerform(String lockKey, Runnable fn) {
        try {
            locks.lock(lockKey);
            fn.run();
        } finally {
            locks.unlock(lockKey);
        }
    }
}