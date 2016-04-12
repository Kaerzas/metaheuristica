package metaheuristics.localsearch.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problems.IInstance;
import problems.ISolution;
import problems.tsp.InstanceTSP;
import problems.tsp.SolutionTSP;
import problems.tsp.TSPGraph;

public class NodeSwapTSP extends INeighOperator{
	
	private InstanceTSP instance;
	private SolutionTSP original;
	private int firstNextIndex;
	private int secondNextIndex;
	
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
	 * @param param Parameter used to generate the neighbour (must be an integer arraylist with two elements)
	 * 
	 * @return a neighbour for the individual
	 */
	private SolutionTSP generateNeighbour(int first, int second){
		List<Integer> oldOrder = new ArrayList<Integer> (original.getOrder());
		SolutionTSP newInd = new SolutionTSP(oldOrder);
		
		boolean firstIsFirst = (first==0);
		boolean lastIsLast = (second==oldOrder.size()-1);
		
		newInd.getOrder().set(first, original.getOrder().get(second));
		newInd.getOrder().set(second, original.getOrder().get(first));

		TSPGraph graph = instance.graph;
		double newFitness = original.getFitness();
		List<Integer> newOrder = new ArrayList<Integer> (newInd.getOrder());
		
		newFitness = newFitness - graph.distance(oldOrder.get(first),oldOrder.get(first+1)) - graph.distance(oldOrder.get(second-1),oldOrder.get(second));
		newFitness = newFitness + graph.distance(newOrder.get(first),newOrder.get(first+1)) + graph.distance(newOrder.get(second-1),newOrder.get(second));
		
		if(firstIsFirst && !lastIsLast){
			newFitness = newFitness - graph.distance(oldOrder.get(oldOrder.size()-1),oldOrder.get(0)) + graph.distance(newOrder.get(newOrder.size()-1),newOrder.get(0));
			newFitness = newFitness - graph.distance(oldOrder.get(second),oldOrder.get(second+1)) + graph.distance(newOrder.get(second),newOrder.get(second+1));		
		}
		else if(!firstIsFirst && lastIsLast){
			newFitness = newFitness - graph.distance(oldOrder.get(oldOrder.size()-1),oldOrder.get(0)) + graph.distance(newOrder.get(newOrder.size()-1),newOrder.get(0));
			newFitness = newFitness - graph.distance(oldOrder.get(first),oldOrder.get(first-1)) + graph.distance(newOrder.get(first),newOrder.get(first-1));		
		}
		else if(!firstIsFirst && !lastIsLast){
			newFitness = newFitness - graph.distance(oldOrder.get(second),oldOrder.get(second+1)) + graph.distance(newOrder.get(second),newOrder.get(second+1));		
			newFitness = newFitness - graph.distance(oldOrder.get(first),oldOrder.get(first-1)) + graph.distance(newOrder.get(first),newOrder.get(first-1));		
		}
		
		newInd.setFitness(newFitness);

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
