package problems.tsp;

import java.util.List;

import problems.AbstractSolution;

public class SolutionTSP extends AbstractSolution 
{
	
	public SolutionTSP(){
		
	}
	
	//private int [] order;
	private List<Integer> order;
	
	public SolutionTSP(List<Integer> order){
		this.order = order;
	}
	
	@Override
	public void printSolution() 
	{
		System.out.println(order);
		System.out.println("Fitness:" + getFitness());
	}
	
	public List<Integer> getOrder(){
		return order;
	}
}
