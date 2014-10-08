package org.belaran.redhat.youblockbuster.queries;


public class TopPopularity extends AbstractQuery {

	private static final long serialVersionUID = 5268169966731972617L;

	@Override
	public int getNbResultsMax() {
		return 20;
	}
}
