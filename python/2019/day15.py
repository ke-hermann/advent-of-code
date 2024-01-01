import networkx as nx
from virtualmachine import VirtualMachine
from copy import deepcopy


class InvalidStateException(Exception):
    pass


NORTH = 1
SOUTH = 2
WEST = 3
EAST = 4


def next_field(x, y, direction):
    if direction == NORTH:
        return (x - 1, y)
    elif direction == SOUTH:
        return (x + 1, y)
    elif direction == WEST:
        return (x, y - 1)
    elif direction == EAST:
        return (x, y + 1)

    raise InvalidStateException


class Droid:
    def __init__(self, program):
        self.x = 0
        self.y = 0
        self.vm = VirtualMachine(program)
        self.hit_wall = False

    def move(self, direction):
        global oxygen
        global walls
        global visited

        visited.add((self.x, self.y))

        self.vm.input = [direction]
        self.vm.run_until_out()
        response = self.vm.output.pop()
        p = next_field(self.x, self.y, direction)

        if response == 0:
            self.hit_wall = True
            walls.add(p)
        elif response == 1:
            self.x = p[0]
            self.y = p[1]
        elif response == 2:
            self.x = p[0]
            self.y = p[1]
            oxygen = p


with open("./day15.txt", "r") as infile:
    data = infile.read().strip().split(",")
    program = [int(x) for x in data]
    oxygen = (0, 0)
    visited = set()
    G = nx.Graph()

    droids = [Droid(program[:])]
    walls = set()
    while droids:
        d = droids.pop(0)
        if d.hit_wall:
            continue
        if (d.x, d.y) in visited:
            continue
        for direction in [NORTH, SOUTH, WEST, EAST]:
            d_new = deepcopy(d)
            d_new.move(direction)
            droids.append(d_new)

    for x, y in visited:
        G.add_node((x, y))
        for i, j in [(1, 0), (-1, 0), (0, -1), (0, 1)]:
            m, n = x + i, y + j
            if (m, n) in visited:
                G.add_edge((x, y), (m, n))

    p = nx.shortest_path_length(G, (0, 0), oxygen)
    print(p)

    # part 2
    steps = 0
    for n in visited:
        paths = nx.all_simple_paths(G, oxygen, n)
        for p in paths:
            if len(p) > steps:
                steps = len(p)

    print(steps - 1)
