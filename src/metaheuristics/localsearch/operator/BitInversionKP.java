package metaheuristics.localsearch.operator;

import java.util.Random;

import problems.IInstance;
import problems.ISolution;
import problems.knapsack.InstanceKnapsack;
import problems.knapsack.SolutionKnapsack;

public class BitInversionKP extends INeighOperator 
{
	
	private InstanceKnapsack instance;
	private SolutionKnapsack original;
	private int nextModified;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	@Override
	public void initialize(IInstance instance, ISolution original){
		this.instance = (InstanceKnapsack) instance;
		this.original = (SolutionKnapsack) original;
		
		this.nextModified = 0;
	}
	
	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param param Index of the element to invert
	 * 
	 * @return a neighbour for the individual
	 */
	private ISolution generateNeighbour(int idx) 
	{
		int nObjects = this.original.getObjects().length;
		byte [] newObjects = new byte[nObjects];
		System.arraycopy(this.original.getObjects(), 0, newObjects, 0, nObjects);
		
		SolutionKnapsack newInd = new SolutionKnapsack(newObjects);
		
		int newWeight = original.getTotalWeight();
		
		if(newInd.getObjects()[idx] == 0){ //Adding an element
			newInd.getObjects()[idx] = 1;
			//newFitness += instance.getObjects().get(idx).getValue();
			newWeight += instance.getObjects().get(idx).getWeight();
		}
		else{ //Subtracting an element
			newInd.getObjects()[idx] = 0;
			//newFitness -= instance.getObjects().get(idx).getValue();
			newWeight  -= instance.getObjects().get(idx).getWeight();
		}
		
		newInd.setTotalWeight(newWeight);
		
		// Evaluate new solution from original solution
		if(newWeight > instance.getKnapsackSize()) //New solution is invalid
			newInd.setFitness(instance.getKnapsackSize() - newWeight);
		else{ // New solution is valid
			double newFitness = original.getFitness();
			
			if(original.getFitness() < 0.0){	// Previous solution was invalid
				//System.out.println("hola");
				instance.evaluate(newInd);
			}
			
			else {
				if(newInd.getObjects()[idx] == 1){ //An element was added
					newFitness += instance.getObjects().get(idx).getValue();
				}
				else{ //An element was subtracted
					newFitness -= instance.getObjects().get(idx).getValue();
				}
				newInd.setFitness(newFitness);
			}
			
		}
		
		return newInd;
	}

	@Override
	public boolean hasNext() {
		return this.nextModified < this.original.getObjects().length;
	}

	@Override
	public ISolution next() {
		ISolution sol = generateNeighbour(nextModified);
		//System.out.println(nextModified + ": " + sol.getFitness());
		nextModified++;
		return sol;
	}

	@Override
	public ISolution randomNeighbour() {
		Random random = instance.getRandom();
		return generateNeighbour(random.nextInt(this.instance.getLength()));
	}
}