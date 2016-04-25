package metaheuristics.evolutive;

import problems.IInstance;
import problems.ISolution;
import util.config.IConfiguration;

public interface ICrossover extends IConfiguration{
	
	public void initialize(IInstance instance);
	public ISolution cross(ISolution parentA, ISolution parentB); 
}
