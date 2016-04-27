package metaheuristics.evolutive;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import problems.ISolution;

public class TournamentSelection extends AbstractSelection {

	private int tournamentSize;
	
	@Override
	public List<ISolution> select(List<ISolution> population, int popSize) {
		
		if(instance == null) {
			System.err.println("You should set the instance to the operator before using it");
			System.exit(0);
		}
		
		List <ISolution> newPop = new ArrayList<ISolution>(population.size());
		
		while(newPop.size()<popSize) {
			
			int [] participants = new int [tournamentSize];
			int winner;
			
			for(int i=0; i<tournamentSize; i++)
				participants[i] = instance.getRandom().nextInt(population.size());
			
			winner = participants[0];
			for(int i=1; i<participants.length; i++)
				if(instance.betterThan(population.get(participants[i]), population.get(winner)))
					winner = participants[i];			
			
			newPop.add(population.get(winner));
		}
		
		return newPop;
	}

	@Override
	public void configure(Configuration configuration) {
		this.tournamentSize = configuration.getInt("tournamentSize");
	}
	
}
