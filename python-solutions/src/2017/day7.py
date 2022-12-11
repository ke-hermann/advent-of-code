import re 
from itertools import chain

with open("resources/2017/day7.txt", "r") as infile:
    data = infile.read().splitlines()
    weights = {}
    graph = {}
    for line in data:
        disks = re.findall("[a-z]{1,}", line)
        weight = int(re.findall("\d+", line)[0])
        graph[disks[0]] = disks[1:]
        weights[disks[0]] = weight

def disk_weight(disk):
    children = graph[disk]
    yield weights[disk]
    for c in children:
        yield from disk_weight(c)


# find root 
all_nodes = set(chain(graph.keys(), *graph.values()))
print([x for x in all_nodes if x not in chain(*graph.values())])

# part 2 
print(weights["sphbbz"] - 9)
