package org.belaran.redhat.youblockbuster;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.belaran.redhat.youblockbuster.ws.MovieWrapper;

@XmlRootElement(name = "movies")
public class Movies {

	private List<MovieWrapper> wrappers;

	
	public List<MovieWrapper> getWrappers() {
		return wrappers;
	}

    @XmlElement(name = "movie")
	public void setWrappers(List<MovieWrapper> wrappers) {
		this.wrappers = wrappers;
	}

	public int size() {
		return this.wrappers.size();
	}
}