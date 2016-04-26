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
		List<Integer> oldOrder = original.getOrder();
		List<Integer> newOrder = new ArrayList<Integer>(oldOrder);
		SolutionTSP newSol = new SolutionTSP(newOrder);
		
		newOrder.set(first, oldOrder.get(second));
		newOrder.set(second, oldOrder.get(first));

		TSPGraph graph = instance.graph;
		double newFitness = original.getFitness();
		
		int prevToFirst  = (first + newOrder.size() - 1) % newOrder.size();
		int nextToFirst  = (first + 1) % newOrder.size();
		int prevToSecond = (second - 1) % newOrder.size();
		int nextToSecond = (second + 1) % newOrder.size();
		
		newFitness = newFitness - graph.distance(oldOrder.get(first),oldOrder.get(nextToFirst))
								- graph.distance(oldOrder.get(prevToSecond),oldOrder.get(second));
		newFitness = newFitness + graph.distance(newOrder.get(first),newOrder.get(nextToFirst))
								+ graph.distance(newOrder.get(prevToSecond),newOrder.get(second));
		
		
		newFitness = newFitness - graph.distance(oldOrder.get(prevToFirst),oldOrder.get(first))
								- graph.distance(oldOrder.get(second),oldOrder.get(nextToSecond));
		newFitness = newFitness + graph.distance(newOrder.get(prevToFirst),newOrder.get(first))
								+ graph.distance(newOrder.get(second),newOrder.get(nextToSecond));
		
		newSol.setFitness(newFitness);

		return newSol;
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
		
		first = random.nextInt(this.instance.getLength());
		second = random.nextInt(this.instance.getLength()-1);
		
		
		if(second < first){
			int aux = first;
			first = second;
			second = aux;
		}
		else if(second >= first)
			second++;
		
		return generateNeighbour(first, second);
	}
}
