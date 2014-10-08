package org.belaran.redhat.youblockbuster;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class MovieUtils {

	private MovieUtils() {}
	
	public static Movie lastMovie(SortedSet<Movie> queryResult, Comparator<Movie> comparator) {
		if ( queryResult == null ) {
    		queryResult = new TreeSet<Movie>(comparator);
    		Movie movie = new Movie();
    		movie.setRating("7");
    		return movie;
		} else
			return queryResult.last();
	}

	
	public static int compareMovieRating(Movie left, Movie right) {
		Float leftRating = Float.valueOf(left.getRating());
		Float rightRating = Float.valueOf(right.getRating());
		
		if(  leftRating.floatValue() > rightRating.floatValue() ) 
			return 1;
		else if (leftRating.floatValue() < rightRating.floatValue())
			return -1;
		else
			return 0;
	}
	
	public static int compareMovieVotes(Movie left, Movie right) {
		Integer leftVotes = Integer.valueOf(left.getVote_count());
		Integer rightVotes = Integer.valueOf(right.getVote_count());
		
		if(  leftVotes.intValue() > rightVotes.intValue() ) 
			return 1;
		else if (leftVotes.intValue() < rightVotes.intValue() )
			return -1;
		else
			return 0;
	}

	public static int compareMoviePopularity(Movie left, Movie right) {
		Float leftRating = Float.valueOf(left.getPopularity());
		Float rightRating = Float.valueOf(right.getPopularity());
		
		if(  leftRating.floatValue() > rightRating.floatValue() ) 
			return 1;
		else if (leftRating.floatValue() < rightRating.floatValue())
			return -1;
		else
			return 0;
	}
	
	public static Map<String,Movie> indexedMoviesByIds(List<Movie> unindexedMovies) {
		Map<String,Movie> movies = new HashMap<String,Movie>(unindexedMovies.size());
		for ( Movie movie : unindexedMovies)
			movies.put(movie.getId(), movie);
		return movies;
	}
}
