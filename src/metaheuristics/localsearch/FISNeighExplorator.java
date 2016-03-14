package metaheuristics.localsearch;

import metaheuristics.localsearch.operator.BitInversionKP;
import metaheuristics.localsearch.operator.BitSwapKP;
import problems.ISolution;
import problems.knapsack.SolutionKnapsack;
import problems.tsp.SolutionTSP;

public class FISNeighExplorator extends AbstractNeighExplorator 
{	
	@Override
	public ISolution generateBestNeighbour(ISolution individual) {
		
		//////////////////////////////////////////////
		// --------------------------------- Knapsack
		/////////////////////////////////////////////
		
		if(individual instanceof SolutionKnapsack) {
			SolutionKnapsack newInd;
			
			//////////////////////////////////////////////
			// ---------------------------- Bit Inversion
			/////////////////////////////////////////////
			
			if(operator instanceof BitInversionKP) {
			
				for(int i=0; i<((SolutionKnapsack)individual).getObjects().length; i++) {
					newInd = (SolutionKnapsack) operator.generateNeighbour(individual, i);
					instance.evaluate(newInd);
					if (instance.betterThan(newInd, individual)) 
						return newInd;
				}		
			}
			
			//////////////////////////////////////////////
			// --------------------------------- Bit Swap
			/////////////////////////////////////////////
			
			else if(operator instanceof BitSwapKP) {
				// TODO pendiente
			}
			else {
				System.out.println("Operator isn't valid");
				System.exit(0);
			}
			
		}
		
		//////////////////////////////////////////////
		// -------------------------------------- TSP
		/////////////////////////////////////////////
		
		else if (individual instanceof SolutionTSP) {
			// TODO pendiente
		}
		
		
		
		else {
			System.out.println("Individual isn't valid");
			System.exit(0);
		}
		// There is not better neighbour
		return null;
	}
}