# By cyphase in ##adventofcode on FreeNode

from functools import reduce
from itertools import combinations, count
from math import gcd


def get_data(infile_path="day12.txt"):
    with open(infile_path, "r") as f:
        data = f.read().rstrip()

    result = []
    for line in data.splitlines():
        result.append(tuple(int(x[2:]) for x in line[1:-1].split(", ")))

    return result


def part1(data):
    moons = [(list(pos), [0, 0, 0]) for pos in data]

    moon_pairs = list(combinations(moons, 2))

    for step in range(1000):
        for (position_a, velocity_a), (position_b, velocity_b) in moon_pairs:
            for axis in range(3):
                if position_a[axis] < position_b[axis]:
                    velocity_a[axis] += 1
                    velocity_b[axis] -= 1
                elif position_a[axis] > position_b[axis]:
                    velocity_a[axis] -= 1
                    velocity_b[axis] += 1

        for position, velocity in moons:
            for axis in range(3):
                position[axis] += velocity[axis]

    total_energy = sum(
        sum(abs(axis) for axis in position) * sum(abs(axis) for axis in velocity)
        for position, velocity in moons
    )

    return total_energy


def _system_axis_state(moons, axis):
    # return (
    #     *(moons[m][0][axis] for m in range(4)),
    #     *(moons[m][1][axis] for m in range(4)),
    # )
    return (
        moons[0][0][axis],
        moons[1][0][axis],
        moons[2][0][axis],
        moons[3][0][axis],
        moons[0][1][axis],
        moons[1][1][axis],
        moons[2][1][axis],
        moons[3][1][axis],
    )


def _part2(data, axis):
    moons = [[list(pos), [0, 0, 0]] for pos in data]

    start_state = _system_axis_state(moons, axis)

    moon_pairs = list(combinations(moons, 2))

    first_loop = True
    for steps in count():
        if _system_axis_state(moons, axis) == start_state and not first_loop:
            return steps
        first_loop = False

        for (position_a, velocity_a), (position_b, velocity_b) in moon_pairs:
            if position_a[axis] < position_b[axis]:
                velocity_a[axis] += 1
                velocity_b[axis] -= 1
            elif position_a[axis] > position_b[axis]:
                velocity_a[axis] -= 1
                velocity_b[axis] += 1

        for pos, v in moons:
            pos[axis] += v[axis]


def lcm(denominators):
    return reduce(lambda a, b: a * b // gcd(a, b), denominators)


def part2(data):
    return lcm((_part2(data, axis) for axis in range(3)))


def main(data):
    print(f"Part 1: {part1(data)}")
    print(f"Part 2: {part2(data)}")


if __name__ == "__main__":
    try:
        data = get_data()
    except FileNotFoundError:
        print("Input file not found")

    main(data)
