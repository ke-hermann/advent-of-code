import re
from itertools import product
from collections import deque

def neighbors(n):
    (x, y, z) = n
    return [
        (x + 1, y, z),
        (x - 1, y, z),
        (x, y + 1, z),
        (x, y - 1, z),
        (x, y, z + 1),
        (x, y, z - 1),
    ]


with open("resources/2022/day18.txt", "r") as infile:
    data = infile.read().splitlines()
    cubes = []
    for line in data:
        (x, y, z) = [int(x) for x in re.findall("\d+", line)]
        cubes.append((x, y, z))


def surface_area(bubbles):
    open_sides = 0
    for p in cubes:
        open_sides += len([x for x in neighbors(p) if x not in cubes and x not in bubbles])

    return open_sides


def flood_fill():
    start = (-1, -1, -1)
    queue = deque([start])
    visited = [start]
    LOWER = -1
    UPPER = 25

    while queue:
        n = queue.pop()
        (x, y, z) = n
        for p in neighbors(n):
            (a, b, c) = p
            if (
                LOWER <= a <= UPPER
                    and LOWER <= b <= UPPER
                    and LOWER <= c <= UPPER
                    and p not in visited
                    and p not in cubes
            ):
                queue.append((p))
        visited.append(n)

    return visited



print(surface_area([]))
visited = flood_fill()
count = 0
for c in cubes:
    n = neighbors(c)
    count += sum(x in visited for x in n)
print(count)

