from math import atan2, sqrt


def angle(x1, y1, x2, y2):
    return atan2(y2 - y1, x2 - x1)


def line_of_sight(x, y):
    los = {}
    for i, j in asteroids:
        if (x, y) == (i, j):
            continue
        a = angle(x, y, i, j)
        los.setdefault(a, []).append([(x, y), (i, j)])
    return los


def distance(x1, y1, x2, y2):
    return sqrt((x2 - x1) ** 2 + (y2 - y1) ** 2)


def sort_asteroids(x, y):
    right = [p for p in asteroids if p[1] >= y]
    left = [p for p in asteroids if p[1] < y]
    right.sort(
        key=lambda p: angle(x, y, p[0], p[1]),
        reverse=True,
    )
    left.sort(key=lambda p: angle(x, y, p[0], p[1]), reverse=True)
    clockwise = right + left

    groups = {}
    for p in clockwise:
        a = angle(x, y, p[0], p[1])
        groups.setdefault(a, []).append(p)

    for k in groups.keys():
        xs = groups[k]
        xs.sort(key=lambda p: distance(x, y, p[0], p[1]))

    for i, (k, v) in enumerate(groups.items()):
        print(i, v)


with open("./day10.txt", "r") as infile:
    asteroids = set()
    data = infile.read().splitlines()
    for i, line in enumerate(data):
        for j, v in enumerate(line):
            if v == "#":
                asteroids.add((i, j))


(x, y) = max(asteroids, key=lambda p: len(line_of_sight(p[0], p[1])))
print("part1:", len(line_of_sight(x, y)))

print(x, y)
sort_asteroids(x, y)
