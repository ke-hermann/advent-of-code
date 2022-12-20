from collections import deque

with open("../../resources/2022/day20.txt", "r") as infile:
    data = infile.read().splitlines()
    data = list(enumerate([int(x) for x in data]))
    mixed = deque(data[:])
    l = len(data)

    for p in data:
        i = mixed.index(p)
        mixed.rotate(-i)
        v = mixed.popleft()
        mixed.rotate(-v[1])
        mixed.appendleft(v)

r = [x[1] for x in mixed]
z = r.index(0)
total = 0
for i in [1000, 2000, 3000]:
    total += r[(z + i) % l]
print(total)
