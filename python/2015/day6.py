import re
from collections import defaultdict


with open("./day6.txt", "r") as infile:
    data = infile.read().splitlines()
    d = defaultdict(int)

    for line in data:
        xs = re.findall(r"\d+", line)
        x1, y1, x2, y2 = [int(x) for x in xs]
        for i in range(x1, x2 + 1):
            for j in range(y1, y2 + 1):
                if "turn on" in line:
                    d[(i, j)] += 1
                elif "turn off" in line:
                    d[(i, j)] = 0 if d[(i, j)] <= 1 else d[(i, j)] - 1
                elif "toggle" in line:
                    d[(i, j)] += 2


    total = 0
    for v in d.values():
        total += v
    print(total)
                    
