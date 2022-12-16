import re
from itertools import permutations
import networkx as nx

with open("resources/2022/day16.txt", "r") as infile:
    data = infile.read().splitlines()
    flows = {}
    distances = {}
    G = nx.Graph()

    for line in data:
        valves = re.findall("[A-Z]{2}", line)
        for a in valves[1:]:
            G.add_edge(valves[0], a)

        flow = int(re.findall("\d+", line)[0])
        flows[valves[0]] = flow

        non_zero = set([k for k in flows.keys() if flows[k] != 0])
        for (a, b) in permutations(non_zero | {"AA"}, 2):
            try:
                d = len(nx.shortest_path(G, a, b))
                distances[(a, b)] = d
            except:
                pass


def traverse(valve, opened, edges, time=0):
    if time >= 26 or not edges:
        yield {**opened,  **{valve: time}}

    else:
        for e in edges:
            yield from traverse(
                e,
                {**opened,  **{valve: time}},
                edges - {e},
                time=time + distances[(valve, e)],
            )


results = traverse("AA", {}, non_zero)
limit = 0
for l in results:
    total = 0
    elephant = traverse("AA", l, non_zero - set(l.keys()))
    for e in elephant:
        for k, v in e.items():
            for i in range(v, 26):
                total += flows[k]
        if total >= limit:
            limit = total 
print(limit)
