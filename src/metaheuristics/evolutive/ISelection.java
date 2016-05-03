package metaheuristics.evolutive;

import java.util.List;

import problems.IInstance;
import problems.ISolution;
import util.config.IConfiguration;

public interface ISelection extends IConfiguration{
	
	public void setInstance(IInstance instance);
	public IInstance getInstance();
	public List<ISolution> select(List<ISolution> population, int popSize);
}
