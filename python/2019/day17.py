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
    # part 2 manual solution
    # L10,L6,R10,R6,R8,R8,L6,R8,L10,L6,
    # R10,L10,R8,R8,L10,R6,R8,R8,L6,R8,
    # L10,R8,R8,L10,R6,R8,R8,L6,R8,L10,
    # L6,R10,L10,R8,R8,L10,R6,R8,R8,L6,
    # R8

    # A: L10,L6,R10
    # B: R6,R8,R8,L6,R8
    # C: L10,R8,R8,L10

    # part 2 path substituted
    # A,B,A,C,B,C,B,A,C,B

    with open("./day17.txt", "r") as infile:
        data = infile.read().strip().split(",")
        program = [int(x) for x in data]
        program[0] = 2
        vm = VirtualMachine(program)
        solution = [
            "A,B,A,C,B,C,B,A,C,B\n",
            "L,10,L,6,R,10\n",
            "R,6,R,8,R,8,L,6,R,8\n",
            "L,10,R,8,R,8,L,10\n",
            "n\n",
        ]
        solution = [ord(c) for l in solution for c in l]
        vm.input = solution
        vm.run()
        return vm.output[-1]


p1_result = part1()
p2_result = part2()
print("part1:", p1_result)
print("part2:", p2_result)
