package optimization.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cli.Text.ALGO_TABU;

public class TabuSearch extends OptimizationAlgorithm {
    /**
     * Default constructor
     *
     * @param nbProcess number of process among which split tasks
     */
    public TabuSearch(int nbProcess) {
        super(nbProcess) ;
    }

    /**
     * Check is this neighbor already exists
     * @param visited list of key for visited states
     * @param node state to check
     * @return true if already visited, false otherwise
     */
    private boolean notVisited(ArrayList<String> visited, HashMap<Integer, int[]> node) {
        String currentKey = getKeyFor(node) ;

        for (String pastNodeKey : visited) {
            if (pastNodeKey.equals(currentKey)) return false ;
        }

        return true ;
    }

    /**
     * Evaluate the tabu search for current data
     *
     * @param durations list of task durations
     * @return best repartition found
     */
    @Override
    public HashMap<Integer, int[]> searchFor(List<Integer> durations, HashMap<Integer, int[]> state) {
        // keeping track of the last solution time
        int currentSolutionTime ;

        // generating s0
        currentSolution = state ;

        // s* = s0 -> first solution is the current best one
        currentBestSolution = new HashMap<>(currentSolution) ;

        // f(s*) -> current best time
        bestSolutionTime = c_max(currentSolution) ;

        // initializing neighbor handling
        ArrayList<HashMap<Integer, int[]>> neighborList ;
        HashMap<Integer, int[]> bestNeighbor ;

        int neighborBestLocalTime ; // f(x) for best neighbor
        int neighborLocalTime ;     // f(x) for current neighbor

        // getting next neighbor
        neighborList = getNeighbors() ;

        // initializing neighbor search with the first one
        bestNeighbor = neighborList.get(0) ;
        neighborBestLocalTime = c_max (bestNeighbor) ;

        // for each neighbor
        for (HashMap<Integer, int[]> neighbor : neighborList) {
            // ignoring the first one (already initialized)
            if (bestNeighbor == neighbor) continue ;

            // ignoring neighbor if it is already visited
            if (!notVisited(visited, neighbor)) continue ;

            // if f(x) doesn't improve the solution, ignoring
            if ((neighborLocalTime = c_max(neighbor))
                    > neighborBestLocalTime) continue ;

            // updating best neighbor and best neighbor's time
            bestNeighbor = neighbor ;
            neighborBestLocalTime = neighborLocalTime ;
        }

        // setting best neighbor as current solution
        currentSolution = bestNeighbor ;

        // if this solution is faster
        if ((currentSolutionTime = c_max(currentSolution)) <= bestSolutionTime) {
            if (currentSolutionTime < bestSolutionTime) stagnation = 0 ;
            else ++stagnation ;

            // s* = s
            currentBestSolution = new HashMap<>(currentSolution) ;
            // updating f(s*)
            bestSolutionTime = currentSolutionTime ;
        } else {
            ++stagnation ;
        }

        // updating tabus -> adding current state to visited
        visited.add (getKeyFor (currentBestSolution)) ;

        // checking tabu size
        if (visited.size() == tabuSize) visited.remove(0) ;

        return new HashMap<>(currentBestSolution) ;
    }

    @Override
    public int getID() {
        return ALGO_TABU ;
    }

    @Override
    public boolean stopSearch() {
        return stagnation > stagnationTrigger;
    }

    @Override
    public String toString() {
        return "Tabu search" ;
    }
}
