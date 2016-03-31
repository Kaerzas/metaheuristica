package metaheuristics.localsearch;

import problems.ISolution;

public class FISNeighExplorator extends AbstractNeighExplorator 
{	
	@Override
	public ISolution generateBestNeighbour(ISolution individual) {
		operator.initialize(instance, individual);
		
		while(operator.hasNext()){
			ISolution neigh = operator.next();
			
			if(instance.betterThan(neigh, individual)){
				return neigh;
			}
		}
		
		return null;
	}
}