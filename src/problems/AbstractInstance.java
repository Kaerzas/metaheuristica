package problems;

public abstract class AbstractInstance implements IInstance {

	protected boolean maximize;

	@Override
	public boolean betterThan(ISolution sol1, ISolution sol2) 
	{
		if((sol1.getFitness() > sol2.getFitness()) && maximize)
			return true;
		if((sol1.getFitness() <= sol2.getFitness()) && !maximize)
			return true;

		return false;
	}

}
