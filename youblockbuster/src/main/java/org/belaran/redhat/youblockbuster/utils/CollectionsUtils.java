package org.belaran.redhat.youblockbuster.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.belaran.redhat.youblockbuster.Movie;
import org.belaran.redhat.youblockbuster.Movies;
import org.belaran.redhat.youblockbuster.ws.MovieWrapper;

public final class CollectionsUtils {

	private CollectionsUtils() {}
	

	public static <T> SortedSet<T> sortCollection(Collection<T> collections, Comparator<T> comparator) {
		if ( collections == null  )
			throw new IllegalArgumentException("Provided collections is empty or 'null'.");
		if ( comparator == null ) 
			throw new IllegalArgumentException("No comparator instance provided.");

		SortedSet<T> set = new TreeSet<T>(comparator);
		for ( T movie : collections ) {
			set.add(movie);
		}
		return set;
	}
	
	public static <T> SortedSet<T> updateMovieListIfNeeded(SortedSet<T> queryResult,
			SortedSet<T> orderedMovies, int nbResultsMax) {		
		// add incoming to existing list - order ensure by TreeSet
		queryResult.addAll(orderedMovies);
		// keep only the "top of list", cap by the query spec
		return queryResult;
	}
	
	public static <T> SortedSet<T> buildIdnitialList(SortedSet<T> queryResult, SortedSet<T> orderedMovies, int nbMovieInResult) {        
    	return orderedMovies;
	}
	
	public static Movies turnSortedSetIntoList(SortedSet<Movie> resultSet) {
		List<Movie> list = new ArrayList<Movie>(resultSet);
		Movies movies = new Movies();
		movies.setWrappers(buildFromMovieList(list));		
		return movies;
	}
	
	public static List<MovieWrapper> buildFromMovieList(List<Movie> movies) {
		List<MovieWrapper> movieWrappers = new ArrayList<MovieWrapper>(movies.size());
		for ( Movie movie : movies ) 
			movieWrappers.add(new MovieWrapper(movie));
		return movieWrappers;
	}
	
	public static <T> T findLastItem(SortedSet<T> queryResult, int lastItemPosition) {
		if ( queryResult == null || queryResult.isEmpty() )
			throw new IllegalArgumentException("Provided result set is 'null' or empty.");
		
		Iterator<T> resultSetIterator = queryResult.iterator();
		int nbItemInList = 0;
		T lastItem = null;
		while (resultSetIterator.hasNext() && nbItemInList++ < lastItemPosition ) 
			lastItem = resultSetIterator.next();
		return lastItem;
	}
	
	public static <T> T findLastItemAtPosition(SortedSet<T> queryResult, int itemPosition) {
		if ( queryResult == null || queryResult.isEmpty() )
			throw new IllegalArgumentException("Provided result set is 'null' or empty.");
		
		Iterator<T> resultSetIterator = queryResult.iterator();
		int nbItemInList = 0;
		T lastItem = null;
		while (resultSetIterator.hasNext() && nbItemInList++ < itemPosition ) 
			lastItem = resultSetIterator.next();
		return lastItem;
	}
}
