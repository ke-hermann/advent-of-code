import re
from collections import defaultdict
from utils import print_grid
from time import sleep

WIDTH = 101
HEIGHT = 103

class Robot:

    def __init__(self, x, y, vx, vy):
        self.x = x
        self.y = y 
        self.vx = vx
        self.vy = vy

    def move(self):
        self.x = (self.x + self.vx) % WIDTH
        self.y = (self.y + self.vy) % HEIGHT

    def __str__(self):
        return f"x:{self.x} y:{self.y}"

with open("day14.txt", "r") as infile:
    data = infile.read().splitlines()
    robots = []
    for line in data:
        x, y, vx, vy = [int(x) for x in re.findall(r'[-+]?\d+', line)]
        robots.append(Robot(x, y, vx, vy))

    for _ in range(100):
        for r in robots:
            r.move()

    ## count quadrants
    ul, ur, bl, br = 0, 0, 0, 0
    for r in robots:
        if r.x < WIDTH // 2 and r.y < HEIGHT // 2:
            ul += 1
        elif r.x > WIDTH // 2 and r.y < HEIGHT // 2:
            ur += 1
        elif r.x < WIDTH // 2 and r.y > HEIGHT // 2:
            bl += 1
        elif r.x > WIDTH // 2 and r.y > HEIGHT // 2:
            br += 1
    print(ul * ur * bl * br)

    i = 99
    while True:
        i += 1
        d = defaultdict(int)
        for r in robots:
            d[(r.x, r.y)] += 1
            r.move()

        if all(x == 1 for x in d.values()):
            print(i)
            break


