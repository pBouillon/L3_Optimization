package cli;

import optimization.Optimization;
import optimization.algorithms.OptimizationAlgorithm;
import optimization.algorithms.SimulatedAnnealing;
import optimization.algorithms.TabuSearch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import static cli.Text.*;

public class CLI {

    private static Scanner input = new Scanner(System.in) ;
    private static int MAX_RAND_VAL = 100 ;

    // Solver
    private static Optimization solver ;

    // Solver settings
    private static int nbProc ;
    private static int[] data ;
    private static OptimizationAlgorithm algorithm ;

    // interface display
    private static int tabuLimit ;
    private static int initTemp  ;

    /**
     *
     */
    private static String getUsrInput() {
        System.out.print(USR_INPUT) ;
        return input.nextLine() ;
    }

    /**
     *
     */
    private static void getUsrAlgoInput() {
        int usrChoice ;
        do {
            usrChoice = getUsrNumericInput(0) ;
        } while (usrChoice > ALGO_MAX) ;

        switch (usrChoice) {
            case ALGO_TABU:
                algorithm = new TabuSearch(nbProc) ;
                break ;
            case ALGO_SA:
                algorithm = new SimulatedAnnealing(nbProc) ;
                break ;
        }
    }

    /**
     *
     */
    private static int[] getUsrArrayInput() {
        String   rawData ;
        String[] splittedData ;

        int[] _data ;
        do {
            rawData = getUsrInput();
            splittedData = rawData.split("\\s+") ;

            try {
                _data = new int[splittedData.length] ;
                for (int i = 0; i < splittedData.length; ++i) {
                    _data[i] = Integer.parseInt(splittedData[i]) ;
                }
            } catch (NumberFormatException e) {
                System.out.println(ERR_BAD_TYPE) ;
                _data = new int[]{} ;
            }
        } while (_data.length < 1) ;

        return _data ;
    }

    /**
     *
     */
    private static double getUsrDoubleInput() {
        double inp ;
        do {
            try {
                inp = Double.parseDouble(getUsrInput());
            } catch (NumberFormatException e) {
                System.out.println(ERR_BAD_TYPE) ;
                inp = -1 ;
                continue ;
            }
            if (inp < 0) {
                System.out.println(ERR_BAD_NUMBER) ;
            }
        } while (inp < 0) ;
        return inp ;
    }
    /**
     *
     */
    private static int getUsrNumericInput() {
        return getUsrNumericInput(1) ;
    }

    /**
     *
     */
    private static int getUsrNumericInput(int min) {
        int inp ;
        do {
            try {
                inp = Integer.parseInt(getUsrInput());
            } catch (NumberFormatException e) {
                System.out.println(ERR_BAD_TYPE) ;
                inp = -1 ;
                continue ;
            }
            if (inp < 0) {
                System.out.println(ERR_BAD_NUMBER) ;
            }
        } while (inp < min) ;
        return inp ;
    }

    /**
     *
     */
    private static void performAlgo() {
        System.out.println ("== ALGO") ;

        System.out.println ("** Current algorithm is " + algorithm.toString()) ;
        System.out.println (MESSAGE_ALGO) ;
        getUsrAlgoInput() ;
        System.out.println();

        updateSolver() ;
    }

    /**
     *
     */
    private static void performData() {
        System.out.println ("== DATA") ;

        System.out.println ("** Tasks to generate: ") ;
        int nb = getUsrNumericInput(1) ;

        data = new int[nb] ;
        for (int i = 0; i < nb; ++i) {
            data[i] = (int) (Math.random() * MAX_RAND_VAL) ;
        }

        System.out.println ("** Current data are now" + Arrays.toString (data) + "\n") ;

        updateSolver() ;
    }

    /**
     *
     */
    private static void performProc() {
        System.out.println ("== PROCESSORS") ;

        System.out.println ("** You are currently using " + nbProc + " processor.s") ;
        System.out.println ("** Number of processors: ") ;
        nbProc = getUsrNumericInput() ;
        System.out.println();

        updateSolver() ;
    }

    /**
     *
     */
    private static void performRun() {
        HashMap<Integer, int[]> solution ;

        System.out.println ("== Run settings") ;

        int rdm ;
        System.out.println ("** Generate s0 randomly? ") ;
        System.out.println ("** (YES = 1 / NO = 0)") ;
        do {
            rdm = getUsrNumericInput(0) ;
        } while (rdm > 1) ;
        System.out.println () ;

        System.out.println ("** Specify the stagnation trigger:") ;
        System.out.println ("** (stop after seeing the same solution X times)") ;
        algorithm.setStagnationTrigger(getUsrNumericInput(1)) ;
        System.out.println () ;

        switch (algorithm.getID()) {
            case ALGO_TABU :
                System.out.println ("** Specify the tabu size for this search: ") ;
                algorithm.setTabuSize(getUsrNumericInput());
                break ;
            case ALGO_SA :
                System.out.println ("** Specify initial temperature: ") ;
                System.out.println ("** (after each iteration T = T * .9 until T == .1)") ;
                algorithm.setInitialTemp(getUsrDoubleInput());
                break ;
        }
        System.out.println () ;

        System.out.println ("== Run") ;
        System.out.println ("** Starting search ...") ;
        long tps = System.currentTimeMillis() ;

        int iteration = 0 ;
        solution = algorithm.getS0 (
                Arrays.stream(data).boxed().collect(Collectors.toList()),
                (rdm != 0)
        ) ;
        while (!algorithm.stopSearch()){
            solution = solver.solve(solution) ;
            System.out.println ("** Solution at iteration n°" + ++iteration + " : ") ;
            printSolution (solution) ;
            System.out.println (
                    "**\n** Current c_max: " +
                    algorithm.c_max(solution) +
                    " (solution seen " + algorithm.getStagnation() + " time.s)"
            ) ;
            System.out.println ("** --------------------------------------------- ") ;
        }


        System.out.println ("** Found in " + (System.currentTimeMillis() - tps) + "ms") ;
        System.out.println() ;
    }

    /**
     *
     */
    private static void performShowCfg() {
        System.out.println(
                String.join ("\n",
                        "== Config",
                        "** Current algorithm: " + algorithm.toString(),
                        "** Tasks durations  : " + Arrays.toString(data),
                        "** Processors number: " + nbProc,
                        ""
                )
        ) ;
    }

    /**
     *
     */
    private static void printSolution(HashMap<Integer, int[]> solution) {
        for (int key : solution.keySet()) {
            System.out.println ("**     " + key + ": " + Arrays.toString(solution.get(key))) ;
        }
    }

    /**
     *
     */
    private static void setup() {
        System.out.println ("== Parameters setting") ;

        // nb proc
        System.out.println ("** Number of processors: ") ;
        nbProc = getUsrNumericInput() ;

        // data
        System.out.println() ;
        System.out.println ("** Tasks durations (separated by ' '): ") ;
        System.out.println ("** (example: '5 7 6 1 2 4')") ;
        data = getUsrArrayInput() ;

        // algorithm
        System.out.println (MESSAGE_ALGO) ;
        getUsrAlgoInput() ;
        System.out.println() ;

        updateSolver() ;
    }

    /**
     *
     */
    public static void start() {
        System.out.println (MESSAGE_START) ;

        algorithm = null ;
        nbProc    = 0 ;
        data      = new int[]{} ;
        solver    = new Optimization (null) ;

        setup() ;

        int usrChoice ;
        do {
            System.out.println(MESSAGE_MENU) ;
            do {
                usrChoice = getUsrNumericInput(OPT_QUIT) ;
            } while (usrChoice > OPT_MAX) ;
            System.out.println() ;

            switch (usrChoice) {
                case OPT_RUN:
                    performRun() ;
                    break ;
                case OPT_CFG:
                    performShowCfg() ;
                    break ;
                case OPT_ALGO:
                    performAlgo() ;
                    break ;
                case OPT_DATA:
                    performData() ;
                    break ;
                case OPT_PROC:
                    performProc() ;
                    break ;
                default:
                    break ;
            }
        } while (usrChoice != OPT_QUIT) ;
    }

    /**
     *
     */
    private static void updateSolver() {
        solver.setAlgorithm (algorithm) ;
        solver.setNbProcess (nbProc) ;
        solver.setDurations (data) ;
    }
}
