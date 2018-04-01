# commands for algo
ALGO_TABU = '0'
ALGO_SA   = '1'

# commands for options
OPT_QUIT  = '0'
OPT_RUN   = '1'
OPT_CFG   = '2'
OPT_ALGO  = '3'
OPT_DATA  = '4'
OPT_PROC  = '5'

OPT_MAX   = '5'

# list of all commands
COMMANDS = [
    ALGO_TABU,
    ALGO_SA,
    OPT_QUIT,
    OPT_RUN,
    OPT_CFG,
    OPT_ALGO,
    OPT_DATA,
    OPT_PROC
]

USR_INPUT = "solver > "
USR_PARAMETERS = "== Parameters setting"
USR_NBPROCESS = "** Number of processors:\n"
USR_TASKS = "** Tasks durations (separated by ' '):\n** example: '5 7 6 1 2 4'\n"
USR_CHOICE = "** Algorithm choice:\n** 0/Tabu search\n** 1/ Simulated annealing"

class Cli :

    """Optimization solver"""
    """int nbProc"""
    """int[] data"""
    """OptimizationAlgorithm algo"""
    """int tabuLimit"""
    """int initTemp"""


    @staticmethod
    def __getUsrInput() -> str :
        return input(USR_INPUT)


    @staticmethod
    def __showTaskRepartition():
        print(
        "==\n"
        "** Task repartition solver\n"
        "**\n"
        "** Written in Java by\n" 
        "**     - Ewan Le Goallec [github.com/eLeGoalled]\n"
        "**     - Pierre Bouillon [pierrebouillon.tech/]\n"
        "**\n"
        "** Copyright (c) 2018 Pierre Bouillon, Ewan Le Goallec \n"
        "** MIT Licence \n"
        "==\n"
        )

    @staticmethod
    def __specifyNumberProcess() -> str:
        print(USR_NBPROCESS)
        return input(USR_INPUT)

    @staticmethod
    def __specifyData() -> list:
        print(USR_TASKS)
        data = input(USR_INPUT)
        return data.split()

    @staticmethod
    def __chooseAlgorithm():
        print(USR_CHOICE)
        choiceAlgo = input(USR_INPUT)
        if choiceAlgo == ALGO_TABU :
            Cli.__showMenu()

    @staticmethod
    def __showMenu():
        print(
            "== MAIN\n"
            "MENU\n"
            "** 1 / Run with current parameters\n"
            "** 2 / See current parameters\n"
            "** 3 / Change algorithm\n"
            "** 4 / Change data\n"
            "** 5 / Change number of processes\n"
            "** 0 / Leave CLI\n"
        )
        choice = input(USR_INPUT)
        if choice == OPT_RUN:
            Cli.__run()
        if choice == OPT_CFG:
            Cli.__printData()
        if choice == OPT_ALGO:
            Cli.__chooseAlgorithm()
        if choice == OPT_DATA:
            Cli.__specifyData()
        if choice == OPT_PROC:
            Cli.__specifyNumberProcess()
        if choice == OPT_QUIT:
            """leave cli"""

    @staticmethod
    def __run():
        print("run")

    @staticmethod
    def __printData():
        print("Data")