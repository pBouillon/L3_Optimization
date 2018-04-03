# commands for algo
ALGO_TABU = 0
ALGO_SA   = 1

# commands for options
OPT_QUIT  = 0
OPT_RUN   = 1
OPT_CFG   = 2
OPT_ALGO  = 3
OPT_DATA  = 4
OPT_PROC  = 5

OPT_MAX   = 5

# list of all commands
COMMANDS = [
    OPT_ALGO,
    OPT_CFG ,
    OPT_DATA,
    OPT_PROC,
    OPT_QUIT,
    OPT_RUN ,
    ALGO_SA ,
    ALGO_TABU
]

# reusable huge strings for display
USR_CHOICE = """
=== ALGORITHM CHOICE
** Algorithm choice:
** """ + str(ALGO_TABU) + """ / Tabu search
** """ + str(ALGO_SA)   + """ / Simulated annealing
"""
USR_INPUT = "solver > "
USR_INTRO = """
===
** Task repartition solver
**
** Written in Python by:
**      - Ewan Le Goallec [github.com/eLeGoalled] 
**      - Pierre Bouillon [pierrebouillon.tech/]
**
** Java version also available at [github.com/pBouillon/L3_Optimization]
** Copyright (c) 2018 Pierre Bouillon, Ewan Le Goallec
** MIT Licence
===
"""
USR_MENU = """
=== MENU
** """ + str(OPT_RUN)  + """ / Run with current parameters
** """ + str(OPT_CFG)  + """ / See current parameters
** """ + str(OPT_ALGO) + """ / Change algorithm
** """ + str(OPT_DATA) + """ / Change data
** """ + str(OPT_PROC) + """ / Change number of processes
** """ + str(OPT_QUIT) + """ / Exit
"""


class CLI:
    """ Cli
    Class grouping several static methods to build the CLI
    """

    @staticmethod
    def __get_usr_input() -> int :
        """
        Get the user input and ensure that it's an int
        :return: user input as integer
        """
        usr_inp = input(USR_INPUT)
        while not usr_inp.isdigit():
            print ('~~ WRONG TYPE, expected an integer')
        return int(usr_inp)

    @staticmethod
    def __show_tasks_repartition():
        """

        :return:
        """
        print (USR_INTRO)

    @staticmethod
    def __specify_processes_number() -> str:
        """

        :return:
        """
        print ('** Please specify how many processes you have')
        return input(USR_INPUT)

    @staticmethod
    def __specify_data() -> list:
        """

        :return:
        """
        print (
            '** Tasks durations (separated by ' '; ex: 5 7 6 1 2 4)'
        )
        data = input(USR_INPUT)
        return data.split()

    @staticmethod
    def __choose_algorithm():
        """

        :return:
        """
        print (USR_CHOICE)

    @staticmethod
    def __show_menu():
        """

        :return:
        """
        print (USR_MENU)
        choice = CLI.__get_usr_input()

        if choice == OPT_RUN:
            CLI.__run()

        elif choice == OPT_CFG:
            CLI.__print_data()

        elif choice == OPT_ALGO:
            CLI.__choose_algorithm()

        elif choice == OPT_DATA:
            CLI.__specify_data()

        elif choice == OPT_PROC:
            CLI.__specify_processes_number()

        elif choice == OPT_QUIT:
            print ('=== CLOSING CLI')
            exit (0)

    @staticmethod
    def __run():
        """

        :return:
        """
        print("run")

    @staticmethod
    def __print_data():
        """

        :return:
        """
        print("Data")
