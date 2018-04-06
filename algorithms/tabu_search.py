from algorithms.optimizer import Optimizer
from copy import deepcopy
from sys import maxsize


class TabuSearch(Optimizer):

    def __init__(self, nb_proc: int, data: list, max_it: int, tabu_size: int):
        super().__init__(nb_proc, data, max_it)
        self.__tabu_size = tabu_size
        self.__tabus = []

    def search(self, rng: bool) -> dict:
        """
        :return:
        """
        # s0
        current_sol = self.gen_s0(rng)
        # s*
        best_sol    = deepcopy(current_sol)
        # f(s*)
        best_time = self.c_max(best_sol)

        best_neighbor = {}

        while not self.stop_search():
            # neighbors handing
            best_neighbor_time = maxsize
            for n in self.get_neighbors(current_sol):
                if n in self.__tabus:
                    continue

                local_time = self.c_max(n)
                if local_time <= best_neighbor_time:
                    best_neighbor = n
                    best_neighbor_time = local_time

            # setting best neighbor ar current solution
            current_sol = best_neighbor

            # checking if the solution is faster
            local_time = self.c_max(current_sol)
            if local_time <= best_time:
                print('choosen: {} <= {}'.format(local_time, best_time))
                print_dict(current_sol)
                print('-------------------')
                best_sol  = deepcopy(current_sol)
                best_time = local_time

            # adding to tabus
            self.__tabus.append(deepcopy(current_sol))

            # reducing tabus
            if len(self.__tabus) > self.__tabu_size:
                self.__tabus.remove(
                    self.__tabus[::-1][0]
                )

        yield best_sol
        # return best_sol

    def stop_search(self) -> bool:
        """
        :return:
        """
        self._iteration += 1
        return self._max_it < self._iteration


def print_dict(d):
    for key, values in d.items():
        print(
            '**\t' + str(key) + ': ' + str(values)
            + '\n**\t\ttotal: ' + str(sum(values))
        )

if __name__ == '__main__':
    solver = TabuSearch(
        3,
        [5, 7, 6, 2, 3, 1],
        100,
        100
    )

    i = 0
    for sol in solver.search(True):
        # print('\n--------------------- ' + str(i))
        # print('** CMax: ' + str(Optimizer.c_max(sol)))
        # for key, values in sol.items():
        #     print(
        #         '**\t' + str(key) + ': ' + str(values)
        #         + '\n**\t\ttotal: ' + str(sum(values))
        #     )
        # i += 1
        pass
