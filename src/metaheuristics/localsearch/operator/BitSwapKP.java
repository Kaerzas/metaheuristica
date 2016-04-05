package metaheuristics.localsearch.operator;

import java.util.Random;

import problems.IInstance;
import problems.ISolution;
import problems.knapsack.InstanceKnapsack;
import problems.knapsack.SolutionKnapsack;

public class BitSwapKP extends INeighOperator 
{

	private InstanceKnapsack instance;
	private SolutionKnapsack original;
	private int firstNextIndex;
	private int secondNextIndex;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public void initialize(IInstance instance, ISolution original){
		this.instance = (InstanceKnapsack) instance;
		this.original = (SolutionKnapsack) original;
		
		this.firstNextIndex = 0;
		this.secondNextIndex = 1;
	}
	
	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param individual The individual
	 * @param param Parameter used to generate the neighbour (must be an integer arraylist with two elements)
	 * 
	 * @return a neighbour for the individual
	 */
	
	private SolutionKnapsack generateNeighbour(int first, int last) 
	{
			
		int nObjects = this.original.getObjects().length;
		byte [] newObjects = new byte[nObjects];
		System.arraycopy(this.original.getObjects(), 0, newObjects, 0, nObjects);
		byte aux;
		
		SolutionKnapsack newInd = new SolutionKnapsack(newObjects);
						
		aux = newInd.getObjects()[first];
		newInd.getObjects()[first] = newInd.getObjects()[last];
		newInd.getObjects()[last] = aux;
		
		//Compute fitness from original solution
		double newFitness = original.getFitness();
		int newWeight = original.getTotalWeight();
		
		//Assumes the swapped elements are different, since that's already checked in hasNext()
		if(newInd.getObjects()[first] == 0){
			newFitness -= instance.getObjects().get(first).getValue();
			newFitness += instance.getObjects().get(last).getValue();
			
			newWeight -= instance.getObjects().get(first).getWeight();
			newWeight += instance.getObjects().get(last).getWeight();
		}else{
			newFitness += instance.getObjects().get(first).getValue();
			newFitness -= instance.getObjects().get(last).getValue();
			
			newWeight += instance.getObjects().get(first).getWeight();
			newWeight -= instance.getObjects().get(last).getWeight();
		}
		
		//Still has to check if solution is valid
		if(original.getTotalWeight() <= instance.getKnapsackSize()){ //Original solution was valid
			if(newWeight > instance.getKnapsackSize()) //but new solution is invalid
				newFitness -= instance.getTotalValue();
		}
		else{ //Original solution was invalid
			if(newWeight <= instance.getKnapsackSize()) //but new solution is invalid
				newFitness += instance.getTotalValue();
		}
		
		newInd.setFitness(newFitness);
		newInd.setTotalWeight(newWeight);
		
		return newInd;
	}

	@Override
	public boolean hasNext() {
		while(firstNextIndex < (this.original.getObjects().length-1)){
			
			//If next neighbour is not redundant, hasNext is true
			if(original.getObjects()[firstNextIndex] != original.getObjects()[secondNextIndex])
				return true;
			
			//Update index
			secondNextIndex++;
			if(secondNextIndex >= this.original.getObjects().length){
				firstNextIndex++;
				secondNextIndex = firstNextIndex + 1;
			}
		}
		
		return false;
	}

	@Override
	public ISolution next() {
		ISolution sol = generateNeighbour(firstNextIndex, secondNextIndex);
		
		//Update index
		secondNextIndex++;
		if(secondNextIndex >= this.original.getObjects().length){
			firstNextIndex++;
			secondNextIndex = firstNextIndex + 1;
		}
		
		return sol;
	}
	
	@Override
	public ISolution randomNeighbour(Random random) {
		int first, second;
		
		first = random.nextInt(this.instance.getNObjects());
		second = random.nextInt(this.instance.getNObjects()-1);
		
		if(second >= first)
			second++;
		
		return generateNeighbour(first, second);
	}
}