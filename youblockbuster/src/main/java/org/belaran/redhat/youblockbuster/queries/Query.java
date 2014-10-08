package org.belaran.redhat.youblockbuster.queries;

import java.io.Serializable;
import java.util.Comparator;

import org.belaran.redhat.youblockbuster.Movie;


public interface Query extends Serializable {

	public int getNbResultsMax();
	
	public Comparator<Movie> getComparator();
}
