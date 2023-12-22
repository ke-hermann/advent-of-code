import re


def simulate(bricks: list, floor: set):
    fallen = []
    while bricks:
        b = bricks.pop(0)
        while not any(z == 1 for (_, _, z) in b) and not any(
            (x, y, z - 1) in floor for (x, y, z) in b
        ):
            b = [(x, y, z - 1) for (x, y, z) in b]

        fallen.append(b)
        floor.update(b)
    return fallen


def test_safety(bricks, b):
    i = bricks.index(b)
    floor = set([x for brick in bricks[:i] for x in brick if x not in b])
    xs = [brick for brick in bricks if brick != b]
    result = simulate(xs[:], floor)
    return result == xs


with open("./day22.in", "r") as infile:
    data = infile.read().splitlines()
    bricks = []
    xs = []
    for line in data:
        xs.append([int(x) for x in re.findall(r"\d+", line)])

    xs.sort(key=lambda b: b[2])
    for x, y, z, x0, y0, z0 in xs:
        coords = []
        for i in range(x, x0 + 1):
            coords.append((i, y, z))
        for j in range(y, y0 + 1):
            coords.append((x, j, z))
        for m in range(z, z0 + 1):
            coords.append((x, y, m))
        bricks.append(set(coords))

    safe = 0
    fallen = simulate(bricks, set())
    for b in fallen:
        safe += test_safety(fallen[:], b)
    print(safe)
