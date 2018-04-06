package optimization.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cli.Text.ALGO_SA;

public class SimulatedAnnealing extends OptimizationAlgorithm {
    public SimulatedAnnealing(int nbProcess) {
        super(nbProcess) ;
    }

    private double acceptanceProb (int bestCost, int newCost) {
        if (bestCost < newCost) return 1. ;
        return Math.exp ((bestCost - newCost) / temperature) ;
    }

    @Override
    public HashMap<Integer, int[]> searchFor(List<Integer> durations, HashMap<Integer, int[]> state) {

        double cooling = .9 ;

        // s0 -> first state
        currentSolution = state ;

        // s* = s0 -> first solution is the current best one
        currentBestSolution = new HashMap<>(currentSolution) ;
        bestSolutionTime = c_max(currentSolution) ;

        // keeping track of the last solution time
        int currentSolutionTime ;

        //
        ArrayList<HashMap<Integer, int[]>> neighbors ;

        // randomly picking a neighbor
        neighbors = getNeighbors() ;
        currentSolution = neighbors.get (r.nextInt(neighbors.size())) ;

        currentSolutionTime = c_max(currentSolution) ;

        if (acceptanceProb(bestSolutionTime, currentSolutionTime) > r.nextInt()) {
            currentBestSolution = new HashMap<>(currentSolution) ;
            bestSolutionTime = currentSolutionTime ;
        }

        temperature *= cooling ;

        return currentBestSolution ;
    }

    @Override
    public int getID() {
        return ALGO_SA ;
    }

    @Override
    public boolean stopSearch() {
        double tempMin = 0.00001 ;
        return temperature  < tempMin ;
    }

    @Override
    public String toString() {
        return "Simulated annealing" ;
    }
}
