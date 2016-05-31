package metaheuristics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	/** Absolute best solution found */
	protected ISolution globalBest;
	
	/** Time mark where globalBest was found*/
	protected long lastGlobalBestTime;
	
	/** Is the algorithm limited by time? */
	protected boolean limitedTime;
	
	/** Max time in nanoseconds*/
	protected long maxTime;
	
	/** Max time without improvement */
	protected long maxTimeWithoutImprovement;
	
	/** Algorithm configuration */
	protected String header;
	
	
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
	
	@Override
	public ISolution getBestSolution() {
		return globalBest;
	};
	
	/**
	 * Add solution to the best solutions list and log the time
	 * @param sol Solution to add
	 */
	protected void logSolution(ISolution sol){
		bestSolutions.add(sol);
		stopwatch.lap();
		
		if((globalBest == null) || instance.betterThan(sol, globalBest)){
			globalBest = sol;
			lastGlobalBestTime = stopwatch.currentElapsed();
		}
	}
	
	protected boolean maxTimeReached(){
		long current = stopwatch.currentElapsed();
		return limitedTime && //The algorithm is time-limited and
				((current >= maxTime) || //Max time has been reached or
				((current - lastGlobalBestTime) >= maxTimeWithoutImprovement)); //max time without improvement has been reached
	}
	
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
			
			if(configuration.containsKey("maxTimeWithoutImprovement"))
				this.maxTimeWithoutImprovement = readableTime(configuration.getString("maxTimeWithoutImprovement"));
			else
				this.maxTimeWithoutImprovement = Long.MAX_VALUE;
			
			this.lastGlobalBestTime = 0L;
			this.globalBest = null;
			
			if(configuration.containsKey("maxTime")){
				limitedTime = true;
				maxTime = readableTime(configuration.getString("maxTime"));
			}
			else
				limitedTime = false;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	//Transforms a string specifying time
	//(ns or human readable) into nanoseconds
	private static long readableTime(String input){
		long time;
		// Recognize time format
		Pattern nano  = Pattern.compile("[0-9]+"); //Time in nanoseconds
		Pattern human = Pattern.compile("(([0-9]+)h)?(([0-9]+)m)?(([0-9]+)s)?"); //Time in human readable format
		
		// Java regex is stupid
		Matcher nano_m  = nano.matcher(input);
		Matcher human_m = human.matcher(input);
		
		if(nano_m.matches())
			time = Long.parseLong(input);
		
		else if (human_m.matches() && (!input.isEmpty())){
			final long one_second = 1000000000L; // if definition of a second changes, change this value
			time = 0L;
			if(human_m.start(2) != -1){ // hours specified
				long hours = Long.parseLong(input.substring(human_m.start(2), human_m.end(2)));
				time += hours * 3600L * one_second; 
			}
			if(human_m.start(4) != -1){ // minutes specified
				long minutes = Long.parseLong(input.substring(human_m.start(4), human_m.end(4)));
				time += minutes * 60L * one_second; 
			}
			if(human_m.start(6) != -1){ // seconds specified
				long seconds = Long.parseLong(input.substring(human_m.start(6), human_m.end(6)));
				time += seconds * one_second; 
			}
		}
		
		else{
			String message = "Specified max time has invalid format\n";
			message += "Correct format is one of the following:\n";
			message += "\t<number_nanoseconds>\n";
			message += "\t[<number_hours>h][<number_minutes>m][<number_seconds>s]";
			throw new RuntimeException(message);
		}
		
		return time;
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
	
	@Override
	public String getHeader(){
		return header;
	}
	
	public void removeBestSolution() {
		globalBest = null;
	}
}