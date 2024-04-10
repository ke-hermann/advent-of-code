def left(x, y):
    blocking = []
    for (i, j), h in trees.items():
        if x == i and j < y:
            if h >= trees[(x, y)]:
                blocking.append(j)
    if not blocking:
        return y
    else:
        return min([abs(p - y) for p in blocking])


def right(x, y):
    blocking = []
    for (i, j), h in trees.items():
        if x == i and j > y:
            if h >= trees[(x, y)]:
                blocking.append(j)
    if not blocking:
        return Y_MAX - y
    else:
        return min([abs(p - y) for p in blocking])


def top(x, y):
    blocking = []
    for (i, j), h in trees.items():
        if y == j and i < x:
            if h >= trees[(x, y)]:
                blocking.append(i)
    if not blocking:
        return x
    else:
        return min([abs(p - x) for p in blocking])


def bottom(x, y):
    blocking = []
    for (i, j), h in trees.items():
        if y == j and i > x:
            if h >= trees[(x, y)]:
                blocking.append(i)
    if not blocking:
        return X_MAX - x
    else:
        return min([abs(p - x) for p in blocking])


def scenic_score(x, y):
    if x == 0 or y == 0 or x == X_MAX or y == Y_MAX:
        return 0
    else:
        return left(x, y) * right(x, y) * bottom(x, y) * top(x, y)


with open("./day8.txt", "r") as infile:
    data = infile.read().splitlines()
    trees = {}


for i, row in enumerate(data):
    for j, t in enumerate(row):
        trees[(i, j)] = int(t)

X_MAX = max(x[0] for x in trees.keys())
Y_MAX = max(x[1] for x in trees.keys())

# part 1
scores = []
for x, y in trees.keys():
    scores.append(scenic_score(x, y))

print(max(scores))
