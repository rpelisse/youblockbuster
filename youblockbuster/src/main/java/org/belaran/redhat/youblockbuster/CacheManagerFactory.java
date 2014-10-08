package org.belaran.redhat.youblockbuster;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.belaran.redhat.youblockbuster.utils.InfiniSpanUtils;
import org.belaran.redhat.youblockbuster.utils.JndiUtils;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

@ApplicationScoped
public class CacheManagerFactory implements Serializable {

	private static final long serialVersionUID = -1873834895438350343L;

	private EmbeddedCacheManager manager;
    
    private static final long ENTRY_LIFESPAN = (60 * 1000) * 60; // 60 minutes

    @Produces
    public synchronized EmbeddedCacheManager getCacheManager() {    	
    	if ( manager == null ) {
	        manager = (EmbeddedCacheManager) new DefaultCacheManager(InfiniSpanUtils.buildGlobalConfiguration(), InfiniSpanUtils.buildConfiguration(ENTRY_LIFESPAN), true);
	        manager.start();
	        JndiUtils.registerCacheInJndi(manager);
    	}
    	return manager;
    }
    
    @PreDestroy
    public void cleanUp() {
    	JndiUtils.unregisterCacheInJndi();
    	if ( manager != null ) {
	        manager.stop();
	        manager = null;
    	}
    }
}

