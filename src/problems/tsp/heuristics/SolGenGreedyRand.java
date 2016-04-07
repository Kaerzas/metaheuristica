package problems.tsp.heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.configuration.Configuration;

import problems.ISolution;
import problems.tsp.SolGenRandomTSP;
import problems.tsp.InstanceTSP;
import problems.tsp.SolutionTSP;

public class SolGenGreedyRand extends SolGenRandomTSP {

	/** Number of nodes for doing the selection **/
	
	private int nNodes;
	private int nCandidates=50;
	private List<Integer> order;
	
	public ISolution generate() {		
		// Get the node number for the problem
		nNodes = ((InstanceTSP)instance).getNNodes();
		order = new ArrayList<Integer>();
		order.add(new Random().nextInt(nNodes));
		for(int i=1; i<nNodes; ++i){
			order.add(heuristic(order.get(order.size()-1)));
		}
		
		ISolution sol = new SolutionTSP(order);
		instance.evaluate(sol);		
		return sol;
	}

	// TODO access to config file
	public void configure(Configuration configuration) {
		this.nCandidates = configuration.getInt("nCandidates");
	}
	
	private Integer heuristic(Integer origin) {
		List<Integer> candidates = new ArrayList<Integer>();
		int tries;
		if(nNodes-order.size()>=nCandidates)
			tries=nCandidates;
		else
			tries=nNodes-order.size();
		for(int i=0; i < tries; ++i){
			Integer candidate;
			do{
				candidate = new Random().nextInt(nNodes);
			}while(order.contains(candidate));
			//TODO Should consider also candidates can't repeat between themselves??????
			candidates.add(candidate);
		}
		
		List<Double> distances = new ArrayList<Double>();
		for(int i=0; i < tries; ++i){
			distances.add(((InstanceTSP)instance).graph.distance(origin, candidates.get(i)));
		}
		int index = distances.indexOf(Collections.min(distances));
		return candidates.get(index);
	}
}