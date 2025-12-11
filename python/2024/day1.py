from collections import Counter

with open("day1.txt", "r") as infile:
    data = infile.read().splitlines()
    col1, col2 = [], []
    for line in data:
        a, b = line.split("   ")
        col1.append(int(a))
        col2.append(int(b))

    col1.sort()
    col2.sort()

    total = 0

    for x, y in zip(col1, col2):
        total += abs(x - y)

    print(total)

    # part 2
    total_p2 = 0
    C = Counter(col2)
    for x in col1:
        total_p2 += C[x] * x

    print(total_p2)
