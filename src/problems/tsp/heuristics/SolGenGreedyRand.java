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
	private int nCandidates;		// Number of candidates to select one node
	private List<Integer> order;	// Visit's order of nodes
	
	public ISolution generate() {
		nNodes = ((InstanceTSP)instance).getNNodes();
		order = new ArrayList<Integer>(nNodes);
		// First node is random
		order.add(new Random().nextInt(nNodes));
		// Next ones are selected by heuristic (closest from current)
		for(int i=1; i<nNodes; ++i)
			order.add(heuristic(order.get(order.size()-1)));
		
		ISolution sol = new SolutionTSP(order);
		instance.evaluate(sol);		
		return sol;
	}

	public void configure(Configuration configuration) {
		this.nCandidates = configuration.getInt("nCandidates");
	}
	
	private Integer heuristic(Integer origin) {
		// Candidates number has to get smaller when there'r little nodes without visiting
		int nSelection;
		if(nNodes-order.size()>=nCandidates)
			nSelection=nCandidates;
		else
			nSelection=nNodes-order.size();
		
		// List of possibles nodes (not assigned)
		List<Integer> possibles = new ArrayList<Integer>(nNodes-order.size());
		for(int i=0; i<nNodes; ++i)
			if(!order.contains(i))
				possibles.add(i);
		
		// List of candidates
		List<Integer> candidates = new ArrayList<Integer>(nSelection);
		Collections.shuffle(possibles);
		candidates.subList(0, nSelection);
		
		// Calculate distances between origin and selected nodes 
		List<Double> distances = new ArrayList<Double>();
		for(int i=0; i < nSelection; ++i)
			distances.add(((InstanceTSP)instance).graph.distance(origin, candidates.get(i)));
		
		// Return index of node with smallest distance
		int index = distances.indexOf(Collections.min(distances));
		return candidates.get(index);
	}
}