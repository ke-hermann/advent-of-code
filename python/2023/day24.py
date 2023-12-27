import re
from dataclasses import dataclass

# (x, y) = 
@dataclass 
class Hailstone:
    px: int
    py: int
    pz: int
    vx: int 
    vy: int 
    vz: int

with open("./day24.in", "r") as infile:
    data = infile.read().splitlines()
    for line in data:
        vals = [int(x) for x in re.findall(r"-?\d+", line)]
        hailstone = Hailstone(*vals)
        print(hailstone)
