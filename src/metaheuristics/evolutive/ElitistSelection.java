package metaheuristics.evolutive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import problems.ISolution;

public class ElitistSelection extends AbstractSelection {

	@Override
	public List<ISolution> select(List<ISolution> population, int popSize) {
		
		if(instance == null) {
			System.err.println("You should set the instance to the operator before using it");
			System.exit(0);
		}
		
		// TODO no se si esto se podra hacer (yo creo que si)
		List <ISolution> newPop = new ArrayList<ISolution>(population);
		Collections.sort(newPop, new FitnessComparator(instance));
		
		newPop = newPop.subList(newPop.size()-popSize, newPop.size());	
		return newPop;
	}

	@Override
	public void configure(Configuration configuration) {
		
	}
}
