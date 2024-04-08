def left(x, y):
    for (i, j), h in trees.items():
        if j == y and i < x:
            if h >= trees[(x, y)]:
                return False
    return True


def right(x, y):
    for (i, j), h in trees.items():
        if j == y and i > x:
            if h >= trees[(x, y)]:
                return False
    return True


def top(x, y):
    for (i, j), h in trees.items():
        if i == x and j < y:
            if h >= trees[(x, y)]:
                return False
    return True


def bottom(x, y):
    for (i, j), h in trees.items():
        if i == x and j > y:
            if h >= trees[(x, y)]:
                return False
    return True


def visible(x, y):
    if x == 0 or y == 0 or x == X_MAX or y == Y_MAX:
        return True
    else:
        return left(x, y) or right(x, y) or bottom(x, y) or top(x, y)


with open("./day8.txt", "r") as infile:
    data = infile.read().splitlines()
    trees = {}


for i, row in enumerate(data):
    for j, t in enumerate(row):
        trees[(i, j)] = int(t)

X_MAX = max(x[0] for x in trees.keys())
Y_MAX = max(x[1] for x in trees.keys())

# part 1
total = 0
for x, y in trees.keys():
    total += visible(x, y)

print(total)
