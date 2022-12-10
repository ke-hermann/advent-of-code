from math import ceil


def seat_id(instructions):
    rows = instructions[:-3]
    cols = instructions[-3:]

    row_seat = 0
    col_seat = 0

    low = 0
    high = 127

    for c in rows:
        if c == "F":
            high = (high + low) // 2
        if c == "B":
            low = ceil((high + low) / 2)

        row_seat = low

    low = 0
    high = 7

    for c in cols:
        if c == "L":
            high = (high + low) // 2
        if c == "R":
            low = ceil((high + low) / 2)

        col_seat = low

    return row_seat * 8 + col_seat


with open("./resources/2020/day5.txt", "r") as infile:
    data = infile.read().splitlines()
    seat_values = []
    for entry in data:
        seat = seat_values.append(seat_id(entry))

    print(max(seat_values))

    # part2
    for i in range(1, len(seat_values) - 1):
        if i - 1 in seat_values and i not in seat_values and i + 1 in seat_values:
            print(i)
