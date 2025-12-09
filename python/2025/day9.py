from itertools import combinations

def sq_size(a, b):
    return (1 + abs(a[0] - b[0])) * (1 + abs(a[1] - b[1]))

with open("day9.txt", "r") as infile:
    data = infile.read().splitlines()
    squares = []
    tiles = []

    for line in data:
        a, b = [int(x) for x in line.split(",")]
        tiles.append((a, b))

    for a, b in combinations(tiles, 2):
        squares.append(sq_size(a, b))

    print(max(squares))

