package metaheuristics.evolutive;

import problems.IInstance;

public abstract class AbstractSelection implements ISelection{

	protected IInstance instance;

	/**
	 * @return the instance
	 */
	public IInstance getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public void setInstance(IInstance instance) {
		this.instance = instance;
	}
	
}
