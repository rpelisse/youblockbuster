package org.belaran.redhat.youblockbuster.ws;

import org.belaran.redhat.youblockbuster.Movie;
import org.belaran.redhat.youblockbuster.queries.Query;
import org.infinispan.distexec.mapreduce.Collector;
import org.infinispan.distexec.mapreduce.Mapper;


public class EntryAboveThresholdMapper implements
		Mapper<String, Movie, String, Movie> {

	private static final long serialVersionUID = 7764128166176979624L;
	private Query query;
	private Movie lastMovie;
		
	public EntryAboveThresholdMapper(Query query, Movie lastMovie) {
		this.query = query;
		this.lastMovie = lastMovie;
	}

	@Override
	public void map(String key, Movie movie, Collector<String, Movie> collectedMovies) {
		if ( query.getComparator().compare(movie, lastMovie) > 0 ) {
			collectedMovies.emit(key, movie);
		} else
			System.out.println("Movie not collected:" + movie + "[key:" + key + "]");		
	}

}
