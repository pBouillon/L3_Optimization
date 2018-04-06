package cli;

public abstract class Text {
    // ERRORS
    final static String ERR_BAD_NUMBER = "## BAD NUMBER -- please try again" ;
    final static String ERR_BAD_TYPE   = "## BAD TYPE   -- please try again" ;

    // CORE
    final static String MESSAGE_START = String.join ("\n",
            "==",
            "** Task repartition solver",
            "**",
            "** Written in Java by ",
            "**     - Ewan Le Goallec [github.com/eLeGoalled]",
            "**     - Pierre Bouillon [pierrebouillon.tech/]",
            "**",
            "** Copyright (c) 2018 Pierre Bouillon, Ewan Le Goallec ",
            "** MIT Licence ",
            "==",
            "") ;

    // ALGO
    public final static int ALGO_TABU = 0 ;
    public final static int ALGO_SA   = 1 ;

    final static int ALGO_MAX  = 1 ;

    final static String MESSAGE_ALGO = String.join ("\n",
            "",
            "** Algorithm choice: ",
            "** " + ALGO_TABU + "/ Tabu search",
            "** " + ALGO_SA   + "/ Simulated annealing") ;

    // MENU
    final static int OPT_QUIT = 0 ;
    final static int OPT_RUN  = 1 ;
    final static int OPT_CFG  = 2 ;
    final static int OPT_ALGO = 3 ;
    final static int OPT_DATA = 4 ;
    final static int OPT_PROC = 5 ;

    final static int OPT_MAX  = 5 ;

    final static String MESSAGE_MENU = String.join ("\n",
            "== MAIN MENU",
            "** " + OPT_RUN  + "/ Run with current parameters",
            "** " + OPT_CFG  + "/ See current parameters",
            "** " + OPT_ALGO + "/ Change algorithm",
            "** " + OPT_DATA + "/ Change data",
            "** " + OPT_PROC + "/ Change number of processes",
            "** " + OPT_QUIT + "/ Leave CLI") ;

    // OTHER
    final static String USR_INPUT = "solver > " ;
}
