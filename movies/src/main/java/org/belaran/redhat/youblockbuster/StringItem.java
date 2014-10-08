package org.belaran.redhat.youblockbuster;

import java.io.Serializable;


public abstract class StringItem implements Serializable {

	private static final long serialVersionUID = 8036231378844548742L;
	private String name;

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
