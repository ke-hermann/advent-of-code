with open("./resources/2020/day3.txt", "r") as infile:
    data = infile.read().splitlines()
    grid = {}
    rows = len(data)
    cols = len(data[0]) 
    for i in range(rows):
        for j in range(cols):
            grid[(i, j)] = data[i][j]


x, y = (0, 0)
trees = 0
while x < len(data) - 1:
    x += 1
    y += 3
    trees += grid[(x, y % cols)] == "#"

print(trees)
