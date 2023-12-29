from copy import deepcopy
from collections import defaultdict

from utils import pprint_dict


def neighbors(x, y):
    xs = []
    for i in [-1, 0, 1]:
        for j in [-1, 0, 1]:
            if (i, j) == (0, 0):
                continue
            xs.append((x + i, y + j))
    return xs


with open("./day18.txt", "r") as infile:
    data = infile.read().splitlines()
    lumber_area = defaultdict(str)
    for i, line in enumerate(data):
        for j, val in enumerate(line):
            lumber_area[(i, j)] = val

    ks = list(lumber_area.keys())
    for i in range(1000):
        la_new = deepcopy(lumber_area)
        for x, y in ks:
            v = lumber_area[(x, y)]
            xs = [lumber_area[p] for p in neighbors(x, y)]
            trees = len([c for c in xs if c == "|"])
            fields = len([c for c in xs if c == "."])
            yards = len([c for c in xs if c == "#"])

            if v == "." and trees >= 3:
                la_new[(x, y)] = "|"
            elif v == "|" and yards >= 3:
                la_new[(x, y)] = "#"
            elif v == "#" and (yards == 0 or trees == 0):
                la_new[(x, y)] = "."

        lumber_area = la_new

        trees = len([c for c in lumber_area.values() if c == "|"])
        yards = len([c for c in lumber_area.values() if c == "#"])
        print(i, trees * yards)
