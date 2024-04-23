import re


def is_triangle(a, b, c):
    return a + b > c and  a + c > b and b + c > a

with open("./day3.txt", "r") as infile:
    data = infile.read().splitlines()
    tri_count = 0
    col1 = []
    col2 = []
    col3 = []

    for line in data:
        nums = re.findall(r"\d+", line)
        a, b, c = [int(x) for x in nums]
        tri_count += is_triangle(a, b, c)
        col1.append(a)
        col2.append(b)
        col3.append(c)

    # part 1
    print(tri_count)
    # part 2
    tri_count_p2 = 0
    for col in [col1, col2, col3]:
        for i in range(0, len(col) - 2, 3):
            a, b, c = col[i:i+3]
            tri_count_p2 += is_triangle(a, b, c)

    print(tri_count_p2)

