package problems.knapsack.heuristics;

import java.util.Comparator;

import problems.knapsack.KPObject;

public class ComparatorRatioKP implements Comparator<KPObject> {

	@Override
	public int compare(KPObject obj1, KPObject obj2) {
		
		int ratio1, ratio2;
		// Get the ratios
		ratio1 = obj1.getValue()/obj1.getWeight();
		ratio2 = obj2.getValue()/obj2.getWeight();
		// Return the best
		return ratio2 - ratio1;
	}
}
