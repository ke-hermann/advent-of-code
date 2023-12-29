def pprint_dict(m: dict):
    rows = max([x for (x, _) in m.keys()])
    cols = max([y for (_, y) in m.keys()])

    for i in range(rows + 1):
        for j in range(cols + 1):
            print(m.get((i, j), " "), end="")
        print()
