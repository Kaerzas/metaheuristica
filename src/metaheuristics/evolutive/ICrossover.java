package metaheuristics.evolutive;

import problems.ISolution;
import util.config.IConfiguration;

public interface ICrossover extends IConfiguration{

	public ISolution cross(ISolution parentA, ISolution parentB); 
}
