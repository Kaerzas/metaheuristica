package metaheuristics.localsearch;

import org.apache.commons.configuration.Configuration;

import metaheuristics.localsearch.operator.INeighOperator;
import problems.IInstance;

public abstract class AbstractNeighExplorator implements INeighExplorator 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	protected INeighOperator operator;
	
	protected IInstance instance;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	public IInstance getInstance() 
	{
		return instance;
	}

	public void setInstance(IInstance instance) 
	{
		this.instance = instance;
	}

	public INeighOperator getOperator() 
	{
		return operator;
	}

	public void setOperator(INeighOperator operator) 
	{
		this.operator = operator;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void configure(Configuration configuration) 
	{
		try {
			// Get the name of the explorator class
			String operatorName = configuration.getString("operator");
	
			// Instance class
			Class<? extends INeighOperator> operatorClass = 
					(Class<? extends INeighOperator>) Class.forName(operatorName);
			
			operator = operatorClass.newInstance();
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
}