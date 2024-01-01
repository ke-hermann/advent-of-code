from virtualmachine import VirtualMachine


def generate_map():
    with open("./day17.txt", "r") as infile:
        data = infile.read().strip().split(",")
        program = [int(x) for x in data]
        vm = VirtualMachine(program)
        vm.run()
        s = bytes(vm.output).decode()
        print(s)


def part1():
    with open("./day17_out.txt", "r") as infile:
        data = infile.read().splitlines()
        alignment = 0
        intersections = []

        for x, row in enumerate(data):
            for y, v in enumerate(row):
                if v != "#":
                    continue

                neighbors = []
                for i, j in [(1, 0), (-1, 0), (0, -1), (0, 1)]:
                    m, n = x + i, y + j
                    neighbors.append((m, n))

                try:
                    if all(data[p[0]][p[1]] == "#" for p in neighbors):
                        intersections.append((x, y))
                except Exception:
                    pass

        for x, y in intersections:
            alignment += x * y
        return alignment


def part2():
    with open("./day17.txt", "r") as infile:
        data = infile.read().strip().split(",")
        program = [int(x) for x in data]
        vm = VirtualMachine(program)
        print(ord("\n"))


p1_result = part1()
print("part1:", p1_result)

part2()
