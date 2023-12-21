import re


with open("./day22.in", "r") as infile:
    data = infile.read().splitlines()
    cubes = {}
    for line in data: 
        xs = re.findall("-?\d+", line)
        xm, xh, ym, yh, zm, zh  = [int(x) for x in xs]

        for x in range(xm, xh + 1):
            for y in range(ym, yh + 1):
                for z in range(zm, zh + 1):
                    if "on" in line:
                        cubes[(x, y, z)] = 1
                    else:
                        cubes[(x, y, z)] = 0

    res = [k for k, v in  cubes.items() if v == 1]
    print(len(res))