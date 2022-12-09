with open("./resources/day1.txt", "r") as infile:
    data = infile.read().split("\n\n")
    data = [[int(x) for x in g.splitlines()] for g in data]

    group_sums = [sum(g) for g in data]
    group_sums.sort()
    print(sum(group_sums[-3::]))
