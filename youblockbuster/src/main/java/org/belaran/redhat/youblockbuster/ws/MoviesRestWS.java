/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.belaran.redhat.youblockbuster.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.belaran.redhat.youblockbuster.Movie;
import org.belaran.redhat.youblockbuster.MovieUtils;
import org.belaran.redhat.youblockbuster.Movies;
import org.belaran.redhat.youblockbuster.queries.ComparatorFactory;
import org.belaran.redhat.youblockbuster.queries.ProcessQueryTemplate;
import org.belaran.redhat.youblockbuster.queries.Query;
import org.belaran.redhat.youblockbuster.utils.CollectionsUtils;
import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;

@Path("/")
@ApplicationScoped
public class MoviesRestWS {

    @Inject
    private Logger logger;

    @Inject
	private EmbeddedCacheManager manager;

    public static final String MOVIES_CACHE_NAME = "movies";
    public static final String QUERIES_CACHE_NAME = "queries";
    private static final String QUERY_IMPL_MAP = "query-impl";

    public Cache<String,Movie> getMovieCache() {
		return manager.getCache(MOVIES_CACHE_NAME);
    }

    public Cache<String,SortedSet<Movie>> getQueriesCache() {
		return manager.getCache(QUERIES_CACHE_NAME);
    }

    public Cache<String,Query> getQueryImplMap() {
		return manager.getCache(QUERY_IMPL_MAP);
    }

    @PostConstruct
    public void initializeCaches() {
    	getMovieCache();
    	getQueriesCache();
    	Cache<String,Query> queriesImplCache = getQueryImplMap();
		for ( Query query : ComparatorFactory.buildQueries() )
			queriesImplCache.put(query.getClass().getSimpleName(), query);
    }

    @GET
    @Path("/check")
	@Produces("text/plain")
    public Response test() {
    	String response = "Service:" + this.getClass().getCanonicalName() + " is running caches [";
    	for ( String cacheNames : this.manager.getCacheNames() )
    		response += cacheNames + " ";
    	response += "]\n";
    	return  Response.ok(response).build();
    }

    private static MovieWrapper validate(MovieWrapper movie) {
		if (movie == null || "".equals(movie.getId()) )
			throw new IllegalArgumentException(
					"Movie provided is either 'null' or have no id:" + movie.getId());
		return movie;
    }

	@PUT
	@Path("/movies")
	@Consumes("application/xml")
    @Produces("text/plain")
	public Response put(MovieWrapper movieWrapper) {
		MovieWrapper movie = validate(movieWrapper);
		Cache<String, Movie> c = getMovieCache();
		c.put(movie.getId(), movie.returnAsMovieInstance());
		logger.info("Added to the cache " + MOVIES_CACHE_NAME + ":" + movie);
		return Response.created(buildUriFrom(MOVIES_CACHE_NAME + "/" + movie.getId())).build();
	}

	@PUT
	@Path("/movies/bulk")
	@Consumes("application/xml")
    @Produces("text/plain")
	public Response puts(Movies movieWrappers) {
		logger.fine("Entering method with:" + movieWrappers);
		List<Movie> movies = validateMovies(movieWrappers);
		logger.fine("Nb Movies passed:" + movies.size());
		logger.fine("Adding them to cache " + MOVIES_CACHE_NAME);
		getMovieCache().putAll(MovieUtils.indexedMoviesByIds(movies));
		logger.info("Added to the cache " + MOVIES_CACHE_NAME + ":" + movies.size() + " entries.");
		logger.fine("Building response");
		return Response.created(buildUriFrom(MOVIES_CACHE_NAME + "/")).build();
	}

	private List<Movie> validateMovies(Movies moviesWrapper) {
		if ( moviesWrapper == null || moviesWrapper.getWrappers() == null || moviesWrapper.getWrappers().isEmpty() )
			throw new IllegalArgumentException("Empty or 'null' list of movies");

		List<Movie> movies = new ArrayList<>();
		for ( MovieWrapper wrapper: moviesWrapper.getWrappers() )
			movies.add(wrapper.returnAsMovieInstance());
		return movies;
	}

	private static URI buildUriFrom(String uriAsString) {
		try {
			return new URI(uriAsString);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static String validateKey(String key) {
		if (key == null || "".equals(key))
			throw new IllegalArgumentException("Invalid Key (null or empty)");
		return key;
	}

	@GET
	@Path("/movies/{key}")
	@Produces("application/xml")
	public JAXBElement<Movie> getOne(@PathParam("key") String key) {
		Movie movie = (Movie) getMovieCache().get(validateKey(key));
		if  ( movie == null )
			throw new IllegalArgumentException("No movie indexed with id:" + key);
		return transformToXml(movie);
	}

	@SuppressWarnings("unchecked")
	public  static <T> JAXBElement<T> transformToXml(T obj) {
	    return new JAXBElement<T>(new QName(obj.getClass().getSimpleName().toLowerCase()), (Class<T>)  obj.getClass(), obj);
	}

	@POST
	@Path("/queries/{query-id}/compute")
    @Produces("text/plain")
	public String computeQuery(@PathParam("query-id") String queryId) {

		System.out.println("Get handlers on caches");
    	Cache<String, SortedSet<Movie>> queries  = getQueriesCache();

    	System.out.println("Retrieve query with id:" + queryId);
    	Query query = getQueryImplMap().get(validateKey(queryId));
    	if ( query == null )
    		throw new IllegalArgumentException("No query identified by id:" + queryId);

    	System.out.println("Retrieve previous result - if any");
    	SortedSet<Movie> queryResult = queries.get(validateKey(queryId));
    	queries.put(queryId,ProcessQueryTemplate.computeQuery(queryResult, query, getMovieCache()));
    	return queryId;
	}

	@GET
	@Path("/queries/{query-id}")
	@Produces("application/xml")
	public JAXBElement<Movies> getQueryResult(@PathParam("query-id") String queryId) {
		if ( "".equals(queryId) )
			throw new IllegalArgumentException("QueryID can be 'null' nor empty");
		SortedSet<Movie> resultSet = getQueriesCache().get(queryId);
		if ( resultSet == null )
			throw new IllegalArgumentException("No result available for query '" + queryId + ".");

		return new JAXBElement<Movies>(new QName("movies"), Movies.class,
				CollectionsUtils.turnSortedSetIntoList(resultSet));
	}

	@DELETE
	@Path("/clean/movies")
    @Produces("text/plain")
	public void cleanMovies() {
		logger.fine("Clean movies requested.");
		getMovieCache().clear();
		logger.fine("Clean movies done.");
	}

	@DELETE
	@Path("/clean/queries")
    @Produces("text/plain")
	public void cleanQueries() {
		logger.fine("Clean queries requested.");
		getQueriesCache().clear();
		logger.fine("Clean queries done.");
	}
}
