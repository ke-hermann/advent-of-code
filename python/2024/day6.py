from collections import defaultdict
from copy import deepcopy

class Guard:
    def __init__(self, x, y):
       self.x = x
       self.y = y
       self.direction = "U"

    def turn(self):
        match self.direction:
            case "U":
                self.direction = "R"
            case "R":
                self.direction = "D"
            case "D":
                self.direction = "L"
            case "L":
                self.direction = "U"
       
    def peak(self):
        match self.direction:
            case "U":
                if D[(self.x - 1, self.y)] == "#":
                    self.turn()
            case "R":
                if D[(self.x, self.y + 1)] == "#":
                    self.turn()
            case "D":
                if D[(self.x + 1, self.y)] == "#":
                    self.turn()
            case "L":
                if D[(self.x, self.y - 1)] == "#":
                    self.turn()

    def move(self):
        match self.direction:
            case "U":
                self.x -= 1
            case "D":
                self.x += 1
            case "L":
                self.y -= 1
            case "R":
                self.y += 1

def detect_loop(guard: Guard, grid):
    seen = set()
    while D[(guard.x, guard.y)] != "?":
        if (guard.x, guard.y, guard.direction) in seen:
            return True
        seen.add((guard.x, guard.y, guard.direction))
        guard.peak()
        guard.move()
    return False
        
   
with open("day6.txt", "r") as infile:
    data = infile.read().splitlines()
    D = defaultdict(lambda: "?")
    for i, row in enumerate(data):
        for j, cell in enumerate(row):
            D[(i, j)] = cell

    x, y = [k for k in D if D[k] == "^"][0]
    seen = set()
    guard = Guard(x, y)

    while D[(guard.x, guard.y)] != "?":
        seen.add((guard.x, guard.y))
        guard.peak()
        guard.move()

    print(len(seen))

    # part 2
    candidates = 0
    for (i, j) in seen:
        d = deepcopy(D)
        g = Guard(x, y)
        d[(i, j)] = "#"
        candidates += detect_loop(g, d)

    print(candidates)
        
