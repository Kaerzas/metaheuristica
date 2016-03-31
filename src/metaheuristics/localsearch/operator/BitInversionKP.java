package metaheuristics.localsearch.operator;

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
		
		double newFitness = original.getFitness();
		int newWeight = original.getTotalWeight();
		
		if(newInd.getObjects()[idx] == 0){
			newInd.getObjects()[idx] = 1;
			newFitness += instance.getObjects().get(idx).getValue();
			newWeight += instance.getObjects().get(idx).getWeight();
		}
		else{
			newInd.getObjects()[idx] = 0;
			newFitness -= instance.getObjects().get(idx).getValue();
			newWeight  -= instance.getObjects().get(idx).getWeight();
		}
		
		//Still has to check if solution is valid
		if(original.getTotalWeight() <= instance.getKnapsackSize()){ //Original solution was valid
			if(newWeight > instance.getKnapsackSize()) //but new solution is invalid
				newFitness -= instance.getTotalValue();
		}
		else{ //Original solution was invalid
			if(newWeight <= instance.getKnapsackSize()) //but new solution is valid
				newFitness += instance.getTotalValue();
		}
		
		newInd.setFitness(newFitness);
		newInd.setTotalWeight(newWeight);
		
		return newInd;
	}

	@Override
	public boolean hasNext() {
		return this.nextModified < this.original.getObjects().length;
	}

	@Override
	public ISolution next() {
		ISolution sol = generateNeighbour(nextModified);
		nextModified++;
		return sol;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}