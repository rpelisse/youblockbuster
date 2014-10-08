package org.belaran.redhat.youblockbuster.queries;

public class TopVoted extends AbstractQuery {

	private static final long serialVersionUID = 5611421247317443897L;

	@Override
	public int getNbResultsMax() {
		return 100;
	}
}
