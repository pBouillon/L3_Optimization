# commands for algo
from random import randint

from optimizer import DEFAULT_MAX_IT
from simulated_annealing import SimulatedAnnealing, DEFAULT_COOLING, DEFAULT_TEMP
from tabu_search import TabuSearch

ALGO_TABU = 0
ALGO_SA   = 1

# commands for options
OPT_QUIT = 0
OPT_RUN  = 1
OPT_CFG  = 2
OPT_DATA = 3
OPT_PROC = 4

OPT_MAX = 5

# list of all commands
COMMANDS = [
    OPT_CFG,
    OPT_DATA,
    OPT_PROC,
    OPT_QUIT,
    OPT_RUN,
    ALGO_SA,
    ALGO_TABU
]

# reusable huge strings for display
USR_CHOICE = """
=== ALGORITHM CHOICE
** Algorithm choice:
** """ + str(ALGO_TABU) + """ / Tabu search
** """ + str(ALGO_SA) + """ / Simulated annealing """
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
** """ + str(OPT_RUN) + """ / Run with current parameters
** """ + str(OPT_CFG) + """ / See current parameters
** """ + str(OPT_DATA) + """ / Change data
** """ + str(OPT_PROC) + """ / Change number of processes
** """ + str(OPT_QUIT) + """ / Exit """


class CLI:
    """ Cli
    Class grouping several static methods to build the CLI
    """

    procs = 0
    data  = []
    sol   = {}

    @staticmethod
    def __get_usr_input() -> int:
        """
        Get the user input and ensure that it's an int
        :return: user input as integer
        """
        usr_inp = input(USR_INPUT)
        while not usr_inp.isdigit():
            print('~~ WRONG TYPE, expected an integer')
            usr_inp = input(USR_INPUT)
        return int(usr_inp)

    @staticmethod
    def __get_usr_float() -> float:
        """
        Get the user input and ensure that it's an int
        :return: user input as integer
        """
        usr_inp = input(USR_INPUT)
        while not usr_inp.isdigit():
            print('~~ WRONG TYPE, expected a float')
            usr_inp = input(USR_INPUT)
        return float(usr_inp)

    @staticmethod
    def __get_usr_random() -> bool:
        """
        :return:
        """
        print('** Generate s0 randomly? (YES 1 / NO 0)')
        usr_inp = input(USR_INPUT)
        while not usr_inp.isdigit() \
                and usr_inp is not '0' \
                and usr_inp is not '1':
            print('~~ WRONG INPUT, expected an integer equal to 0 or 1')
        return True if usr_inp is '1' else False

    @staticmethod
    def __show_solution():
        """
        :return:
        """
        for key, values in CLI.sol.items():
            print(
                '**\t' + str(key) + ': ' + str(values)
                + '**\n\t\ttotal: ' + str(sum(values))
            )

    @staticmethod
    def __specify_processes_number() -> int:
        """

        :return:
        """
        print('** With how many processes do you want to work? (0 for random)')
        proc = CLI.__get_usr_input()
        return proc if proc > 0 else randint(0, 50)

    @staticmethod
    def __specify_data() -> list:
        """
        :return:
        """
        print('** How many tasks do you want to generate?')
        tasks = CLI.__get_usr_input()

        print('** What should be their maximum value?')
        max_val = CLI.__get_usr_input()

        return [randint(1, max_val) for _ in range(tasks)]

    @staticmethod
    def __alter_data():
        print('\n=== NEW CONFIGURATION')
        CLI.data = CLI.__specify_data()

        CLI.__print_config()

    @staticmethod
    def __alter_proc():
        print('\n=== NEW CONFIGURATION')
        CLI.procs = CLI.__specify_processes_number()

        CLI.__print_config()

    @staticmethod
    def __choose_algorithm() -> int:
        """
        :return:
        """
        print(USR_CHOICE)
        usr_inp = input(USR_INPUT)
        while not usr_inp.isdigit() \
                and int(usr_inp) != ALGO_TABU \
                and int(usr_inp != ALGO_SA):
            print(
                '~~ WRONG INPUT, expected an integer equal to '
                + str(ALGO_TABU) + ' or '
                + str(ALGO_SA)
            )
        return int(usr_inp)

    @staticmethod
    def __show_menu():
        """

        :return:
        """
        choice = OPT_RUN

        while choice != OPT_QUIT:
            print(USR_MENU)
            choice = CLI.__get_usr_input()

            if choice == OPT_RUN:
                CLI.__run()

            elif choice == OPT_CFG:
                CLI.__print_config()

            elif choice == OPT_DATA:
                CLI.__alter_data()

            elif choice == OPT_PROC:
                CLI.__alter_proc()

        print('=== CLOSING CLI')
        exit(0)

    @staticmethod
    def __run():
        """
        :return:
        """
        algo_id = CLI.__choose_algorithm()
        if algo_id == ALGO_TABU:
            print('** Specify the tabu size:')
            tab_size = CLI.__get_usr_input()

            solver = TabuSearch(
                CLI.procs,
                CLI.data,
                DEFAULT_MAX_IT,
                tabu_size=tab_size
            )
        else:
            print('** Initial temperature (0 for default value -> ' + str(DEFAULT_TEMP) + ')')
            temp = CLI.__get_usr_input()
            temp = temp if temp != 0 else DEFAULT_TEMP

            print('** Initial cooling (0 for default value -> ' + str(DEFAULT_COOLING) + ')')
            cooling = CLI.__get_usr_float()
            cooling = cooling if cooling != 0 else DEFAULT_COOLING

            solver = SimulatedAnnealing(
                CLI.procs,
                CLI.data,
                DEFAULT_MAX_IT,
                temp_init=temp,
                cooling=cooling
            )

        print('\n=== SEARCH')
        rand = CLI.__get_usr_random()
        CLI.sol = solver.search(rand)

        print('\n=== FOUND')
        print('** Went through ' + str(solver.get_iterations() - 1) + ' iteration.s')
        CLI.__show_solution()

    @staticmethod
    def __print_config():
        """
        :return:
        """
        print('\n=== ENVIRONMENT')
        print('** procs: ' + str(CLI.procs))
        print('** data : ' + str(CLI.data))

    @staticmethod
    def start():
        """
        :return:
        """
        print(USR_INTRO)

        print('=== CONFIGURATION')
        CLI.procs = CLI.__specify_processes_number()
        CLI.data = CLI.__specify_data()

        CLI.__print_config()
        CLI.__show_menu()
