package metaheuristics.localsearch.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problems.IInstance;
import problems.ISolution;
import problems.qmkp.InstanceQMKP;
import problems.qmkp.SolutionQMKP;

public class ChangeBagQMKP extends INeighOperator 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** The instance */
	
	private InstanceQMKP instance;
	
	/** Solution to optimize */
	
	private SolutionQMKP original;
	
	/** The number of modifications */
	
	private int modified;
	
	/** Index used in modifications */
	
	private int idxModified;
	
	/** The size of the neighborhood */
	
	private int neighborhoodSize;
	
	/** Array that stores the index to the object inserted */
	
	private int [] objIndex;
	
	/** Object inserted in the initial solution */
	
	private int inserted;
	
	/** Step used changing the knapsack */
	
	private int step;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	@Override
	public void initialize(IInstance instance, ISolution original)
	{
		// Load the instance and the individual
		this.instance = (InstanceQMKP) instance;
		this.original = (SolutionQMKP) original;	
		
		// Get the objects
		int [] objects = this.original.getObjects();
		
		// The inserted objects need to be initialized only once
		if(inserted == 0) {
			//System.out.println("Inserted is equal to 0");
			// Create an auxiliary list with the index of the objects inserted
			List <Integer> aux = new ArrayList<>();
			for(int i=0; i<objects.length; i++)
				if(objects[i] != -1) {
					inserted++;
					aux.add(i);
				}
			
			// Convert the auxiliary list into an array
			objIndex = new int [aux.size()];
			for(int i =0; i<aux.size();i++)
				objIndex[i] = aux.get(i);
		}
		
		// Set the size of the neighborhood
		this.neighborhoodSize = inserted * (this.instance.getNKnapsacks()-1);
		// The number of solutions explorated
		this.modified = 0;
		// A index to read the actual object inserted that it's going to change
		this.idxModified = 0;
		// The step used to change an object from one bag to another
		this.step = 1;	
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
		
		// The bag where the item was stored
		int oldBag = newInd.getObjects()[objIndex[idx]];
		// The bag where the item is going to be stores
		int newBag = (oldBag + step)%instance.getNKnapsacks();

		// Change the bag
		newInd.getObjects()[objIndex[idx]] = newBag;

		// Change the weights in the bags
		weight[oldBag] = weight[oldBag] - instance.getObjects().get(objIndex[idx]).getWeight();
		weight[newBag] = weight[newBag] + instance.getObjects().get(objIndex[idx]).getWeight();
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
				// Get the previoud fitness
				double fitness = original.getFitness();				
				// Get the old and the new partners element from the object that has been moved
				int [] oldPartners = newInd.getObjectsInBag(oldBag);
				int [] newPartners = newInd.getObjectsInBag(newBag);
				
				// Disassociate with the objects of the old bag
				for(int i=0; i<oldPartners.length; i++) 
					fitness -= instance.getProfits()[objIndex[idx]][oldPartners[i]];
				// Associate with the objects the new bag
				for(int i=0; i<newPartners.length; i++)
					fitness += instance.getProfits()[objIndex[idx]][newPartners[i]];
				
				// Assign the fitness
				newInd.setFitness(fitness);
			}
		}
		
		return newInd;
	}

	@Override
	public boolean hasNext() 
	{
		return modified < neighborhoodSize;
	}

	@Override
	public ISolution next() 
	{
		// Generate a neighbour
		ISolution sol = generateNeighbour(idxModified);
		// Increment the number of modifications and the index
		modified++;
		idxModified = modified % inserted;
		// Check if we have read all the object inserted
		if(idxModified == 0) {
			// Increment the step
			step++;
			//System.out.println("Step:" + step);
		}
		return sol;
	}

	@Override
	public ISolution randomNeighbour() 
	{
		Random random = instance.getRandom();
		return generateNeighbour(random.nextInt(this.instance.getNObjects()));
	}
}