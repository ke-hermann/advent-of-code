import networkx as nx
import networkx.algorithms.approximation as nx_app
from itertools import permutations

with open("./day24.txt", "r") as infile:
    data = infile.read().splitlines()
    G = nx.Graph()
    poi = []

    for i, line in enumerate(data):
        for j, v in enumerate(line):
            if v in ".0123456789":
                G.add_node((i, j))
            if v in "0123456789":
                poi.append((i, j))
            for (x, y) in [(0, -1), (0, 1), (1, 0), (-1, 0)]:
                (m, n) = (i+x, j + y)
                try:
                    if data[m][n] in ".0123456789":
                        G.add_edge((i, j), (m, n))
                except Exception:
                    pass
