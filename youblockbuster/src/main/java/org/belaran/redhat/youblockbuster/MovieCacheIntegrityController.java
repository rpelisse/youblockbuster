package org.belaran.redhat.youblockbuster;

import java.util.List;

import javax.inject.Inject;

import org.belaran.redhat.youblockbuster.ws.MoviesRestWS;
import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntriesEvicted;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.event.CacheEntriesEvictedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;

@Listener
public class MovieCacheIntegrityController {

  @Inject 
  private EmbeddedCacheManager manager;
	
  @CacheEntryCreated
  public void print(CacheEntryCreatedEvent<String, Movie> event) {
	  Cache<String, List<Movie>> queries = manager.getCache(MoviesRestWS.QUERIES_CACHE_NAME);
	  for ( String queryId: queries.keySet() ) {
		  List<Movie> content = queries.get(queryId);
	  }
  }
  
  @CacheEntryModified
  public void two(CacheEntryCreatedEvent event) {}

  @CacheEntriesEvicted
  public void noEvictIfIndexed(CacheEntriesEvictedEvent<String, Movie> event) {
	  
  }
}
