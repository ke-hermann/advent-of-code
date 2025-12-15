import re

w, h = 101, 103
bots = [[*map(int, re.findall(r'-?\d+',l))]
                   for l in open('day14.txt')]

def danger(t):
    a = b = c = d = 0

    for x, y, dx, dy in bots:
        x = (x + dx * t) % w
        y = (y + dy * t) % h

        a += x > w//2 and y > h//2
        b += x > w//2 and y < h//2
        c += x < w//2 and y > h//2
        d += x < w//2 and y < h//2

    return a * b * c * d

print(danger(100))
print(min(range(10_000), key=danger))
