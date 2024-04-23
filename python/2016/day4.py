import re
from string import ascii_lowercase
from collections import Counter, OrderedDict

LENGTH = len(ascii_lowercase)


def most_common(s):
    n = Counter(s)
    ordered = sorted(s, key=lambda c: (n[c] * -1, c))
    five = list(OrderedDict.fromkeys(ordered))[:5]
    return "".join(five)


def cipher(s: str, i):
    result = []
    for c in s:
        if c == "-":
            result.append(" ")
        else:
            p = (ascii_lowercase.index(c) + i) % LENGTH
            result.append(ascii_lowercase[p])
    return "".join(result)


with open("./day4.txt", "r") as infile:
    data = infile.read().splitlines()
    total = 0
    for line in data:
        name = "".join(line.split("-")[:-1])
        dash_name = "-".join(line.split("-")[:-1])
        checksum = re.findall(r"\[.*\]", line)[0][1:-1]
        sector_id = int(re.findall(r"\d+", line)[0])

        if checksum == most_common(name):
            total += sector_id

        print(cipher(dash_name, sector_id), sector_id)

    print(total)
