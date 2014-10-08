package org.belaran.redhat.youblockbuster.queries;

import java.util.Comparator;

import org.belaran.redhat.youblockbuster.Movie;
import org.belaran.redhat.youblockbuster.MovieUtils;

public class MovieByVotesComparator implements Comparator<Movie> {

	@Override
	public int compare(Movie o1, Movie o2) {
		return MovieUtils.compareMovieVotes(o1,o2);
	}

}
