package metaheuristics.localsearch;

import problems.ISolution;

public class RandomNeighExplorator extends AbstractNeighExplorator {

	@Override
	public ISolution generateBestNeighbour(ISolution individual) {
		operator.initialize(instance, individual);
		return operator.randomNeighbour();
	}

}
