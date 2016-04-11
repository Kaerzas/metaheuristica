package metaheuristics.localsearch;

import problems.ISolution;


public class BISNeighExplorator extends AbstractNeighExplorator 
{	
	@Override
	public ISolution generateBestNeighbour(ISolution individual) {
		operator.initialize(instance, individual);
		ISolution solution = individual;
		
		while(operator.hasNext()){
			ISolution neigh = operator.next();
			
			if(instance.betterThan(neigh, solution)){
				solution = neigh;
				//operator.initialize(instance, solution);
			}
		}
		
		if(solution != individual) //Better solution found
			return solution;
		else return null;
	}
}