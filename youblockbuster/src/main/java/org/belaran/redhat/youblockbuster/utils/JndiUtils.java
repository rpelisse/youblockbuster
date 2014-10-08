package org.belaran.redhat.youblockbuster.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.belaran.redhat.youblockbuster.LoggerFactory;
import org.belaran.redhat.youblockbuster.ws.MoviesRestWS;
import org.infinispan.manager.EmbeddedCacheManager;

public final class JndiUtils {

    public static final String JNDI_CACHE_NAME = "java:/" + MoviesRestWS.MOVIES_CACHE_NAME;
	
	private JndiUtils() {}

	private static void log(String msg) {
    	LoggerFactory.getLoggerForCategory(JndiUtils.class.getCanonicalName()).info(msg);;
	}
	
    public static void registerCacheInJndi(EmbeddedCacheManager cache) {
        try {
			Context ctx = new InitialContext();
			ctx.bind(JNDI_CACHE_NAME, cache);
			Object o = ctx.lookup(JNDI_CACHE_NAME);
			log("Retrieved from JNDI:" + o.getClass());
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
    }
    
    public static void unregisterCacheInJndi() {
        try {
			new InitialContext().removeFromEnvironment(JNDI_CACHE_NAME);
		} catch (NamingException e) {
			throw new IllegalStateException(e);
		}
    }
}
