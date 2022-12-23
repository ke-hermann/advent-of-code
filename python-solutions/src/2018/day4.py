import re
from collections import Counter


with open("../../resources/2018/day4.txt", "r") as infile:
    data = infile.read().splitlines()
    active = None
    guards = {}
    sleep = 0

    for s in sorted(data):
        if "Guard" in s:
            active = re.findall("\d+", s)[-1]
        if "asleep" in s:
            sleep = int(re.findall("\d+", s)[-1])
        if "wakes up" in s:
            awake = int(re.findall("\d+", s)[-1])
            guards.setdefault(active, []).extend(range(sleep, awake))


g = max(guards.keys(), key=lambda k: len(guards[k]))
minute = Counter(guards[g]).most_common()[0][0]
print(int(g) * minute)

# part 2
minutes = []
for k, v in guards.items():
    mc = Counter(v).most_common()
    minutes.append((k, mc[0][0], mc[0][1]))

r = max(minutes, key=lambda t: t[2])
print(int(r[0]) * r[1])
