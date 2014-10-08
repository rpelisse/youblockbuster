package org.belaran.redhat.youblockbuster;

import java.util.Comparator;

public class MovieByRatingComparator implements Comparator<Movie> {

	@Override
	public int compare(Movie o1, Movie o2) {
		return MovieUtils.compareMovieRating(o1, o2);
	}
}
