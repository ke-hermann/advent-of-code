import math
import networkx as nx
from itertools import combinations


def euclidean_distance_3d(point1, point2):
    return math.sqrt(
        (point1[0] - point2[0]) ** 2 +
        (point1[1] - point2[1]) ** 2 +
        (point1[2] - point2[2]) ** 2
    )

with open("day8.txt", "r") as infile:
    data = infile.read().splitlines()
    points = [tuple([int(x) for x in s.split(",")]) for s in data]
    pairs = list(combinations(points, 2))
    pairs.sort(key=lambda p: euclidean_distance_3d(p[0], p[1]))

    G = nx.Graph()
    G.add_edges_from(pairs[:1000])

    result = [len(g) for g in (nx.connected_components(G))]
    print(sorted(result, reverse=True)[:3])
    
    pairs = pairs[1000:]
    while True:
        a, b = pairs.pop(0)
        G.add_edge(a, b)
        if len(list(nx.connected_components(G))) == 1:
            print(a, b)
            break

