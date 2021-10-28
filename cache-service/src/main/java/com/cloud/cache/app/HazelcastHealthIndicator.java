package com.cloud.cache.app;
import com.hazelcast.core.HazelcastInstance;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * {@link HealthIndicator} for Hazelcast.
 *
 */
@Component
public class HazelcastHealthIndicator extends AbstractHealthIndicator {

	private final HazelcastInstance hazelcast;

	public HazelcastHealthIndicator(HazelcastInstance hazelcast) {
		super("Hazelcast health check failed");
		Assert.notNull(hazelcast, "HazelcastInstance must not be null");
		this.hazelcast = hazelcast;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		this.hazelcast.executeTransaction((context) -> {
			builder.up().withDetail("name", this.hazelcast.getName()).withDetail("uuid",
					this.hazelcast.getLocalEndpoint().getUuid());
			return null;
		});
	}

}