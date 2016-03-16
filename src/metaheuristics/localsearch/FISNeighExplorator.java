package metaheuristics.localsearch;

import metaheuristics.localsearch.operator.*;
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
				//Go over all combinations
				for(int i=0; i<((SolutionKnapsack)individual).getObjects().length; i++) {
					for(int j=i+1; j<((SolutionKnapsack)individual).getObjects().length; j++) {
						int pos[] = {i, j};
						newInd = (SolutionKnapsack) operator.generateNeighbour(individual, pos);
						instance.evaluate(newInd);
						if(instance.betterThan(newInd, individual)){
							return newInd;
						}
					}
				}
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
			SolutionTSP individualTSP = (SolutionTSP) individual;
			SolutionTSP newInd;
			
			//////////////////////////////////////////////
			// ---------------------------- Node Inversion
			/////////////////////////////////////////////
			if(operator instanceof NodeInversionTSP){
				for(int i=0 ; i < individualTSP.getOrder().size() ; ++i){
					for(int j=i+1 ; j < individualTSP.getOrder().size() ; ++j){
						int pos[] = {i, j};
						newInd = (SolutionTSP) operator.generateNeighbour(individual, pos);
						instance.evaluate(newInd);
						
						if(instance.betterThan(newInd, individualTSP))
							return newInd;
					}
				}
			}
			
			//////////////////////////////////////////////
			// ---------------------------- Node Swap
			/////////////////////////////////////////////
			else if(operator instanceof NodeSwapTSP){
				for(int i=0 ; i < individualTSP.getOrder().size() ; ++i){
					for(int j=i+1 ; j < individualTSP.getOrder().size() ; ++j){
						int pos[] = {i, j};
						newInd = (SolutionTSP) operator.generateNeighbour(individual, pos);
						instance.evaluate(newInd);
						
						if(instance.betterThan(newInd, individualTSP))
							return newInd;
					}
				}
			}
		}
		
		
		
		else {
			System.out.println("Individual isn't valid");
			System.exit(0);
		}
		// There is not better neighbour
		return null;
	}
}