with open("day12.txt", "r") as infile:
    data = infile.read().splitlines()
    total = 0
    for line in data:
        a, b = line.split(": ")
        w, h = [int(x) for x in a.split("x")]
        xs = [int(x) for x in b.split(" ")]

        required = sum([x * 9 for x in xs])
        total += w * h >= required

    print(total)
