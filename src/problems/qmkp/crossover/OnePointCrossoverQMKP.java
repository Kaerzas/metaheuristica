package problems.qmkp.crossover;

import org.apache.commons.configuration.Configuration;

import problems.IInstance;
import problems.ISolution;
import problems.qmkp.InstanceQMKP;
import problems.qmkp.SolutionQMKP;
import metaheuristics.evolutive.AbstractCrossover;

public class OnePointCrossoverQMKP extends AbstractCrossover {

	InstanceQMKP instance;
	
	@Override
	public void initialize(IInstance instance) 
	{
		this.instance = (InstanceQMKP) instance;
	}

	@Override
	public ISolution cross(ISolution parentA, ISolution parentB) 
	{
		// Get the information
		int nObjects = instance.getNObjects();
		int crossPoint = instance.getRandom().nextInt(nObjects);
				
			/* Para probar el main
			int nObjects = 10;
			int crossPoint = (new Random()).nextInt(nObjects);
			System.out.println("point:"+crossPoint);
			*/
		
		// Cross the genotypes
		int s [] = new int[nObjects];
		System.arraycopy(((SolutionQMKP)parentA).getObjects(), 0, s, 0, crossPoint);
		System.arraycopy(((SolutionQMKP)parentB).getObjects(), crossPoint, s, crossPoint, nObjects - crossPoint);
		
		// Create and evaluate the son
		SolutionQMKP son = new SolutionQMKP(s);
		instance.evaluate(son);
	
		return son;
	}

	
	public SolutionQMKP [] cross2Sons(ISolution parentA, ISolution parentB) {
		// Get the information
		int nObjects = instance.getNObjects();
		int crossPoint = instance.getRandom().nextInt(nObjects);
		SolutionQMKP [] sons = new SolutionQMKP [2];
		
			/* Para probar el main
			int nObjects = 10;
			int crossPoint = (new Random()).nextInt(nObjects);
			System.out.println("point:"+crossPoint); */
				
		// Cross the genotypes
		int s1 [] = new int[nObjects];
		int s2 [] = new int[nObjects];
		
		// Son 1
		System.arraycopy(((SolutionQMKP)parentA).getObjects(), 0, s1, 0, crossPoint);
		System.arraycopy(((SolutionQMKP)parentB).getObjects(), crossPoint, s1, crossPoint, nObjects - crossPoint);
		
		// Son 2
		System.arraycopy(((SolutionQMKP)parentB).getObjects(), 0, s2, 0, crossPoint);
		System.arraycopy(((SolutionQMKP)parentA).getObjects(), crossPoint, s2, crossPoint, nObjects - crossPoint);
		
		// Create and evaluate the son
		SolutionQMKP son1 = new SolutionQMKP(s1);
		SolutionQMKP son2 = new SolutionQMKP(s2);
		
		instance.evaluate(son1);
		instance.evaluate(son2);
		
		// Add the sons to the array
		sons[0] = son1;
		sons[1] = son2;
		
		return sons;			
	}
	
	
	@Override
	public void configure(Configuration configuration) {
		// TODO Auto-generated method stub

	}

	
	public static void main(String args[]) {
		
		int nObjects = 10;
		
		int [] pg1 = new int [nObjects];
		int [] pg2 = new int [nObjects];
		
		for(int i=0; i< nObjects; i++) {
			pg1[i] = i;
			pg2[i] = nObjects-i;
		}
		
		SolutionQMKP p1 = new SolutionQMKP(pg1);
		SolutionQMKP p2 = new SolutionQMKP(pg2);
		
		
		OnePointCrossoverQMKP crossover = new OnePointCrossoverQMKP();
		
		// Test coss
		SolutionQMKP son = (SolutionQMKP) crossover.cross(p1, p2);
		((SolutionQMKP)son).printSolution();
		
		// Test cross2Sons
		SolutionQMKP sons [] = crossover.cross2Sons(p1, p2);
		((SolutionQMKP)sons[0]).printSolution();
		((SolutionQMKP)sons[1]).printSolution();
	}
	
}
