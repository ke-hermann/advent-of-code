import re 
from itertools import permutations, product
import sys


with open("day10.txt", "r") as infile:
    data = infile.read().splitlines()
    presses = 0

    for line in data:
        indicators = list(re.findall(r"\[.*?\]", line)[0][1:-1])
        wiring = re.findall(r"\(.*?\)", line)
        wiring = [[int(x) for x in s[1:-1].split(",")] for s in wiring]
        joltage = re.findall(r"\{.*?\}", line)

        i = 1
        run = True

        while run:
            for p in product(wiring, repeat=i):
                target = list("." * len(indicators))
                for button in p:
                    for x in button:
                        target[x] = "#" if target[x] == "." else "."
                if target == indicators:
                    run = False
                    presses += i
                    break
            else:
                i += 1

    print(presses)

