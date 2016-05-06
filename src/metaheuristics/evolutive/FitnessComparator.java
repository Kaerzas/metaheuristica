package metaheuristics.evolutive;

import java.util.Comparator;

import problems.IInstance;
import problems.ISolution;
import problems.knapsack.InstanceKnapsack;
import problems.knapsack.SolutionKnapsack;
import problems.tsp.InstanceTSP;
import problems.tsp.SolutionTSP;

public class FitnessComparator implements Comparator<ISolution>{

	private IInstance instance;
	
	public FitnessComparator(IInstance instance) {
		this.instance = instance;
	}
	
    @Override
    public int compare(ISolution o1, ISolution o2) {
    	if(instance.maximize())
    		return Double.compare(o1.getFitness(), o2.getFitness());
    	else
    		return Double.compare(o2.getFitness(), o1.getFitness());
    }
    
    public static void main(String[] args){
    	SolutionKnapsack k1 = new SolutionKnapsack();
    	SolutionKnapsack k2 = new SolutionKnapsack();
    	InstanceKnapsack ik = new InstanceKnapsack();
    	
    	k1.setFitness(30.0);
    	k2.setFitness(40.0);
    	
    	int comp = new FitnessComparator(ik).compare(k1, k2);
    	
    	if(comp < 0){
    		System.out.println("Doing good");
    	}
    	else{
    		System.out.println("Something went horribly wrong");
    	}
    	
    	SolutionTSP t1 = new SolutionTSP();
    	SolutionTSP t2 = new SolutionTSP();
    	InstanceTSP it = new InstanceTSP();
    	
    	t1.setFitness(1000.0);
    	t2.setFitness(2000.0);
    	
    	comp = new FitnessComparator(it).compare(k1, k2);
    	
    	if(comp > 0){
    		System.out.println("Doing good");
    	}
    	else{
    		System.out.println("Something went horribly wrong");
    	}
    }
}