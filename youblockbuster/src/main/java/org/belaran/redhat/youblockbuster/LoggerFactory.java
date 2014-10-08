package org.belaran.redhat.youblockbuster;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LoggerFactory {
	
    @Produces
    public Logger getLogger(InjectionPoint ip) {
        String category = ip.getMember().getDeclaringClass().getName();
        return Logger.getLogger(category);
    }  
    
    public static Logger getLoggerForCategory(String category) {
        return Logger.getLogger(category);    	
    }
}
