from collections import defaultdict


class VirtualMachine:
    def __init__(self, program) -> None:
        self.position = 0
        self.halt = 0
        self.input = []
        self.output = []
        self.program = defaultdict(int)
        for i, v in enumerate(program):
            self.program[i] = v

    def process(self):
        i = self.position
        opcode = self.program[i]
        a = self.program[i + 1]
        b = self.program[i + 2]
        c = self.program[i + 3]
        k1 = self.program[a]
        k2 = self.program[b]
        k3 = self.program[c]

        match opcode:
            case 1:
                self.program[k3] = k1 + k2
            case 2:
                self.program[k3] = k1 * k2
            case 3:
                v = self.input.pop()
                self.program[a] = v
            case 4:
                v = self.program[a]
                self.output.append(v)

            case 99:
                self.halt = True

        self.position += 4

    def run(self):
        while not self.halt:
            self.process()
