from virtualmachine import VirtualMachine


def print_dict(d):
    x_min = min(p[0] for p in d)
    x_max = max(p[0] for p in d)
    y_min = min(p[1] for p in d)
    y_max = max(p[1] for p in d)

    for x in range(x_min, x_max + 1):
        for y in range(y_min, y_max + 1):
            print(" " if d[(x, y)] == 0 else d[(x, y)], end="")
        print()


def part_1():
    with open("./day13.txt", "r") as infile:
        data = infile.read().split(",")
        program = [int(x) for x in data]
        vm = VirtualMachine(program)
        vm.run()
        out = vm.output
        screen = {}

    for i in range(0, len(out), 3):
        y = out[i]
        x = out[i + 1]
        tile = out[i + 2]
        screen[(x, y)] = tile

    blocks = [x for x in screen.values() if x == 2]
    print("part1:", len(blocks))
    return screen


def part_2():
    with open("./day13.txt", "r") as infile:
        data = infile.read().split(",")
        program = [int(x) for x in data]
        program[0] = 2
        vm = VirtualMachine(program)

        paddle = (22, 20)
        ball = (19, 18)

        while not vm.halt:
            if vm.output and vm.output[-1] == 3 and len(vm.output) % 3 == 0:
                paddle = (vm.output[-3], vm.output[-2])
                vm.input = (
                    [0]
                    if paddle[0] == ball[0]
                    else [-1] if paddle[0] > ball[0] else [1]
                )
            if vm.output and vm.output[-1] == 4 and len(vm.output) % 3 == 0:
                ball = (vm.output[-3], vm.output[-2])
                vm.input = (
                    [0]
                    if paddle[0] == ball[0]
                    else [-1] if paddle[0] > ball[0] else [1]
                )

            vm.process()

        return vm.output


out = part_2()
print(out)
