with open("./resources/2020/day3.txt", "r") as infile:
    data = infile.read().splitlines()
    grid = {}
    rows = len(data)
    cols = len(data[0]) 
    for i in range(rows):
        for j in range(cols):
            grid[(i, j)] = data[i][j]


deltas = [(1, 1), (1, 3), (1, 5), (1, 7), (2, 1)]
results = 1

for (dx, dy) in deltas:
    x, y = (0, 0)
    trees = 0
    while x < len(data) - 1:
        x += dx
        y += dy
        trees += grid[(x, y % cols)] == "#"

    results *= trees

print(results)
