from collections import defaultdict
import re

with open("./resources/day7.txt", "r") as infile:
    data = infile.read().splitlines()


current_path = ["/"]
filesystem = defaultdict(list)

while data:
    instruction = data.pop(0)

    if instruction == "$ cd /":
        current_path = ["/"]
    
    elif instruction == "$ ls":
        while data and  (not data[0].startswith("$")):
            filename = data.pop(0)
            weight = [int(x) for x in re.findall("\d+", filename)]
            filesystem[tuple(current_path)].extend(weight)

    elif instruction == "$ cd ..":
        current_path.pop()

    else:
        _, _, dirname = instruction.split()
        current_path.append(dirname)

for k, v in filesystem.items():
    filesystem[k] = sum(v)
# part 1
total = 0
for d, v in filesystem.items():
    l = len(d)
    subdirs = [s for s in filesystem.keys() if s[:l] == d]
    total_weight = sum([filesystem[s] for s in subdirs])
    
    if total_weight > 100000:
        continue 
    total += total_weight

print(total)