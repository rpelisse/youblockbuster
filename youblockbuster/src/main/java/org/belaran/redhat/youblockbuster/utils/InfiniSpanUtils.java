package org.belaran.redhat.youblockbuster.utils;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;

public class InfiniSpanUtils {

	private InfiniSpanUtils() { }
		
	public static GlobalConfiguration buildGlobalConfiguration() {
        return new GlobalConfigurationBuilder().clusteredDefault()
        		.transport().addProperty("configurationFile", "jgroups-udp.xml")
        		.globalJmxStatistics().allowDuplicateDomains(true).enable()
        		.build();
	}
	
	public static Configuration buildConfiguration(long entryLifespan) {
		return new ConfigurationBuilder().jmxStatistics().enable() 
		.clustering().cacheMode(CacheMode.DIST_SYNC)
		.hash().numOwners(2)
		.expiration().lifespan(entryLifespan)
		.build();
	}
}
