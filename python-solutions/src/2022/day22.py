TURN_L = {"L": "D", "D": "R", "R": "U", "U": "L"}
TURN_R = {"L": "U", "D": "L", "R": "D", "U": "R"}

from copy import deepcopy


with open("resources/2022/day22_moves.txt", "r") as moves_file:
    # had to manually add zero move section to input due to scuffed parsing
    # further down below
    MOVES = moves_file.read().strip()

with open("resources/2022/day22.txt", "r") as infile:
    data = infile.read().splitlines()
    board = {}
    facing = "R"

    for i, row in enumerate(data):
        for j, v in enumerate(row):
            if v in ["#", "."]:
                board[(i, j)] = v

    x, y = sorted([p for p in board if board[p] == "."])[0]

    board_cp = deepcopy(board)


instruction = []
for c in MOVES:
    if c not in "LR":
        instruction.append(c)
    else:
        steps = int("".join(instruction))
        instruction = []

        for _ in range(steps):
            board_cp[(x, y)] = {"R": ">", "D": "v", "L": "<", "U": "^"}[facing]
            match facing:
                case "U":
                    if (x - 1, y) not in board:
                        cnd = sorted([(i, j) for (i, j) in board.keys() if j == y])[-1]
                        if board[cnd] == ".":
                            x , y = cnd[0], cnd[1]
                    elif board[(x - 1, y)] == ".":
                        x -= 1
                case "D":
                    if (x + 1, y) not in board:
                        cnd = sorted([(i, j) for (i, j) in board.keys() if j == y])[0]
                        if board[cnd] == ".":
                            x , y = cnd[0], cnd[1]
                    elif board[(x + 1, y)] == ".":
                        x += 1
                case "L":
                    if (x, y - 1) not in board:
                        cnd = sorted([(i, j) for (i, j) in board.keys() if i == x])[-1]
                        if board[cnd] == ".":
                            x , y = cnd[0], cnd[1]
                    elif board[(x, y - 1)] == ".":
                        y -= 1
                case "R":
                    if (x, y + 1) not in board:
                        cnd = sorted([(i, j) for (i, j) in board.keys() if i == x])[0]
                        if board[cnd] == ".":
                            x , y = cnd[0], cnd[1]
                        
                    elif board[(x, y + 1)] == ".":
                        y += 1

        facing = TURN_L[facing] if c == "L" else TURN_R[facing]

x += 1
y += 1 
print(x * 1000 + 4 * y + {"R": 0, "D": 1, "L": 2, "U": 3}[facing])
