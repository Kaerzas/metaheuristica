package metaheuristics.evolutive;

import java.util.List;

import problems.ISolution;
import util.config.IConfiguration;

public interface ISelection extends IConfiguration{
	
	public List<ISolution> select(List<ISolution> population);
}
