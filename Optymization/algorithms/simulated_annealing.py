from copy import deepcopy
from math import exp
from random import choice, random

from algorithms.optimizer import Optimizer, DEFAULT_MAX_IT

DEFAULT_TEMP    = 10
DEFAULT_COOLING = .99


class SimulatedAnnealing(Optimizer):
    """
    """

    def __init__(self, nb_proc: int, data: list, max_it: int,
                 temp_init: float, cooling: float):
        super().__init__(nb_proc, data, max_it)

        self.__temp = temp_init
        self.__cooling = cooling

    def __acceptance(self, best_cost: int, new_cost: int) -> float:
        # if the new solution is better
        if new_cost < best_cost:
            return 1.

        return exp(-(new_cost - best_cost) / self.__temp)

    def search(self, rng: bool) -> dict:
        """
        """
        # s0
        current_sol = self.gen_s0(rng)
        # s*
        best_sol = deepcopy(current_sol)
        # f(s*)
        best_time = self.c_max(best_sol)

        while not self.stop_search():
            current_sol = choice(self.get_neighbors(current_sol))
            local_time = self.c_max(current_sol)

            if self.__acceptance(best_time, local_time) > random():
                best_sol = deepcopy(current_sol)
                best_time = self.c_max(best_sol)

            self.__temp *= self.__cooling
            yield best_sol

        # return best_sol

    def stop_search(self):
        """
        :return:
        """
        self._iteration += 1
        return self._max_it < self._iteration or self.__temp < 0.1


if __name__ == '__main__':
    solver = SimulatedAnnealing(
        5,
        [7, 14, 2, 1, 8, 15, 8, 4, 5, 9, 7, 3, 15, 11, 8, 5, 6, 9, 2, 15, 3, 13, 5, 11, 13, 9, 14, 3, 2, 7],
        DEFAULT_MAX_IT,
        DEFAULT_TEMP,
        DEFAULT_COOLING)

    for sol in solver.search(True):
        print('\n---------------------')
        print('** CMax: ' + str(Optimizer.c_max(sol)))
        for key, values in sol.items():
            print(
                '**\t' + str(key) + ': ' + str(values)
                + '\n**\t\ttotal: ' + str(sum(values))
            )
