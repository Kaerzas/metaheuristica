package problems.tsp;

import java.util.List;

import problems.AbstractSolution;

public class SolutionTSP extends AbstractSolution 
{
	
	//private int [] order;
	private List<Integer> order;
	
	public SolutionTSP(List<Integer> order){
		this.order = order;
	}
	
	@Override
	public void printSolution() 
	{
		System.out.print("[");
		for (Integer i : order) {
			System.out.print(i+",");
		}
		System.out.println("]");
		System.out.println("Fitness:" + getFitness());
	}
}
