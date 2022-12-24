from collections import deque  


def move_blizzard(pos, direction):
    (x, y) = pos
    match direction:
        case ">":
            return (x, 1) if y + 1 > COLS else (x, y + 1)
        case "<":
            return (x, COLS) if y - 1 < 1 else (x, y - 1)
        case "^":
            return (ROWS, y) if x - 1 < 1 else (x - 1, y)
        case "v":
            return (1, y) if x + 1 > ROWS else (x + 1, y)


with open("resources/2022/day24.txt", "r") as infile:
    data = infile.read().splitlines()
    blizzards = []
    ROWS = len(data) - 2
    COLS = len(data[0]) - 2
    GOAL = (ROWS, COLS)

    for x, row in enumerate(data):
        for y, v in enumerate(row):
            if data[x][y] in "><v^":
                blizzards.append((x, y))
    print(blizzards, ROWS, COLS)


stack = deque([(0, 1)])
while GOAL not in stack:
    x, y, = stack.popleft()
    blizzards = [move_blizzard(b) for b in blizzards]
    moves = [(x + i , y + j) for (i, j) in [(0, 1), (1, 0), (-1, 0), (0, -1), (0, 0)]]
    moves = [(x, y) for (x, y) in moves if 1 <= x <= ROWS and 1 <= y <= COLS]
    moves = [m for m in moves if m not in blizzards]