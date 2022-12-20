from collections import deque

with open("../../resources/2022/day20.txt", "r") as infile:
    data = infile.read().splitlines()
    data = [int(x) for x in data]
    mixed = deque(data[:])
    l = len(data)

    for i in range(l):
        o = data[i]
        p = mixed.index(o)
        mixed.rotate(-p)
        v = mixed.popleft()
        mixed.insert(o, v)


    z_idx = mixed.index(0)
    total = 0
    for i in [1000, 2000, 3000]:
        total += mixed[(i + z_idx) % l]
    print(total)
