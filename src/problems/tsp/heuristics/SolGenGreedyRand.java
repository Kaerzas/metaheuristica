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

	private int nNodes;				// Problem's number of nodes
	private int nCandidates=50;		// Number of candidates to select one node
	private List<Integer> order;	// Visit's order of nodes
	
	public ISolution generate() {
		nNodes = ((InstanceTSP)instance).getNNodes();
		order = new ArrayList<Integer>();
		// First node is random
		order.add(new Random().nextInt(nNodes));
		// Next ones are selected by heuristic (closest from current)
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
		// List of candidates
		List<Integer> candidates = new ArrayList<Integer>();
		// Candidates number has to get smaller when there's little nodes without visiting
		int nSelection;
		if(nNodes-order.size()>=nCandidates)
			nSelection=nCandidates;
		else
			nSelection=nNodes-order.size();
		
		// Get "nSelection" nodes who have not been in "order"
		for(int i=0; i < nSelection; ++i){
			Integer candidate;
			do{
				candidate = new Random().nextInt(nNodes);
			}while(order.contains(candidate));
			//TODO Should consider also candidates can't repeat between themselves??????
			candidates.add(candidate);
		}
		//TODO More efficient if set of possible nodes is becoming smaller
		
		// Calculate distances between origin and selected nodes 
		List<Double> distances = new ArrayList<Double>();
		for(int i=0; i < nSelection; ++i){
			distances.add(((InstanceTSP)instance).graph.distance(origin, candidates.get(i)));
			//TODO I had to change graph attribute to public
		}
		
		// Return index of node with smallest distance
		int index = distances.indexOf(Collections.min(distances));
		return candidates.get(index);
	}
}