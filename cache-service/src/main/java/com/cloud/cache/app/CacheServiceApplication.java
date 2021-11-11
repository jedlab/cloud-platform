package com.cloud.cache.app;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.cloud.cache.app.mapstore.PersistenceMapStore;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapPartitionLostListenerConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.MapStoreConfig.InitialLoadMode;
import com.hazelcast.config.PartitionGroupConfig.MemberGroupType;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CacheServiceApplication {

	@Value("${hazelcast.port:5701}")
	private int hazelcastPort;

	@Autowired
	Environment env;

	@Autowired
	ApplicationArguments args;

	@Bean
	public Config hazelcastConfig(JdbcTemplate jdbcTemplate, PlatformTransactionManager ptm) {
		Config config = new Config();
		// management
//        config.getManagementCenterConfig().setEnabled(true);
//        config.getManagementCenterConfig().setUpdateInterval(1);
//        config.getManagementCenterConfig().setUrl("http://localhost:46305/hazelcast-mancenter");
		//
		config.addMapConfig(nonPersistenceMap());
		config.addMapConfig(persistenceMap(jdbcTemplate, ptm));
		config.addMapConfig(lockMap());
		config.getNetworkConfig().setPort(hazelcastPort);
		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getEurekaConfig().setEnabled(true).setProperty("self-registration", "true")
				.setProperty("namespace", "hazelcast");
		//
		config.getPartitionGroupConfig().setEnabled(true).setGroupType(MemberGroupType.HOST_AWARE);
		return config;
	}

	private MapConfig nonPersistenceMap() {
		MapConfig mc = new MapConfig("map");
		//
		return mc;
	}
	
	private MapConfig lockMap() {
		MapConfig mc = new MapConfig("lock");
		//
		return mc;
	}

	private MapConfig persistenceMap(JdbcTemplate jdbcTemplate, PlatformTransactionManager ptm) {
		MapConfig mc = new MapConfig("persistence-map");
		MapStoreConfig msc = new MapStoreConfig();
		msc.setImplementation(new PersistenceMapStore(jdbcTemplate, ptm));
		msc.setEnabled(true);
		msc.setInitialLoadMode(InitialLoadMode.LAZY);
		mc.setMapStoreConfig(msc);
		mc.setPartitionLostListenerConfigs(
				Arrays.asList(new MapPartitionLostListenerConfig(new LogMapPartitionLostListener())));
		return mc;
	}

	public static void main(String[] args) {
		System.setProperty("hazelcast.internal.override.enterprise", "true");
		SpringApplication.run(CacheServiceApplication.class, args);
	}

}
