from collections import deque
from string import ascii_lowercase

def reaction(polymer):
    hd = deque(polymer[0])
    tl = deque(polymer[1:])

    while tl:
        if not hd:
            hd.append(tl.popleft())

        elif hd[-1] != tl[0] and hd[-1].upper() == tl[0].upper():
            hd.pop()
            tl.popleft()
        else:
            hd.append(tl.popleft())

    return hd

with open("../../resources/2018/day5.txt", "r") as infile:
    data = infile.read().strip()

result = reaction(data)
print(len(result))

 # part 2
counts = []
for c in ascii_lowercase:
    s = [x for x in data if x != c and x != c.upper()]
    r = len(reaction(s))
    counts.append(r)

print(min(counts))
