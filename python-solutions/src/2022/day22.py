TURN_L = {"L": "D", "D": "R", "R": "U", "U": "L"}
TURN_R = {"L": "U", "D": "L", "R": "D", "U": "R"}

from copy import deepcopy


def walk_edges(x, y, fcg):
    print(x, y, fcg)
    if 150 <= x <= 199 and 0 <= y <= 49: # surface 1 
        if fcg == "R":
            return (149, (x - 150) + 50, "U")
        elif  fcg == "D":
            return (0, y + 100, "D")
        elif fcg == "L":
            return (0, (x - 150) + 50, "D")
    elif 100 <= x <= 149 and 0 <= y <= 49: # surface 2 
        if fcg == "L":
            return (49 - (x - 100), 50, "R")
        elif fcg == "U":
            return (y + 50, 50, "R")
    elif 100 <= x <= 149 and 50 <= y <= 99: # surface 3
        if fcg == "D":
            return ((y - 50) + 150, 49, "L")
        elif fcg == "R":
            x_off = x - 100
            return (49 - x_off, 149, "L")
    elif 50 <= x <= 99 and 50 <= y <= 99: # surface 4 
        if fcg == "L":
            return (100 ,(x - 50), "D")
        elif fcg == "R":
            return (49 , (x - 50) + 100, "U")
    elif 0 <= x <= 49 and 50 <= y <= 99: # surface 5
        if fcg == "L":
            return (149 - x , 0, "R")
        elif fcg == "U":
            return (150 + (y - 50), 0, "R")
    elif 0 <= x <= 49 and 100 <= y <= 149: # surface 6
        if fcg == "U":
            return (199, y - 100, "U")
        elif fcg == "R":
            return (149 - x, 99, "L")
        elif fcg == "D":
            return ((y - 100) + 50, 99, "L")


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
                        x_new, y_new, fcg = walk_edges(x, y, facing)
                        cnd = (x_new, y_new)
                        if board[cnd] == ".":
                            x , y = cnd[0], cnd[1]
                            facing = fcg
                    elif board[(x - 1, y)] == ".":
                        x -= 1
                case "D":
                    if (x + 1, y) not in board:
                        x_new, y_new, fcg = walk_edges(x, y, facing)
                        cnd = (x_new, y_new)
                        if board[cnd] == ".":
                            x , y = cnd[0], cnd[1]
                            facing = fcg
                    elif board[(x + 1, y)] == ".":
                        x += 1
                case "L":
                    if (x, y - 1) not in board:
                        x_new, y_new, fcg = walk_edges(x, y, facing)
                        cnd = (x_new, y_new)
                        if board[cnd] == ".":
                            x , y = cnd[0], cnd[1]
                            facing = fcg
                    elif board[(x, y - 1)] == ".":
                        y -= 1
                case "R":
                    if (x, y + 1) not in board:
                        x_new, y_new, fcg = walk_edges(x, y, facing)
                        cnd = (x_new, y_new)
                        if board[cnd] == ".":
                            x , y = cnd[0], cnd[1]
                            facing = fcg
                    elif board[(x, y + 1)] == ".":
                        y += 1

        facing = TURN_L[facing] if c == "L" else TURN_R[facing]

x += 1
y += 1 
print(x * 1000 + 4 * y + {"R": 0, "D": 1, "L": 2, "U": 3}[facing])
