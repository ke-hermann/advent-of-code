from collections import defaultdict


def orbits(planet, target=None):
    q = [planet]
    visited = []
    while q:
        n = q.pop()

        if n == target:
            return visited

        for x in adjacent[n]:
            if x not in visited:
                q.append(x)
        visited.append(n)
    return visited


def paths(cur, target, seen):
    if cur == target:
        yield len(seen)
    for n in adjacent[cur]:
        if n not in seen:
            yield from paths(n, target, seen | {cur})


with open("../../resources/2019/day6.txt", "r") as infile:
    data = infile.read().splitlines()
    adjacent = defaultdict(list)
    for line in data:
        source, target = line.split(")")
        adjacent.setdefault(source, []).append(target)
        adjacent.setdefault(target, []).append(source)

    ps = paths("YOU", "SAN", set())
    print(min(ps) - 2)
