def print_dict(d):
    x_min = min(p[0] for p in d)
    x_max = max(p[0] for p in d)
    y_min = min(p[1] for p in d)
    y_max = max(p[1] for p in d)

    for x in range(x_min, x_max + 1):
        for y in range(y_min, y_max + 1):
            print(" " if d[(x, y)] == 0 else d[(x, y)], end="")
        print()


def print_set(d):
    x_min = min(p[0] for p in d)
    x_max = max(p[0] for p in d)
    y_min = min(p[1] for p in d)
    y_max = max(p[1] for p in d)

    for x in range(x_min, x_max + 1):
        for y in range(y_min, y_max + 1):
            print("#" if (x, y) in d else " ", end="")
        print()
