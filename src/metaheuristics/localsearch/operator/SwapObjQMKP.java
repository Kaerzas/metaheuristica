package metaheuristics.localsearch.operator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import problems.IInstance;
import problems.ISolution;
import problems.qmkp.InstanceQMKP;
import problems.qmkp.SolutionQMKP;

/**
 * 
 *
 */
public class SwapObjQMKP extends INeighOperator 
{
	class Neighbour{
		public int first;
		public int second; 
		
		public Neighbour(int first, int second){
			this.first = first;
			this.second = second;
		}
	}
	
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** The instance */
	
	private InstanceQMKP instance;
	
	/** Solution to optimize */
	
	private SolutionQMKP original;
	
	private Stack<Neighbour> neighbourhood;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	@Override
	public void initialize(IInstance instance, ISolution original)
	{
		// Load the instance and the individual
		this.instance = (InstanceQMKP) instance;
		this.original = (SolutionQMKP) original;	
		neighbourhood = null;
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
		
		
		// Which object was added, which was removed
		int added, removed;
		
		//Which bag is modified
		int bag;
		
		if(original.getObjects()[fIdx] == -1){
			added = fIdx;
			removed = sIdx;
			bag = newInd.getObjects()[sIdx];
		}
		else{
			added = sIdx;
			removed = fIdx;
			bag = newInd.getObjects()[fIdx];
		}
		
		//Swap objectsnewInd
		int aux = newObjects[fIdx];
		newObjects[fIdx] = newObjects[sIdx];
		newObjects[sIdx] = aux;
		
		// Update weights
		weight[bag] -= instance.getObjects().get(removed).getWeight();
		weight[bag] += instance.getObjects().get(added).getWeight();
		
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
				// It is needed to evaluate from the beginning
				instance.evaluate(newInd);
			}
			// The previous was valid
			else {
				// Get the previous fitness
				double fitness = original.getFitness();			
				// Get the partners
				List<Integer> partners = newInd.getObjectsInBag(bag);
				partners.remove(new Integer(added)); //Because it is already added
				
				// Change the individual fitness
				fitness += instance.getObjects().get(added).getValue();
				fitness -= instance.getObjects().get(removed).getValue();
				
				// Disassociate with the objects of the old bag
				for(Integer i : partners){
					fitness -= instance.getProfits()[removed][i];
					fitness += instance.getProfits()[added][i];
				}
				
				// Assign the fitness
				newInd.setFitness(fitness);			
			}
		}
		
		return newInd;
	}

	private void resetNeighbourhood(){
		int [] objects = this.original.getObjects();
		
		List<Integer> inserted = new ArrayList<>();
		List<Integer> notInserted = new ArrayList<>();
		
		int bag;
		neighbourhood = new Stack<>();
		
		for(int i=0 ; i < objects.length ; ++i){
			bag = objects[i];
			if(bag < 0)
				notInserted.add(i);
			else
				inserted.add(i);
		}
		
		for(Integer n : notInserted){
			for(Integer i : inserted){
				neighbourhood.push(new Neighbour(n, i));
			}
		}
		Collections.shuffle(neighbourhood, instance.getRandom());
	}
	
	@Override
	public boolean hasNext() 
	{
		if(neighbourhood == null)
			resetNeighbourhood();
		
		return neighbourhood.size() > 0;
	}

	@Override
	public ISolution next() 
	{
		Neighbour n = neighbourhood.pop();
		return generateNeighbour(n.first, n.second);
	}

	@Override
	public ISolution randomNeighbour() 
	{
		List<Integer> inserted = new ArrayList<>(instance.getNObjects());
		List<Integer> notInserted = new ArrayList<>(instance.getNObjects());
		int[] objects = original.getObjects();
		
		// List of inserted elements
		for(int i=0 ; i < objects.length ;  ++i){
			if(objects[i] >= 0)
				inserted.add(i);
			else
				notInserted.add(i);
		}
		
		if(inserted.size() == 0 || notInserted.size() == 0)
			throw new RuntimeException("SwapObjMKP can only generate neighbours for solutions with at least one object out and another inserted.");
		
		int first  =    inserted.get(instance.getRandom().nextInt(   inserted.size()));
		int second = notInserted.get(instance.getRandom().nextInt(notInserted.size()));
		
		return generateNeighbour(first, second);
	}
	
	public static void main(String[] args){
		//Create instance
		InstanceQMKP instance = new InstanceQMKP();
		Configuration c = new BaseConfiguration();
		c.setProperty("data", "examples/QMKPInstances/jeu_100_100_1.txt");
		instance.configure(c);
		instance.setRandom(new Random());
		
		//Create solution
		int[] objects = new int[instance.getNObjects()];
		for(int i=0 ; i < objects.length ; ++i){
			objects[i] = -1;
		}
		objects[0] = 0;
		objects[1] = 1;
		objects[2] = 2;
		ISolution solution = new SolutionQMKP(objects);
		instance.evaluate(solution);
		
		//Create operator
		SwapObjQMKP operator = new SwapObjQMKP();
		operator.initialize(instance, solution);
		
		System.out.println("Original solution:");
		solution.printSolution();
		
		System.out.println("Neighbours:");
		while(operator.hasNext()){
			ISolution neigh = operator.next();
			neigh.printSolution();
			
			double quickFitness = neigh.getFitness();
			instance.evaluate(neigh);
			double correctFitness = neigh.getFitness();
			
			if(quickFitness != correctFitness){
				System.err.println("Fitness incorrectly computed");
				System.err.println("Computed: " + quickFitness + ", Correct: " + correctFitness);
			}
		}
		
		System.out.println("Random neighbours:");
		for(int i=0 ; i < 10 ; ++i){
			ISolution neigh = operator.randomNeighbour();
			neigh.printSolution();
			
			double quickFitness = neigh.getFitness();
			instance.evaluate(neigh);
			double correctFitness = neigh.getFitness();
			
			if(quickFitness != correctFitness){
				System.err.println("Fitness incorrectly computed");
				System.err.println("Computed: " + quickFitness + ", Correct: " + correctFitness);
			}
		}
	}
}