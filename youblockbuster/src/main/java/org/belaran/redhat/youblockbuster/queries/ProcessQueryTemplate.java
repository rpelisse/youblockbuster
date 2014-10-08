package org.belaran.redhat.youblockbuster.queries;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.belaran.redhat.youblockbuster.AddEntryToQueryResultReducer;
import org.belaran.redhat.youblockbuster.Movie;
import org.belaran.redhat.youblockbuster.MovieUtils;
import org.belaran.redhat.youblockbuster.utils.CollectionsUtils;
import org.belaran.redhat.youblockbuster.ws.EntryAboveThresholdMapper;
import org.infinispan.Cache;
import org.infinispan.distexec.mapreduce.MapReduceTask;


public class ProcessQueryTemplate {

	protected  static  Movie retrieveLastMovieOfList(SortedSet<Movie> queryResult, Query query) {
    	System.out.println("Find the last entry of the list - if none, create one with a fixed rating");
		return MovieUtils.lastMovie(queryResult,query.getComparator() );
	}

	protected  static  Map<String, Movie> runMapReduce(Cache<String,Movie> cache, Movie lastMovie, Query query) {
    	System.out.println("Recompute results with current grid content");
        MapReduceTask<String, Movie, String, Movie> entrySetCollector = new MapReduceTask<String, Movie, String, Movie>(cache);
        entrySetCollector.mappedWith(new EntryAboveThresholdMapper(query,lastMovie))
        	.reducedWith(new AddEntryToQueryResultReducer());
        
        System.out.println("Executing map reduce on cache " + cache.getName() + ", which contains:" + cache.keySet().size());
        return entrySetCollector.execute();		
	}

	protected static SortedSet<Movie> reconstructResultSet(SortedSet<Movie> queryResult, Query query, Collection<Movie> results) {
        SortedSet<Movie> orderedMovies = CollectionsUtils.sortCollection(results, query.getComparator());
        if ( queryResult == null )	
        	queryResult = new TreeSet<Movie>(query.getComparator());
        if (queryResult.isEmpty() ) 
        	queryResult = orderedMovies;
        else
        	queryResult = CollectionsUtils.updateMovieListIfNeeded(queryResult, orderedMovies, query.getNbResultsMax());
        // Keeps only the requested number of entries
        return queryResult.tailSet(CollectionsUtils.findLastItem(queryResult, 
        		queryResult.size() - query.getNbResultsMax() + 1));
	}
	
	public static SortedSet<Movie> computeQuery(SortedSet<Movie> queryResult, Query query, Cache<String,Movie> cache) {
		Movie lastMovieInTheStack = retrieveLastMovieOfList(queryResult, query);
    	Map<String, Movie> distributedCacheMap = runMapReduce(cache, lastMovieInTheStack, query);
    	return reconstructResultSet(queryResult, query, distributedCacheMap.values());
	}
}
