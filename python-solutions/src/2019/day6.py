from collections import defaultdict


def orbits(planet):
    q = [planet]
    visited = []
    while q:
        n = q.pop()
        for x in adjacent[n]:
            if x not in visited:
                q.append(x)
        visited.append(n)
    return visited

        
with open("../../resources/2019/day6.txt", "r") as infile:
    data = infile.read().splitlines()
    adjacent = defaultdict(list)
    for line in data:
        source, target = line.split(")")
        adjacent.setdefault(source, []).append(target)

    total = 0
    ks = list(adjacent.keys())
    for k in ks:
        r = orbits(k)
        total += len(r) - 1
    print(total)
            

