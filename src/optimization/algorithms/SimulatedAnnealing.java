package optimization.algorithms;

import java.util.HashMap;
import java.util.List;

import static cli.Text.ALGO_SA;

public class SimulatedAnnealing extends OptimizationAlgorithm {
    public SimulatedAnnealing(int nbProcess) {
        super(nbProcess) ;
    }

    private double acceptanceProb (int bestCost, int newCost) {
        // best solution always accepted
        if (bestCost < newCost) return 1. ;
        // else evaluate the condition of Metropolis
        return Math.exp (-(newCost - bestCost) / temperature) ;
    }

    @Override
    public HashMap<Integer, int[]> searchFor(List<Integer> durations, HashMap<Integer, int[]> state) {

        double cooling = .99 ;

        // s0 -> first state
        currentSolution = state ;

        // s* = s0 -> first solution is the current best one
        currentBestSolution = new HashMap<>(currentSolution) ;
        bestSolutionTime = c_max(currentSolution) ;

        // keeping track of the last solution time
        int currentSolutionTime ;

        // randomly picking a neighbor
        currentSolution = getRandomNeighbors() ;

        currentSolutionTime = c_max(currentSolution) ;

        ++stagnation ;
        // Checking acceptance for the new solution
        if (acceptanceProb(bestSolutionTime, currentSolutionTime) > r.nextInt()) {
            stagnation = 0 ;
            currentBestSolution = new HashMap<>(currentSolution) ;
            bestSolutionTime = currentSolutionTime ;
        }

        // cooling the current temperature by 1%
        temperature *= cooling ;

        return currentBestSolution ;
    }

    @Override
    public int getID() {
        return ALGO_SA ;
    }

    @Override
    public boolean stopSearch() {
        double tempMin = 0.01 ;
        return temperature  < tempMin || stagnation >= stagnationTrigger;
    }

    @Override
    public String toString() {
        return "Simulated annealing" ;
    }
}
