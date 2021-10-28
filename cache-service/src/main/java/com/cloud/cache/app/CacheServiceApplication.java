package com.cloud.cache.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.hazelcast.config.Config;
import com.hazelcast.config.PartitionGroupConfig.MemberGroupType;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class CacheServiceApplication
{

    @Value("${hazelcast.port:5701}")
    private int hazelcastPort;
    
    @Autowired
    Environment env;
    
    @Autowired
    ApplicationArguments args;

    
    
    @Bean
    public Config hazelcastConfig()
    {
        Config config = new Config();
        //management
//        config.getManagementCenterConfig().setEnabled(true);
//        config.getManagementCenterConfig().setUpdateInterval(1);
//        config.getManagementCenterConfig().setUrl("http://localhost:46305/hazelcast-mancenter");
        //

//        config.addMapConfig(urlPermissionMap());
        config.getNetworkConfig().setPort(hazelcastPort);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getEurekaConfig().setEnabled(true).setProperty("self-registration", "true")
                .setProperty("namespace", "hazelcast");
        //
        config.getPartitionGroupConfig().setEnabled(true).setGroupType(MemberGroupType.HOST_AWARE);
        return config;
    }


    public static void main(String[] args)
    {
        System.setProperty("hazelcast.internal.override.enterprise", "true");
        SpringApplication.run(CacheServiceApplication.class, args);
    }

}
