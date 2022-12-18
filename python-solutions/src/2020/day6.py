from functools import reduce

with open("resources/2020/day6.txt", "r") as infile:
    data = infile.read().split("\n\n")
    groups = []
    for entry in data:
        groups.append(entry.split("\n"))

# part 1
total = 0
for group in groups:
    distinct = len(set("".join(group)))
    total += distinct
print(total)

# part 2
total_2 = 0
for group in groups:
    s = [set(p) for p in group]
    everyone = reduce(set.intersection, s)
    total_2 += len(everyone)
print(total_2)
