def move_blizzard(blizzard):
    (x, y, v) = blizzard
    match v:
        case ">":
            return (x, 1, v) if y + 1 > COLS else (x, y + 1, v)
        case "<":
            return (x, COLS, v) if y - 1 < 1 else (x, y - 1, v)
        case "^":
            return (ROWS, y, v) if x - 1 < 1 else (x - 1, y, v)
        case "v":
            return (1, y, v) if x + 1 > ROWS else (x + 1, y, v)


def traverse(pos, goal, i):
    global steps
    global blizzards_timed
    global seen

    seen.add((pos, i))

    if pos == goal:
        if i < steps:
            steps = i
        return

    if i > steps:
        return

    (x, y) = pos
    xs = [(x + i, y + j) for (i, j) in [(0, 1), (1, 0), (-1, 0), (0, -1), (0, 0)]]

    for m in xs:
        if (
            (m, i + 1) in seen
            or not (1 <= m[0] <= ROWS and 1 <= m[1] <= COLS or m == GOAL or m == (0, 1))
            or m in blizzards_timed[i]
        ):
            continue

        traverse(m, goal, i + 1)


with open("resources/2022/day24.txt", "r") as infile:
    data = infile.read().splitlines()
    blizzards = []
    ROWS = len(data) - 2
    COLS = len(data[0]) - 2
    GOAL = (ROWS + 1, COLS)
    START = (0, 1)

    for x, row in enumerate(data):
        for y, v in enumerate(row):
            if data[x][y] in "><v^":
                blizzards.append((x, y, v))

    # pre-calc blizzards
    blizzards_timed = {}
    for i in range(2000):
        blizzards = [move_blizzard(b) for b in blizzards]
        blizzards_timed[i] = set([(x, y) for (x, y, _) in blizzards])

steps = 1000
seen = set()
# traverse(GOAL, START, 343) // p1 answer and  intermediate solution
traverse(START, GOAL, 663)
print(steps)
