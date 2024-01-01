from virtualmachine import VirtualMachine
from utils import print_set


def part1():
    total = 0
    for x in range(50):
        for y in range(50):
            vm = VirtualMachine(program[:])
            vm.input = [x, y]
            vm.run_until_out()
            result = vm.output.pop()
            total += result == 1
    return total


def part2():
    cone = set()
    for x in range(0, 1000):
        for y in range(0, 1000):
            vm = VirtualMachine(program[:])
            vm.input = [x, y]
            vm.run_until_out()
            res = vm.output.pop()
            if res == 1:
                cone.add((x, y))

    print_set(cone)


with open("./day19.txt", "r") as infile:
    data = infile.read().strip().split(",")
    program = [int(x) for x in data]

    print("part1:", part1())
    part2()
