from virtualmachine import VirtualMachine
from collections import defaultdict
from enum import Enum


class Direction(Enum):
    UP = 1
    RIGHT = 2
    DOWN = 3
    LEFT = 4


class Robot:
    def __init__(self, program):
        self.vm = VirtualMachine(program)
        self.x = 0
        self.y = 0
        self.direction = Direction.UP
        self.visited = set()

    def turn_left(self):
        match self.direction:
            case Direction.UP:
                self.direction = Direction.LEFT
            case Direction.LEFT:
                self.direction = Direction.DOWN
            case Direction.DOWN:
                self.direction = Direction.RIGHT
            case Direction.RIGHT:
                self.direction = Direction.UP

    def turn_right(self):
        match self.direction:
            case Direction.UP:
                self.direction = Direction.RIGHT
            case Direction.RIGHT:
                self.direction = Direction.DOWN
            case Direction.DOWN:
                self.direction = Direction.LEFT
            case Direction.LEFT:
                self.direction = Direction.UP

    def forward(self):
        match self.direction:
            case Direction.UP:
                self.x -= 1
            case Direction.RIGHT:
                self.y += 1
            case Direction.DOWN:
                self.x += 1
            case Direction.LEFT:
                self.y -= 1

    def move(self):
        panel = grid[(self.x, self.y)]
        self.vm.input.append(panel)

        self.vm.run_until_out()

        if not self.vm.output:
            return

        color = self.vm.output.pop()
        self.vm.run_until_out()
        next_dir = self.vm.output.pop()
        # sanity check
        assert color in [0, 1] and next_dir in [0, 1]

        grid[(self.x, self.y)] = color
        if next_dir == 0:
            self.turn_left()
        else:
            self.turn_right()
        self.visited.add((self.x, self.y))

        self.forward()


with open("day11.txt", "r") as infile:
    data = infile.read().split(",")
    program = [int(x) for x in data]

    grid = defaultdict(int)
    robot = Robot(program[:])
    while not robot.vm.halt:
        robot.move()
    print("part1:", len(robot.visited))

    robot_2 = Robot(program[:])
    grid = defaultdict(int)
    grid[(0, 0)] = 1
    while not robot_2.vm.halt:
        robot_2.move()

    x_min = min(p[0] for p in grid)
    x_max = max(p[0] for p in grid)
    y_min = min(p[1] for p in grid)
    y_max = max(p[1] for p in grid)

    for x in range(x_min, x_max + 1):
        for y in range(y_min, y_max + 1):
            print(" " if grid[(x, y)] == 0 else "#", end="")
        print()
