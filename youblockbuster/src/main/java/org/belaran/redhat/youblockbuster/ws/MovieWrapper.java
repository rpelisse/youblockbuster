package org.belaran.redhat.youblockbuster.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.belaran.redhat.youblockbuster.Actor;
import org.belaran.redhat.youblockbuster.Director;
import org.belaran.redhat.youblockbuster.Genre;
import org.belaran.redhat.youblockbuster.Movie;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "movie")
public class MovieWrapper {

	private Movie movie = new Movie();

	public MovieWrapper() {
		movie.setDirectors(new ArrayList<Director>());
		movie.setActors(new ArrayList<Actor>());
		movie.setGenres(new ArrayList<Genre>());
	}

	public MovieWrapper(Movie movie) {
		if ( movie == null )
			throw new IllegalArgumentException("Can't build wrapper from 'null' movie instance.");
		if ( movie.getDirectors() == null )
			movie.setDirectors(new ArrayList<Director>());
		if ( movie.getActors() == null )
			movie.setActors(new ArrayList<Actor>());
		if ( movie.getGenres() == null )
			movie.setGenres(new ArrayList<Genre>());
		this.movie = movie;
	}

	public String getRating() {
		return movie.getRating();
	}

	public void setRating(String rating) {
		movie.setRating(rating);
	}

	public String getId() {
		return movie.getId();
	}

	public void setId(String id) {
		movie.setId(id);
	}

	public String getTitle() {
		return movie.getTitle();
	}

	public void setTitle(String title) {
		movie.setTitle(title);
	}

	public String getYear() {
		return movie.getYear();
	}

	public void setYear(String year) {
		movie.setYear(year);
	}

	public String getSynopsis() {
		return movie.getSynopsis();
	}

	public void setSynopsis(String synopsis) {
		movie.setSynopsis(synopsis);
	}

	@XmlElementWrapper(name="directors")
	@XmlElement(name="director")
	public List<Director> getDirectors() {
		return movie.getDirectors();
	}

	public void setDirectors(List<Director> directors) {
		movie.setDirectors(directors);
	}

	public int hashCode() {
		return movie.hashCode();
	}

	@XmlElementWrapper(name="genres")
	@XmlElement(name="genre")
	public List<Genre> getGenres() {
		return movie.getGenres();
	}

	public void setGenres(List<Genre> genres) {
		movie.setGenres(genres);
	}

	public String getPopularity() {
		return movie.getPopularity();
	}

	public void setPopularity(String popularity) {
		movie.setPopularity(popularity);
	}

	public String getVote_count() {
		return movie.getVote_count();
	}

	public void setVote_count(String vote_count) {
		movie.setVote_count(vote_count);
	}

	@XmlElementWrapper(name="actors")
	@XmlElement(name="actor")
	public List<Actor> getActors() {
		return movie.getActors();
	}

	public void setActors(List<Actor> actors) {
		movie.setActors(actors);
	}

	public String toString() {
		return movie.toString();
	}

	public boolean equals(Object obj) {
		return movie.equals(obj);
	}

	public Movie returnAsMovieInstance() {
		return movie;
	}

	public void setReadAvailable(boolean b) {

	}
}
