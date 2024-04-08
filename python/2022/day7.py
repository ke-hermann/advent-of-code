from collections import defaultdict

from pathlib import PurePath


with open("./day7.txt", "r") as infile:
    data = infile.read().splitlines()

    current_path = PurePath("/")
    filesystem = defaultdict(list)

    while data:
        line = data.pop(0)

        if line == "$ cd ..":
            current_path = current_path.parent
        elif line.startswith("$ cd"):
            _, _, p = line.split(" ")
            current_path = current_path / p
        elif line == "$ ls":
            while data and not data[0].startswith("$"):
                s = data.pop(0)
                a, b = s.split(" ")
                if a != "dir":
                    filesystem[current_path].append((int(a), b))

    # part 1
    total = 0

    for d in filesystem:
        size = 0
        for k, files in filesystem.items():
            for f in files:
                p = k / f[1]
                if d in p.parents:
                    size += f[0]

        if size <= 100000:
            total += size

    print(total)
