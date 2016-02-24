/**
 * 
 */
package metaheuristics;

import java.util.Random;

import org.apache.commons.configuration.Configuration;
import problems.IInstance;
import problems.ISolGenerator;
import problems.ISolution;
import util.config.IConfiguration;

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
	
	protected static ISolGenerator generator;
	
	/** Solution used in the representation */
	
	protected static ISolution solution;
	
	/** Instance of the problem */
	
	protected static IInstance instance;
	
	/** Random seed */
	
	protected static int seed;

	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	@Override
	public void configure(Configuration configuration) 
	{
		// Get the seed used
		seed = Integer.parseInt(configuration.getString("seed"));
		
		// Get the name of the classes
		String instanceName = configuration.getString("instance[@name]");
		String solutionName = configuration.getString("solution");
		String generatorName = configuration.getString("solGenerator");
		
		// Instantiate the classes used in the experiment
		try {
			
			// Instance class
			Class<? extends IInstance> instanceClass = 
					(Class<? extends IInstance>) Class.forName(instanceName);
			
			instance = instanceClass.newInstance();

			if(instance instanceof IConfiguration)
				((IConfiguration) instance).configure(configuration.subset("instance"));
			
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
			generator.setRandom(new Random(seed));
			generator.setInstance(instance);
			
			if(generator instanceof IConfiguration)
				((IConfiguration) generator).configure(configuration.subset("solGenerator"));
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
}