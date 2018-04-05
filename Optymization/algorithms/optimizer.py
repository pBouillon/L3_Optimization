import copy
from abc import ABC
from abc import abstractmethod

from random import randint


DEFAULT_MAX_IT = 500


class Optimizer(ABC):
    """
    """

    def __init__(self, nb_proc: int, data: list, max_it: int):
        self._nb_proc = nb_proc
        self._data = data

        self._max_it = max_it
        self._iteration = 0

    @staticmethod
    def c_max(solution: dict) -> int:
        """
        :param solution:
        :return:
        """
        max_time = 0

        for repartition in solution.values():
            if max_time < sum(repartition):
                max_time = sum(repartition)

        return max_time

    def gen_s0(self, rng: bool) -> dict:
        """
        :return:
        """
        s0 = {}
        self._iteration = 0

        for i in range(self._nb_proc):
            s0[i] = []

        if not rng:
            s0[0] = self._data

        else:
            for i in range(len(self._data)):
                proc = randint(0, self._nb_proc - 1)
                s0[proc].append(self._data[i])

        return s0

    def get_neighbors(self, state: dict) -> list:
        """
        :return:
        """
        neighbors = []

        for proc_source in range(self._nb_proc):
            for task_id in state[proc_source]:
                for proc_dest in range(self._nb_proc):

                    if proc_source == proc_dest:
                        continue

                    successor = copy.deepcopy(state)
                    successor[proc_source].remove(task_id)
                    successor[proc_dest].append(task_id)

                    if successor not in neighbors:
                        neighbors.append(successor)

        return neighbors

    def set_max_it(self, new_max: int):
        """
        :param new_max:
        :return:
        """
        self._max_it = new_max

    def get_iterations(self) -> int:
        """
        :return:
        """
        return self._iteration

    @abstractmethod
    def search(self, rng: bool):
        """
        :return:
        """
        pass

    @abstractmethod
    def stop_search(self):
        """
        :return:
        """
        pass
