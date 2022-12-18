from dataclasses import dataclass
from copy import deepcopy


@dataclass
class Virtualmachine:
    instructions: list
    visited: set
    accumulator: int = 0
    position: int = 0

    def step(self):
        op = self.instructions[self.position]
        match op:
            case ["nop", x]:
                self.position += 1
            case ["acc", x]:
                self.accumulator += x
                self.position += 1
            case ["jmp", x]:
                self.position += x

    def run(self):
        while self.position not in self.visited:
            if self.position >= len(self.instructions):
                print(self.position, self.accumulator)
                break
            self.visited.add(self.position)
            self.step()


with open("resources/2020/day8.txt", "r") as infile:
    data = infile.read().splitlines()
    instructions = []
    for line in data:
        k, v = line.split()
        instructions.append((k, int(v)))

    indices = [i for i, (x, _) in enumerate(instructions) if x == "jmp"]
    for p in indices:
        (_, v) = instructions[p]
        new_inst = deepcopy(instructions)
        new_inst[p] = ("nop", v)
        vm = Virtualmachine(new_inst, set())
        vm.run()
