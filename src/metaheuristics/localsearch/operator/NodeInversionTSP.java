package metaheuristics.localsearch.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problems.IInstance;
import problems.ISolution;
import problems.tsp.InstanceTSP;
import problems.tsp.SolutionTSP;

public class NodeInversionTSP extends INeighOperator 
{
	private InstanceTSP instance;
	private SolutionTSP original;
	private int firstNextIndex;
	private int secondNextIndex;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	@Override
	public void initialize(IInstance instance, ISolution original){
		this.instance = (InstanceTSP) instance;
		this.original = (SolutionTSP) original;
		
		this.firstNextIndex = 0;
		this.secondNextIndex = 1;
	}
	
	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param individual The individual
	 * @param param Parameter used to generate the neighbour (must be an ArrayList with two elements)
	 * 
	 * @return a neighbour for the individual
	 */
	
	private SolutionTSP generateNeighbour(int first, int second) 
	{

		List<Integer> newObjects = new ArrayList<Integer>(original.getOrder());
		
		SolutionTSP newInd = new SolutionTSP(newObjects);
		
		int aux;
	    
	    for (int i = first; i <= ((second-first) / 2)+first; i++) {
	        aux = newObjects.get(i);
	        newObjects.set(i,newObjects.get(second-(i-first)));
	        newObjects.set(second-(i-first), aux);
	    }
	    
	    instance.evaluate(newInd);
		
		return newInd;
	}

	@Override
	public boolean hasNext() {
		return firstNextIndex < (this.original.getOrder().size()-1);
	}

	@Override
	public ISolution next() {
		ISolution sol = generateNeighbour(firstNextIndex, secondNextIndex);
		
		//Update index
		secondNextIndex++;
		if(secondNextIndex >= this.original.getOrder().size()){
			firstNextIndex++;
			secondNextIndex = firstNextIndex + 1;
		}
		
		return sol;
	}
	
	@Override
	public ISolution randomNeighbour() {
		Random random = instance.getRandom();
		int first, second;
		
		first = random.nextInt(this.instance.getNNodes());
		second = random.nextInt(this.instance.getNNodes()-1);
		
		if(second >= first)
			second++;
		
		return generateNeighbour(first, second);
	}
}
