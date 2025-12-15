import re
from z3 import *

def solve(ax, ay, bx, by, px, py):
    # Create integer variables (Mathematical integers, not C++ ints)
    a = Int('a')
    b = Int('b')
    
    s = Solver()
    
    # Add constraints
    s.add(a * ax + b * bx == px)
    s.add(a * ay + b * by == py)
    
    # Add non-negative constraints
    s.add(a >= 0)
    s.add(b >= 0)
    
    if s.check() == sat:
        m = s.model()
        return m[a].as_long(), m[b].as_long()
    else:
        return None

with open("day13.txt", "r") as infile:
    data = infile.read().split("\n\n")
    total = 0
    for entry in data:
        lines = entry.splitlines()
        ax, ay = [int(x) for x in re.findall(r"\d+", lines[0])]
        bx, by = [int(x) for x in re.findall(r"\d+", lines[1])]
        px, py = [int(x) for x in re.findall(r"\d+", lines[2])]
        px += 10000000000000
        py += 10000000000000

        result = solve(ax, ay, bx, by, px , py)
        if result:
            a, b = result
            total += 3 * a + b
    print(total)




