from copy import deepcopy
from math import exp
from random import choice, random

from algorithms.optimizer import Optimizer


DEFAULT_TEMP    = 100
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

        return best_sol

    def stop_search(self):
        """
        :return:
        """
        self._iteration += 1
        return self._max_it < self._iteration or self.__temp < 0.1


if __name__ == '__main__':
    solver = SimulatedAnnealing(3, [5, 2, 6, 1, 7], 10000, DEFAULT_TEMP, DEFAULT_COOLING)
    print(solver.search(True))
