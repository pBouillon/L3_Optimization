package optimization.algorithms;

import java.util.*;
import java.util.stream.IntStream;

public abstract class OptimizationAlgorithm {
    Random r = new Random() ;

    int stagnation ;
    int stagnationTrigger ;

    int tabuSize ;
    double temperature ;
    double initialTemp ;

    private int nbProcess  ;

    // tabus : key of each state as a string
    ArrayList<String> visited ;

    // s0
    HashMap<Integer, int[]> currentSolution ;

    // s*
    HashMap<Integer, int[]> currentBestSolution ;

    // f(s*)
    int bestSolutionTime ;

    OptimizationAlgorithm(int _nbProcess) {
        visited = new ArrayList<>() ;

        currentBestSolution = new HashMap<>() ;
        currentSolution     = new HashMap<>() ;
        bestSolutionTime    = 0 ;
        stagnation    = 0 ;

        tabuSize = 100 ;

        nbProcess = _nbProcess ;
        setProcess (nbProcess) ;
    }

    /**
     * f(x) ; goal function
     *
     * @param repartition current repartition
     * @return time of the longest process
     */
    public int c_max(HashMap<Integer, int[]> repartition) {
        int max = Integer.MIN_VALUE ;

        int localMax ;
        for (int[] tasks : repartition.values()) {
            localMax = IntStream.of(tasks).sum() ;
            if (localMax > max)  max = localMax  ;
        }

        return max ;
    }

    /**
     * Reset parameters for the search
     */
    void resetSearchParam() {
        currentBestSolution.clear() ;
        currentSolution.clear() ;

        for (int procID = 0; procID < nbProcess; ++procID) {
            currentBestSolution.put (procID, new int[]{}) ;
            currentSolution.put (procID, new int[]{}) ;
        }

        bestSolutionTime = 0 ;
        stagnation = 0 ;
    }

    /**
     * @param durations list of task durations
     * @return <[id process][task durations]>
     */
    public abstract HashMap<Integer, int[]> searchFor(List<Integer> durations, HashMap<Integer, int[]> state) ;

    /**
     * Change number of process and refresh repartition
     * @param _nbProcess new number of process
     */
    public void setProcess(int _nbProcess) {
        nbProcess = _nbProcess ;

        resetSearchParam() ;
    }

    /**
     * Generate the first state of the tabu search
     *
     * @param durations   tasks
     * @param randomState if s0 should be randomly generated
     * @return s0
     */
    public HashMap<Integer, int[]> getS0(List<Integer> durations, boolean randomState) {
        resetSearchParam() ;
        temperature = initialTemp ;

        HashMap<Integer, int[]> s0 = new HashMap<>() ;
        for (int i = 0; i < nbProcess; ++i) s0.put (i, new int[]{}) ;

        if (randomState) {
            int processID ;
            int tasks[] ;
            for (Integer duration : durations) {
                processID = r.nextInt(nbProcess);
                tasks = new int[s0.get(processID).length + 1];
                for (int i = 0; i < tasks.length - 1; ++i) {
                    tasks[i] = s0.get(processID)[i];
                }
                tasks[tasks.length - 1] = duration;
                s0.put(processID, tasks);
            }
        }
        else {
            // All tasks in the first process
            s0.put (
                    0,
                    durations.stream()
                            .mapToInt(i->i)
                            .toArray()
            ) ;
        }

        return s0 ;
    }

    /**
     * Search for neighbors of `currentSolution`
     * @return neighbors states
     */
    ArrayList<HashMap<Integer, int[]>> getNeighbors() {
        ArrayList<HashMap<Integer, int[]>> neighbors = new ArrayList<>() ;
        ArrayList<String> neighborsKey = new ArrayList<>() ;
        HashMap<Integer, int[]> successor ;

        List<Integer> stash = new ArrayList<>() ;

        // for each process
        for (int procSource = 0; procSource < nbProcess; ++procSource) {
            for (int taskID = 0; taskID < currentSolution.get(procSource).length; ++taskID) {
                for (int procDest = 0; procDest < nbProcess; ++procDest) {

                    // same process = no correct neighbors
                    if (procSource == procDest) continue ;

                    // initializing successor with current state
                    successor = new HashMap<>(currentSolution) ;

                    // updating destination proc values
                    stash.clear() ;

                    for (int val : currentSolution.get(procDest)) stash.add(val) ;
                    stash.add(currentSolution.get(procSource)[taskID]) ;

                    successor.put (
                            procDest,
                            stash.stream().mapToInt(value->value).toArray()
                    ) ;

                    // updating source proc values
                    stash.clear() ;

                    for (int val : currentSolution.get(procSource)) stash.add(val) ;
                    stash.remove(taskID) ;

                    successor.put (
                            procSource,
                            stash.stream().mapToInt(value->value).toArray()
                    ) ;

                    // checking is successor already exists
                    if (!neighborsKey.contains(getKeyFor(successor))) {
                        neighbors.add(successor) ;
                        neighborsKey.add(getKeyFor(successor)) ;
                    }
                }
            }
        }
        return neighbors ;
    }

    /**
     * Convert an HashMap to a key string
     *
     * for a node n with processes as pX and tasks as tN
     * generate: p0t1t4-p1t2-p2t0t3-
     *
     * @param node state to convert
     * @return the generated key
     */
    String getKeyFor(HashMap<Integer, int[]> node) {
        StringBuilder res = new StringBuilder() ;

        int[] values ;
        for (int proc = 0; proc < nbProcess; ++proc) {
            values = node.get(proc) ;
            Arrays.sort(values) ;

            res.append(proc).append(":") ;
            for (int val : values) res.append(val).append(",") ;
            res.append("-") ;
        }

        return res.toString() ;
    }

    /**
     * For CLI purpose
     */
    public abstract int getID() ;

    /**
     * Stopping conditions
     * @return true if one of the stopping condition is reached
     */
    public abstract boolean stopSearch() ;

    public void setTabuSize(int _tabuSize) {
        tabuSize = _tabuSize;
    }

    public int getStagnation() {
        return stagnation;
    }

    public void setStagnationTrigger(int _stagnationTrigger) {
        stagnationTrigger = _stagnationTrigger ;
    }

    public void setInitialTemp (double _initialTemp) {
        initialTemp = _initialTemp ;
    }
}
