package metaheuristics.localsearch.operator;

import problems.IInstance;
import problems.ISolution;
import problems.qmkp.InstanceQMKP;
import problems.qmkp.SolutionQMKP;

public class SwapObjQMKP extends INeighOperator 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** The instance */
	
	private InstanceQMKP instance;
	
	/** Solution to optimize */
	
	private SolutionQMKP original;
		
	/** First index for reading position */
	
	int firstNextIndex;
	
	/** Second index for reading position */
	
	int secondNextIndex;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	@Override
	public void initialize(IInstance instance, ISolution original)
	{
		// Load the instance and the individual
		this.instance = (InstanceQMKP) instance;
		this.original = (SolutionQMKP) original;	
		
		// Initialize the index
		this.firstNextIndex = 0;
		this.secondNextIndex = 1;
	}
	
	/**
	 * Generate a neighbor for a given individual
	 * 
	 * @param param Index of the element to invert
	 * 
	 * @return a neighbor for the individual
	 */
	
	private ISolution generateNeighbour(int fIdx, int sIdx) 
	{
		// Get the objects of the original solution
		int nObjects = this.original.getObjects().length;
		int [] newObjects = new int[nObjects];
		System.arraycopy(this.original.getObjects(), 0, newObjects, 0, nObjects);
		// Get the weight of the original solution
		int nKnapsacks = this.instance.getNKnapsacks();
		int [] weight = new int [nKnapsacks];
		System.arraycopy(this.original.getTotalWeight(), 0, weight, 0, nKnapsacks);	
		
		// Generate a new individual (the neighbor)
		SolutionQMKP newInd = new SolutionQMKP(newObjects);		
		
		
		// Flag used to see which object is out
		boolean firstIsOut;		
		if(newInd.getObjects()[fIdx] == -1)
			firstIsOut = true;
		else
			firstIsOut = false;
			
		// The bag where the item was stored
		int newBag, oldBag;
		// Position of the object added and removed
		int added, removed;
		// Control the position of the objects
		if(firstIsOut == false) {
			// Swap the object
			newBag = newInd.getObjects()[fIdx];
			oldBag = newInd.getObjects()[sIdx];
			newInd.getObjects()[fIdx] = -1;
			newInd.getObjects()[sIdx] = newBag;
			// Change the weights in the bag
			weight[newBag] = weight[newBag] + instance.getObjects().get(sIdx).getWeight();
			weight[newBag] = weight[newBag] - instance.getObjects().get(fIdx).getWeight();
			// Set added and removed
			added = sIdx;
			removed = fIdx;
		}
		else {
			// Swap the object
			newBag = newInd.getObjects()[sIdx];
			oldBag = newInd.getObjects()[fIdx];
			newInd.getObjects()[sIdx] = -1;
			newInd.getObjects()[fIdx] = newBag;
			// Change the weights in the bag
			weight[newBag] = weight[newBag] - instance.getObjects().get(sIdx).getWeight();
			weight[newBag] = weight[newBag] + instance.getObjects().get(fIdx).getWeight();
			// Set added and removed
			added = fIdx;
			removed = sIdx;
		}
		// Set the weights to the solution
		newInd.setTotalWeight(weight);
		
		// Flag used to see if the solution is invalid based on the weights
		boolean invalid = false;
		// See if any knapsack is overflow
		for(int i=0; i<weight.length;i++) {
			if(weight[i]>instance.getKnapsackSize())
				invalid = true;
		}
		
		// Reevaluate the solution
		// The solution is invalid
		if(invalid) {
			int exceded = 0;
			for(int i=0; i<weight.length; i++)
				if(weight[i] > instance.getKnapsackSize())
					exceded += (instance.getKnapsackSize() - weight[i]);
			newInd.setFitness(exceded);
		}
		// The solution is valid 
		else {
			// The previous was invalid
			if(original.getFitness()<0) {
				// It is needed to evaluate from the begining
				instance.evaluate(newInd);
			}
			// The previous was valid
			else {
				// Get the previous fitness
				double fitness = original.getFitness();			
				// Get the partners
				int [] partners = newInd.getObjectsInBag(newBag);
				
				// Change the individual fitness
				fitness += instance.getObjects().get(added).getValue();
				fitness -= instance.getObjects().get(removed).getValue();
				
				// Disassociate with the objects of the old bag
				for(int i=0; i<partners.length; i++) 
					fitness -= instance.getProfits()[removed][partners[i]];
				// Associate with the objects the new bag
				for(int i=0; i<partners.length; i++)
					fitness += instance.getProfits()[added][partners[i]];
				
				// Assign the fitness
				newInd.setFitness(fitness);			
			}
		}
		
		return newInd;
	}

	@Override
	public boolean hasNext() 
	{
		// while(firstNextIndex < (this.original.getObjects().length-1)){
		while(firstNextIndex != (this.original.getObjects().length-1)){	
			// Neighbor shouldn't be redundant
			if(original.getObjects()[firstNextIndex] != original.getObjects()[secondNextIndex])
				// One of the object has to be in out of the bags
				if((original.getObjects()[firstNextIndex] == -1)||(original.getObjects()[secondNextIndex] == -1))
					return true;
			
			//Update the second index
			secondNextIndex++;
			// The first index has been compared with all the second index
			if(secondNextIndex >= this.original.getObjects().length){
				firstNextIndex++;
				secondNextIndex = firstNextIndex + 1;
			}
		}
		return false;
	}

	@Override
	public ISolution next() 
	{
		// Generate the a neighbor
		ISolution sol = generateNeighbour(firstNextIndex, secondNextIndex);
		
		//Update the second index
		secondNextIndex++;
		// The first index has been compared with all the second index
		if(secondNextIndex >= this.original.getObjects().length){
			firstNextIndex++;
			secondNextIndex = firstNextIndex + 1;
		}
		return sol;
	}

	@Override
	public ISolution randomNeighbour() 
	{
		// TODO No forma parte the la busqueda local
		return null;
	}
}