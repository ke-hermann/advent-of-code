from itertools import product, zip_longest

def left_to_right(expr):
    tokens = expr.split()
    result = int(tokens[0])

    for i in range(1, len(tokens), 2):
        op = tokens[i]
        val = int(tokens[i + 1])
        if op == "+":
            result += val
        elif op == "*":
            result *= val
        elif op == "||":
            result = int(str(result) + str(val))

    return result

def intersperse(a, b):
    return [
        x
        for pair in zip_longest(a, b)
        for x in pair
        if x is not None
    ]

with open("day7.txt", "r") as infile:
    data = infile.read().splitlines()
    total = 0
    for line in data:
        t, s = line.split(": ")
        xs = s.split(" ")
        t = int(t)

        for ops in product(["*", "+", "||"], repeat=len(xs) - 1):
            eq = intersperse(xs, ops)
            s = " ".join(eq)
            i = left_to_right(s)
            if i == t:
                total += t
                break

    print(total)
