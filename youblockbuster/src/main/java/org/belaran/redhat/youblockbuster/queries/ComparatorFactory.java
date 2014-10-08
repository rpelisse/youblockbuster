package org.belaran.redhat.youblockbuster.queries;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.belaran.redhat.youblockbuster.Movie;
import org.belaran.redhat.youblockbuster.MovieByRatingComparator;

public final class ComparatorFactory {

	private ComparatorFactory() {}
	
	public static Set<Query> buildQueries() {		
		Set<Query> queries = new HashSet<Query>(3);
		queries.add(new TopRating());
		queries.add(new TopVoted());
		queries.add(new TopPopularity());
		return queries;
	}

	public static Comparator<Movie> comparatorFactory(String queryId) {
		switch (queryId) {
		case "TopRating":
			return new MovieByRatingComparator();
		case "TopVoted":
			return new MovieByVotesComparator();
		case "TopPopularity":
			return new MovieByPopularityComparator();
		default:
			throw new UnsupportedOperationException("No comparator implementation for query:" + queryId);
		}
	}
}
