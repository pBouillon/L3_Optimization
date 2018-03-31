package optimization;

import optimization.algorithms.OptimizationAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Optimization {

    // Base input from the subject
    public static final int[] SUBJECT_INPUT = new int[] {
            5, 4, 3, 6, 5, 7
    } ;
    // Base process number from the subject
    public static final int SUBJECT_PROCESS = 2 ;

    // durations of each tasks
    private List<Integer> durations ;

    // current algorithm
    private OptimizationAlgorithm algorithm ;

    /**
     * Default constructor
     */
    public Optimization(OptimizationAlgorithm algo) {
        durations = new ArrayList<>() ;
        algorithm = algo ;
    }

    /**
     * Solve the current `duration` repartition among several process
     *
     * @return [id process][task ids]
     */
    public HashMap<Integer, int[]> solve(HashMap<Integer, int[]> state) {
        return algorithm.searchFor (durations, state) ;
    }

    /**
     * @param _durations collection of new durations
     */
    public void setDurations(int[] _durations) {
        durations = Arrays.stream(_durations)
                            .boxed()
                            .collect(Collectors.toList()) ;
    }

    /**
     * Change current algorithm
     *
     * @param _algorithm new algorithm
     */
    public void setAlgorithm(OptimizationAlgorithm _algorithm){
        algorithm = _algorithm ;
    }

    /**
     * Update process number
     *
     * @param nbProcess nb of process among which split tasks
     */
    public void setNbProcess(int nbProcess) {
        algorithm.setProcess(nbProcess) ;
    }
}
