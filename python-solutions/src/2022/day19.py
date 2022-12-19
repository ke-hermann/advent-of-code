from collections import defaultdict
import re

with open("resources/2022/day19.txt", "r") as infile:
    data = infile.read().splitlines()
    blueprints = []
    for line in data:
        xs = re.findall("\d+", line)
        xs = [int(x) for x in xs[1:]]
        blueprints.append({"ore": xs[0], "clay": xs[1], "obsidian": (xs[2], xs[3]), "geode": (xs[4], xs[5])})

        
for b in blueprints:
    bots = ["ore"]
    stash =  defaultdict(int)
    minutes = 0
    queue = []

    while minutes <= 24:
        queue = [(k, v - 1) for (k, v) in queue]
        bots.extend([k for (k, v) in queue if v == 0])
        queue = [x for x in queue if x[1] > 0]

        if stash["ore"] >= b["geode"][0] and stash["obsidian"] >= b["geode"][1]:
            queue.append(("geode", 1))
            stash["ore"] -= b["geode"][0]
            stash["obsidian"] -= b["geode"][1]

        elif stash["ore"] >= b["obsidian"][0] and stash["clay"] >= b["obsidian"][1]:
            queue.append(("obsidian", 1))
            stash["ore"] -= b["obsidian"][0]
            stash["clay"] -= b["obsidian"][1]

        elif stash["ore"] >= b["clay"] and bots.count("clay") <= 3 * bots.count("ore"):
            queue.append(("clay", 1))
            stash["ore"] -= b["clay"]

        elif stash["ore"] >= b["ore"]:
            queue.append(("ore", 1))
            stash["ore"] -= b["ore"]

        stash["ore"] += bots.count("ore")
        stash["clay"] += bots.count("clay")
        stash["obsidian"] += bots.count("obsidian")
        stash["geode"] += bots.count("geode")

        minutes += 1


    print(stash)
