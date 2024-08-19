import re
import sys

sys.path.append("..")
import utils
import code


def pp():
    utils.print_dict(reservoir, ".")


with open("./day17.txt", "r") as infile:
    data = infile.read().splitlines()
    reservoir = {}
    reservoir[(0, 500)] = "+"
    for line in data:
        p = re.findall(r"[xy]=\d+", line)[0]
        d = re.findall(r"[xy]=\d+..\d+", line)[0]
        l, h = [int(x) for x in d[2:].split("..")]
        v = int(p[2:])
        for i in range(l, h + 1):
            reservoir[(v, i) if "x" in d else (i, v)] = "#"

    utils.print_dict(reservoir, ".")
    while True:
        code.interact(local=locals())
