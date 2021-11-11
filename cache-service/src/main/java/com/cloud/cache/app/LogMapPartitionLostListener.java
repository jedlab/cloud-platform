package com.cloud.cache.app;

import com.hazelcast.map.MapPartitionLostEvent;
import com.hazelcast.map.listener.MapPartitionLostListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogMapPartitionLostListener implements MapPartitionLostListener
{

    @Override
    public void partitionLost(MapPartitionLostEvent event)
    {
    	log.info("partitionLost {}", event.toString());
    }

}
