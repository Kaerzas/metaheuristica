package metaheuristics.evolutive;

import java.util.Comparator;

import problems.ISolution;

public class FitnessComparator implements Comparator<ISolution>{

    @Override
    public int compare(ISolution o1, ISolution o2) {
        return (int) (o1.getFitness()-o2.getFitness());
    }
}