import re
import time

class Star:
    def __init__(self, y, x, dy, dx):
        self.x = x
        self.y = y
        self.dx = dx
        self.dy = dy


    def move(self):
        self.x += self.dx
        self.y += self.dy


def pprint(stars):
    min_x = min([s.x for s in stars])
    max_x = max([s.x for s in stars])
    min_y = min([s.y for s in stars])
    max_y = max([s.y for s in stars])
    coords = [(s.x, s.y) for s in stars]
        
    for i in range(min_x, max_x + 1):
        for j in range(min_y, max_y + 1):
            if (i, j) in coords:
                print("#", end="")
            else:
                print(".", end="")

        print("")


        
with open("./day10.txt", "r") as infile:
    data = infile.read().splitlines()
    stars = []
    count = 0
    
    for line in data:
        nums = re.findall(r"-?\d+", line)
        nums = [int(x) for x in nums]
        star = Star(*nums)
        stars.append(star)

    while True:
        count += 1
        min_x = min([s.x for s in stars])
        max_x = max([s.x for s in stars])
        min_y = min([s.y for s in stars])
        max_y = max([s.y for s in stars])

        if max_x - min_x < 100 and max_y - min_y < 100:
            pprint(stars)
            print(count)
            time.sleep(1)

        for s in stars:
            s.move()
