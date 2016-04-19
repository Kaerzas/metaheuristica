package metaheuristics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.configuration.Configuration;

import problems.IInstance;
import problems.ISolGenerator;
import problems.ISolution;
import util.config.IConfiguration;
import util.config.Stopwatch;

/**
 * @author i22balur
 *
 */
public abstract class AbstractAlgorithm implements IAlgorithm 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Generator used in the creation */
	
	protected ISolGenerator generator;
	
	/** Solution used in the representation */
	
	protected ISolution solution;
	
	/** Instance of the problem */
	
	protected IInstance instance;
	
	/** Random seed */
	protected Random random;
	
	/** */
	protected Stopwatch stopwatch;

	/** List of best solutions found (in order being found) */
	
	protected List<ISolution> bestSolutions;
	
	/** Number of iterations */
	
	protected int tries;
	
	/** Is the algorithm limited by time? */
	protected boolean limitedTime;
	
	/** Max time in nanoseconds*/
	protected long maxTime;
	
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public void execute() {
		stopwatch = new Stopwatch();
		bestSolutions = new ArrayList<ISolution>();
		
		stopwatch.start();
		search();
		stopwatch.stop();
	}
	
	/**
	 * Execute the search itself
	 */
	abstract protected void search();
	
	@SuppressWarnings("unchecked")
	@Override
	public void configure(Configuration configuration) 
	{
		//Instantiate the stopwatch
		stopwatch = new Stopwatch();
		bestSolutions = new ArrayList<ISolution>();
		
		
		// Get the seed used
		try{
			long seed;
			seed = Long.parseLong(configuration.getString("seed"));
			random = new Random(seed);
		} //If no seed specified, a random seed is given
		catch(NumberFormatException e){
			random = new Random();
		}
		
		// Get the name of the classes
		String instanceName = configuration.getString("instance[@name]");
		String solutionName = configuration.getString("solution");
		String generatorName = configuration.getString("solGenerator[@name]");
		
		// Instantiate the classes used in the experiment
		try {
			
			// Instance class
			Class<? extends IInstance> instanceClass = 
					(Class<? extends IInstance>) Class.forName(instanceName);
			
			instance = instanceClass.newInstance();

			if(instance instanceof IConfiguration)
				((IConfiguration) instance).configure(configuration.subset("instance"));
			
			instance.setRandom(random);
			
			// Solution class
			Class<? extends ISolution> solutionClass = 
					(Class<? extends ISolution>) Class.forName(solutionName);
			
			solution = solutionClass.newInstance();

			if(solution instanceof IConfiguration)
				((IConfiguration) solution).configure(configuration.subset("solution"));
			
			// Generator class
			Class<? extends ISolGenerator> generatorClass = 
					(Class<? extends ISolGenerator>) Class.forName(generatorName);
		
			generator = generatorClass.newInstance();
			
			if(generator instanceof IConfiguration)
				((IConfiguration) generator).configure(configuration.subset("solGenerator"));
			
			generator.setRandom(random);
			generator.setInstance(instance);
			
			if(configuration.containsKey("maxTime")){
				limitedTime = true;
				maxTime = (long) configuration.getDouble("maxTime");
			}
			else
				limitedTime = false;
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
	
	@Override
	public ISolGenerator getGenerator(){
		return generator;
	}
	
	@Override
	public Stopwatch getStopwatch(){
		return stopwatch;
	}
	
	@Override
	public List<ISolution> getBestSolutions(){
		return this.bestSolutions;
	}
	
	@Override
	public IInstance getInstance() {
		return instance;
	}
	
	@Override
	public Random getRandom(){
		return this.random;
	}
}