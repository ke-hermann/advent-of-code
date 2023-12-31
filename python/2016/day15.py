import re
from dataclasses import dataclass


@dataclass
class Disc:
    offset: int
    length: int
    start: int


def is_pass(disc: Disc, i):
    print(disc)
    t = i + (disc.offset - 1) + (disc.start - 1)
    return t % ((disc.length - 1) ** 2) == 0


with open("./day15.txt", "r") as infile:
    data = infile.read().splitlines()
    discs = []
    for line in data:
        a, b, _, c = [int(x) for x in re.findall(r"\d+", line)]
        discs.append(Disc(a, b, c))

    for i in range(10):
        print(all(is_pass(d, i) for d in discs))
