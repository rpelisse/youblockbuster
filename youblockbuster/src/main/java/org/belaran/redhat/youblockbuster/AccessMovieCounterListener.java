package org.belaran.redhat.youblockbuster;

import org.infinispan.Cache;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryVisited;
import org.infinispan.notifications.cachelistener.event.CacheEntryVisitedEvent;

@Listener
public class AccessMovieCounterListener {

	
	private Cache<String, Integer> cache;
	
	public AccessMovieCounterListener(Cache<String,Integer> cache) {
		this.cache = cache;
	}
	
	@CacheEntryVisited
	public void reportMissingMovie(CacheEntryVisitedEvent<String, Movie> event) {
		if ( cache.containsKey(event.getKey())) {
			int sum = cache.get(event.getKey());
			cache.put(event.getKey(), ++sum);
		} else
			cache.put(event.getKey(),1);
	}
}
