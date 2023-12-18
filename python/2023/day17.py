import networkx as nx
from pprint import pprint

UP = 0
DOWN = 1
RIGHT = 2
LEFT = 3


def reachable(x, y, f, s):
    if f == UP:
        if s == 2:
            return [(x, y - 1, LEFT, 0), (x, y + 1, RIGHT, 0)]
        else:
            return [(x, y - 1, LEFT, 0), (x, y + 1, RIGHT, 0), (x - 1, y, UP, s + 1)]
    elif f == DOWN:
        if s == 2:
            return [(x, y - 1, LEFT, 0), (x, y + 1, RIGHT, 0)]
        else:
            return [(x, y - 1, LEFT, 0), (x, y + 1, RIGHT, 0), (x + 1, y, DOWN, s + 1)]
    elif f == LEFT:
        if s == 2:
            return [(x - 1, y, UP, 0), (x + 1, y, DOWN, 0)]
        else:
            return [(x - 1, y, UP, 0), (x + 1, y, DOWN, 0), (x, y - 1, LEFT, s + 1)]
    elif f == RIGHT:
        if s == 2:
            return [(x - 1, y, UP, 0), (x + 1, y, DOWN, 0)]
        else:
            return [(x - 1, y, UP, 0), (x + 1, y, DOWN, 0), (x, y + 1, RIGHT, s + 1)]


def reachable_p2(x, y, f, s):
    if f == UP:
        if s < 3:
            return [(x - 1, y, UP, s + 1)]
        elif 3 <= s < 9:
            return [(x, y - 1, LEFT, 0), (x, y + 1, RIGHT, 0), (x - 1, y, UP, s + 1)]
        else:
            return [(x, y - 1, LEFT, 0), (x, y + 1, RIGHT, 0)]
    elif f == DOWN:
        if s < 3:
            return [(x + 1, y, DOWN, s + 1)]
        elif 3 <= s < 9:
            return [(x, y - 1, LEFT, 0), (x, y + 1, RIGHT, 0), (x + 1, y, DOWN, s + 1)]
        else:
            return [(x, y - 1, LEFT, 0), (x, y + 1, RIGHT, 0)]

    elif f == LEFT:
        if s < 3:
            return [(x, y - 1, LEFT, s + 1)]
        elif 3 <= s < 9:
            return [(x - 1, y, UP, 0), (x + 1, y, DOWN, 0), (x, y - 1, LEFT, s + 1)]
        else:
            return [(x - 1, y, UP, 0), (x + 1, y, DOWN, 0)]
    elif f == RIGHT:
        if s < 3:
            return [(x, y + 1, RIGHT, s + 1)]
        elif 3 <= s < 9:
            return [(x - 1, y, UP, 0), (x + 1, y, DOWN, 0), (x, y + 1, RIGHT, s + 1)]
        else:
            return [(x - 1, y, UP, 0), (x + 1, y, DOWN, 0)]


with open("./day17.txt", "r") as infile:
    data = infile.read().splitlines()
    ROWS = len(data)
    COLS = len(data[0])

    lavamap = nx.DiGraph()
    starts = [(0, 0, RIGHT, 0), (0, 0, DOWN, 0)]
    ends = []

    for x, row in enumerate(data):
        for y, val in enumerate(row):
            cost = int(val)
            for facing in [UP, DOWN, RIGHT, LEFT]:
                for steps in range(11):
                    n = (x, y, facing, steps)

                    if (
                        x == ROWS - 1
                        and y == COLS - 1
                        and steps >= 3
                        and (facing == DOWN or facing == RIGHT)
                    ):
                        ends.append((x, y, facing, steps))
                    lavamap.add_node(n)

                    for p in reachable_p2(x, y, facing, steps):
                        if p[0] < 0 or p[0] >= ROWS or p[1] < 0 or p[1] >= COLS:
                            continue
                        lavamap.add_edge(n, p, cost=cost)

    path_lengths = []
    for p in starts:
        for t in ends:
            try:
                d = nx.shortest_path_length(lavamap, p, t, "cost")
                d += int(data[ROWS - 1][COLS - 1])
                d -= int(data[0][0])
                print(d)
                path_lengths.append(d)
            except Exception:
                pass

    print(min(path_lengths))
