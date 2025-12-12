from functools import lru_cache
from collections import defaultdict

@lru_cache(maxsize=None)
def reachable(cur, dac, fft):
    if cur == "out":
        return dac and fft
    else:
        if cur == "dac":
            dac = True
        if cur == "fft":
            fft = True
        return sum([reachable(x, dac, fft) for x in d[cur]])

with open("day11.txt", "r") as infile:
    data = infile.read().splitlines()
    d = defaultdict(list)
    for line in data:
        n, t = line.split(": ")
        t = t.split(" ")
        d[n] = t

    paths_p1 = reachable("you", True, True)
    paths_p2 = reachable("svr", False, False)
    print(paths_p1)
    print(paths_p2)
