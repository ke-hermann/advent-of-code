import re

def overlap(xlow, xhigh, ylow, yhigh):
    pass

with open("./day22.in", "r") as infile:
    data = infile.read().splitlines()
    ranges = []
    finished = []

    for line in data: 
        xs = re.findall("-?\d+", line)
        cubes  = [int(x) for x in xs]  
        ranges.append(cubes)

    cur = ranges.pop()
    for (xl, xh, yl, yh, zl, zh) in ranges:
        
