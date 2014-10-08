package org.belaran.redhat.youblockbuster;

import java.util.Iterator;

import org.infinispan.distexec.mapreduce.Reducer;

public class AddEntryToQueryResultReducer implements Reducer<String, Movie> {

	private static final long serialVersionUID = 5622298564415344839L;
	
	@Override
	public Movie reduce(String key, Iterator<Movie> movies) {
		while ( movies.hasNext() ) {
			Movie movie = movies.next();
				return movie;
		}
		return null;
	}
}
