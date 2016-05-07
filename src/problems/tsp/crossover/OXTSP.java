package problems.tsp.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.configuration.Configuration;

import metaheuristics.evolutive.AbstractCrossover;
import problems.IInstance;
import problems.ISolution;
import problems.tsp.InstanceTSP;
import problems.tsp.SolutionTSP;

public class OXTSP extends AbstractCrossover {

	InstanceTSP instance;
	
	@Override
	public ISolution cross(ISolution parentA, ISolution parentB) {

		Random random = instance.getRandom();
		
		SolutionTSP tspA = ((SolutionTSP)parentA);
		SolutionTSP tspB = ((SolutionTSP)parentB);
		
		int start = random.nextInt(tspA.getOrder().size()/2);
		int end = (start + (tspA.getOrder().size())/2);
		
		List<Integer> order = new ArrayList<Integer>(tspA.getOrder());
		List<Integer> aux = new ArrayList<Integer>(tspB.getOrder().size());
		
		int idx;
		
		for(int i = 0; i < order.size(); i++){
			 idx = (end + 1 + i) % order.size();
			 aux.add(tspB.getOrder().get(idx));
		}
		
		idx = (end + 1) % aux.size();

		for(int i = 0; i < aux.size(); i++){
			boolean flag = false;
			for(int j = start; j <= end; j++ ){
				if(order.get(j).intValue() == aux.get(i).intValue())
					flag = true;
			}
			if(!flag){
				order.set(idx, aux.get(i));
				idx = (idx+1) % aux.size();
			}
		}
		
		SolutionTSP child = new SolutionTSP(order);
		instance.evaluate(child);
		
		return child;
		
	}

	@Override
	public void configure(Configuration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize(IInstance instance) {
		this.instance = (InstanceTSP) instance;
		
	}

	public static void main(String[] args){
		
		Random r = new Random();
		InstanceTSP it = new InstanceTSP();
		it.setRandom(r);
		
		OXTSP htsp = new OXTSP();
		htsp.initialize(it);
		
		List<Integer> a = new ArrayList<Integer>();
		List<Integer> b = new ArrayList<Integer>();
		
		a.add(0);
		a.add(3);
		a.add(2);
		a.add(6);
		a.add(4);
		a.add(5);
		a.add(1);
		
		b.add(0);
		b.add(4);
		b.add(1);
		b.add(5);
		b.add(2);
		b.add(6);
		b.add(3);

		
		SolutionTSP s1 = new SolutionTSP(a);
		SolutionTSP s2 = new SolutionTSP(b);
		
		s1.printSolution();
		s2.printSolution();
		SolutionTSP ns = (SolutionTSP) htsp.cross(s1, s2);
		ns.printSolution();
	}
	
}
