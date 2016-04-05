package metaheuristics;

import org.apache.commons.configuration.Configuration;

import metaheuristics.localsearch.INeighExplorator;
import problems.ISolution;
import util.config.IConfiguration;

public class SimulatedAnnealing extends AbstractAlgorithm{

	private INeighExplorator explorator;
	
	private double initialTemperature;
	private double minimumTemperature;
	/**
	 * Current temperature
	 */
	private double currentTemperature;
	
	/**
	 * Acceptance rule for neighbours
	 * 
	 * Valid values = {"metropolis", "logistic"}
	 */
	private String acceptanceRule;
	
	/**
	 * Scheme used for cooldown
	 * 
	 * Valid values = {"exponential", "boltzmann", "linear", "cauchy"}
	 */
	private String coolingScheme;
	
	/**
	 * Speed at which the search cools down. Only applicable for exponential and linear cooling schemes.
	 */
	private double coolingRatio;
	
	/**
	 * Current cooling iteration
	 */
	private int iteration;
	
	@Override
	protected void search() {
		iteration = 0;
		currentTemperature = initialTemperature;
		
		//Initial (random) solution
		ISolution currentSolution = generator.generate();
		stopwatch.lap();
		bestSolutions.add(currentSolution);
		
		while(currentTemperature > minimumTemperature){
			//System.out.println(currentTemperature);
			ISolution newSol = explorator.generateBestNeighbour(currentSolution);
			
			if(accept(currentSolution, newSol)){
				stopwatch.lap();
				bestSolutions.add(newSol);
				currentSolution = newSol;
			}
			
			cooldown();
			iteration++;
		}
	}
	
	/**
	 * Decides if solution is acceptable according to temperature and fitness
	 * @return Is the solution acceptable?
	 */
	private boolean accept(ISolution previousSol, ISolution newSol){
		double fitnessInc; //Fitness increment (delta E)
		double probability; //Acceptance probability
		
		fitnessInc = newSol.getFitness() - previousSol.getFitness();
		
		switch(acceptanceRule){
		case "metropolis":
			//Always accept if the new solution is better
			if(instance.betterThan(newSol, previousSol))
				return true;
			else{
				probability = Math.exp((-fitnessInc) / currentTemperature);
				return random.nextDouble() < probability;
			}
			
		case "logistic":
			probability = 1.0 - (1.0 / (1.0 + Math.exp(fitnessInc / currentTemperature)));
			return random.nextDouble() < probability;
			
		default:
			throw new UnsupportedOperationException("The specified acceptance rule \"" + acceptanceRule + "\" is not supported");
		}
	}

	/**
	 * Decrease temperature according to method
	 */
	private void cooldown(){
		switch(coolingScheme){
		case "exponential":
			currentTemperature *= coolingRatio;
			break;
			
		case "boltzmann":
			currentTemperature = initialTemperature / (1 + Math.log(iteration+1));
			break;
			
		case "linear":
			currentTemperature -= coolingRatio; 
			break;
			
		case "cauchy":
			currentTemperature = initialTemperature / (1 + iteration);
			break;
			
		default:
			throw new UnsupportedOperationException("The specified cooling scheme \"" + coolingScheme + "\" is not supported");
		}
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public void configure(Configuration configuration){
		super.configure(configuration);
		
		initialTemperature = configuration.getDouble("initialTemperature");
		minimumTemperature = configuration.getDouble("minimumTemperature");
		acceptanceRule = configuration.getString("acceptanceRule");
		coolingScheme = configuration.getString("coolingScheme");
		
		if(coolingScheme.equals("exponential") || coolingScheme.equals("linear"))
			coolingRatio = configuration.getDouble("coolingRatio");
		
		
		try {
			// Get the name of the explorator class
			String exploratorName = configuration.getString("explorator[@name]");
			
			// Instance class
			Class<? extends INeighExplorator> exploratorClass = 
					(Class<? extends INeighExplorator>) Class.forName(exploratorName);
			
			explorator = exploratorClass.newInstance();
			explorator.setInstance(instance);
			
			if(explorator instanceof IConfiguration)
				((IConfiguration) explorator).configure(configuration.subset("explorator"));
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
}
