package metaheuristics.localsearch;

import metaheuristics.localsearch.operator.BitInversionKP;
import metaheuristics.localsearch.operator.BitSwapKP;
import metaheuristics.localsearch.operator.NodeInversionTSP;
import metaheuristics.localsearch.operator.NodeSwapTSP;
import problems.ISolution;
import problems.knapsack.InstanceKnapsack;
import problems.knapsack.SolutionKnapsack;
import problems.tsp.SolutionTSP;

public class BISNeighExplorator extends AbstractNeighExplorator 
{	
	@Override
	public ISolution generateBestNeighbour(ISolution individual) {
		
		//////////////////////////////////////////////
		// --------------------------------- Knapsack
		/////////////////////////////////////////////
		
		if(individual instanceof SolutionKnapsack) {
			
			SolutionKnapsack betterInd = (SolutionKnapsack) individual;
			SolutionKnapsack newInd = null;
			
			//////////////////////////////////////////////
			// ---------------------------- Bit Inversion
			/////////////////////////////////////////////
			
			if(operator instanceof BitInversionKP) {
				for(int i=0; i<((SolutionKnapsack)individual).getObjects().length; i++) {
					newInd = (SolutionKnapsack) operator.generateNeighbour(individual, i);
					
					if(((SolutionKnapsack) individual).getObjects()[i] == 1){
						
						double newWeight = ((SolutionKnapsack)individual).getTotalWeight() - ((InstanceKnapsack)instance).getObjects().get(i).getWeight();
						double newFitness = individual.getFitness()-((InstanceKnapsack)instance).getObjects().get(i).getValue();
						
						if(individual.getFitness() < 0){
														
							if(newWeight <= ((InstanceKnapsack)instance).getKnapsackSize())
								newInd.setFitness(newFitness+((InstanceKnapsack)instance).getTotalValue());
							else
								newInd.setFitness(newFitness);
						}
						else {
							newInd.setFitness(newFitness);
						}			
						
						newInd.setTotalWeight(newWeight);
					}
					
					else{
						
						double newFitness = individual.getFitness()+((InstanceKnapsack)instance).getObjects().get(i).getValue();
						double newWeight = ((InstanceKnapsack)instance).getObjects().get(i).getWeight() + ((SolutionKnapsack)individual).getTotalWeight();
						
						newInd.setTotalWeight(newWeight);
						
						if(newWeight > ((InstanceKnapsack)instance).getKnapsackSize()){
							
							if(((SolutionKnapsack)individual).getTotalWeight() > ((InstanceKnapsack)instance).getKnapsackSize())
								newInd.setFitness(newFitness);
							else
								newInd.setFitness(newFitness - ((InstanceKnapsack)instance).getTotalValue());
						
						}
						else {
							
							if(((SolutionKnapsack)individual).getTotalWeight() > ((InstanceKnapsack)instance).getKnapsackSize())
								newInd.setFitness(newFitness + ((InstanceKnapsack)instance).getTotalValue());
							else
								newInd.setFitness(newFitness);
						}
					}
					
					//instance.evaluate(newInd);
					if (instance.betterThan(newInd, betterInd)) 
						betterInd = newInd;
				}
				// If (betterInd == individual) then all the neighbour are worse
				if(betterInd != individual)
					return betterInd;
			}
			
			//////////////////////////////////////////////
			// --------------------------------- Bit Swap
			/////////////////////////////////////////////
			
			else if(operator instanceof BitSwapKP) {
				//Go over all combinations
				for(int i=0; i<((SolutionKnapsack)individual).getObjects().length; i++) {
					for(int j=i+1; j<((SolutionKnapsack)individual).getObjects().length; j++) {
						if(((SolutionKnapsack)individual).getObjects()[i] != ((SolutionKnapsack)individual).getObjects()[j]){
							int pos[] = {i, j};
							newInd = (SolutionKnapsack) operator.generateNeighbour(individual, pos);
							
							double newWeight = 0, newFitness = 0;
							
							if(((SolutionKnapsack) individual).getObjects()[i] == 1 ){
								newWeight = ((SolutionKnapsack)individual).getTotalWeight() - ((InstanceKnapsack)instance).getObjects().get(i).getWeight() + ((InstanceKnapsack)instance).getObjects().get(j).getWeight();
								newFitness = individual.getFitness() - ((InstanceKnapsack)instance).getObjects().get(i).getValue() + ((InstanceKnapsack)instance).getObjects().get(j).getValue();
							}
							
							else if(((SolutionKnapsack) individual).getObjects()[i] == 0 ){
								newWeight = ((SolutionKnapsack)individual).getTotalWeight() + ((InstanceKnapsack)instance).getObjects().get(i).getWeight() - ((InstanceKnapsack)instance).getObjects().get(j).getWeight();
								newFitness = individual.getFitness() + ((InstanceKnapsack)instance).getObjects().get(i).getValue() - ((InstanceKnapsack)instance).getObjects().get(j).getValue();
							}
															
							newInd.setTotalWeight(newWeight);
							
							if(newWeight > ((InstanceKnapsack)instance).getKnapsackSize()){
								
								if(((SolutionKnapsack)individual).getTotalWeight() > ((InstanceKnapsack)instance).getKnapsackSize())
									newInd.setFitness(newFitness);
								else
									newInd.setFitness(newFitness - ((InstanceKnapsack)instance).getTotalValue());
							
							}
							else {
								
								if(((SolutionKnapsack)individual).getTotalWeight() > ((InstanceKnapsack)instance).getKnapsackSize())
									newInd.setFitness(newFitness + ((InstanceKnapsack)instance).getTotalValue());
								else
									newInd.setFitness(newFitness);
							}
							
							if(instance.betterThan(newInd, betterInd)){
								betterInd = newInd;
							}
						}
					}
				}
				
				if(betterInd != individual)
					return betterInd;
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
			SolutionTSP betterInd = individualTSP;
			SolutionTSP newInd = null;
			
			//////////////////////////////////////////////
			// ---------------------------- Node Inversion
			/////////////////////////////////////////////
			if(operator instanceof NodeInversionTSP){
				for(int i=0 ; i < individualTSP.getOrder().size() ; ++i){
					for(int j=i+1 ; j < individualTSP.getOrder().size() ; ++j){
						int pos[] = {i, j};
						newInd = (SolutionTSP) operator.generateNeighbour(individual, pos);
						instance.evaluate(newInd);
						
						if(instance.betterThan(newInd, betterInd))
							betterInd = newInd;
					}
				}
				if(betterInd != individualTSP)
					return betterInd;
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
						
						if(instance.betterThan(newInd, betterInd))
							betterInd = newInd;
					}
				}
				if(betterInd != individualTSP)
					return betterInd;
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