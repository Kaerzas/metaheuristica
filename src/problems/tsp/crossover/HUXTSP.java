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

public class HUXTSP extends AbstractCrossover {

	InstanceTSP instance;
	
	@Override
	public ISolution cross(ISolution parentA, ISolution parentB) {

		Random random = instance.getRandom();
		
		SolutionTSP tspA = ((SolutionTSP)parentA);
		SolutionTSP tspB = ((SolutionTSP)parentB);
		
		int start = random.nextInt(tspA.getOrder().size());
		int end = (start + (tspA.getOrder().size())/2) % tspA.getOrder().size();
				
		List<Integer> order = new ArrayList<Integer>(tspA.getOrder());
		List<Integer> subvector = createSubvector(tspA, tspB, start, end);
		
		int sub = 0;
		for(int i = 0; i < order.size(); ++i){
			
			if(start < end && (i < start || i > end)){
				order.set(i,subvector.get(sub));
				sub++;
			}

			else if(i < start && i > end){
				order.set(i,subvector.get(sub));
				sub++;
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
	
	public List<Integer> createSubvector (SolutionTSP tspA, SolutionTSP tspB, int start, int end){
		
		List<Integer> subvector = new ArrayList<Integer>();
		
		if (start < end){
			
			for(int i = 0; i < tspB.getOrder().size(); ++i){
				
				boolean aux = false;
				
				for(int j = start; j <= end; ++j){
					if(tspB.getOrder().get(i) == tspA.getOrder().get(j))
						aux = true;
				}
				
				if(!aux)
					subvector.add(tspB.getOrder().get(i));
			}
		}
		else{
			
			for(int i = 0; i < tspB.getOrder().size(); ++i){
				
				boolean aux = false;
				
				for(int j = start; j < tspA.getOrder().size(); ++j){
					if(tspB.getOrder().get(i) == tspA.getOrder().get(j))
						aux = true;
				}
				
				for(int j = 0; j <= end; ++j){
					if(tspB.getOrder().get(i) == tspA.getOrder().get(j))
						aux = true;
				}
				
				if(!aux)
					subvector.add(tspB.getOrder().get(i));
			}
		}
		
		return subvector;
	}

	public static void main(String[] args){
		
		Random r = new Random();
		InstanceTSP it = new InstanceTSP();
		it.setRandom(r);
		
		HUXTSP htsp = new HUXTSP();
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
		
		b.add(2);
		b.add(5);
		b.add(4);
		b.add(1);
		b.add(0);
		b.add(3);
		b.add(6);

		SolutionTSP s1 = new SolutionTSP(a);
		SolutionTSP s2 = new SolutionTSP(b);
		
		s1.printSolution();
		s2.printSolution();
		SolutionTSP ns = (SolutionTSP) htsp.cross(s1, s2);
		ns.printSolution();
	}
	
}
