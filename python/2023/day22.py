import re 


with open("./day22.in", "r") as infile:
    data = infile.read().splitlines()
    for line in data:
        xs = re.findall("\d+", line)
        xs = [int(x) for x in xs]
        x0, y0, z0, x1, y1, z1 = xs
        print(xs)
