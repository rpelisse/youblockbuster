package org.belaran.redhat.youblockbuster.queries;

import java.io.Serializable;
import java.util.Comparator;

import org.belaran.redhat.youblockbuster.Movie;


public abstract class AbstractQuery implements Query, Serializable {

	private static final long serialVersionUID = -7104660699376582642L;

	@Override
	public Comparator<Movie> getComparator() {
		return ComparatorFactory.comparatorFactory(this.getClass().getSimpleName());
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getName() + ", resultSize:" + this.getNbResultsMax() + ", comparator:" + 
				getComparator().getClass().getName() + "]";
	}
}
