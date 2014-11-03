package org.belaran.redhat.youblockbuster;

import org.belaran.redhat.youblockbuster.ws.MovieWrapper;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.event.CacheEntryCreatedEvent;
import org.infinispan.notifications.cachelistener.event.CacheEntryModifiedEvent;

@Listener
public class NewMovieListener {

	@CacheEntryCreated	
	public void newMovieCreated(CacheEntryCreatedEvent<String, MovieWrapper> event) {
		MovieWrapper movie = event.getValue();
		movie.setReadAvailable(false);
		processNewMovie(movie); // push to ESB request for update by SecretData
	}

	@CacheEntryModified
	public void print(CacheEntryModifiedEvent<String, MovieWrapper> event) {
		MovieWrapper movie = event.getValue();
		movie.setReadAvailable( MovieUtils.movieUpdated(movie));
	}
	
	public static void processNewMovie(MovieWrapper movie) {};
}
