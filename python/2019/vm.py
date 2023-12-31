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

    def parse_opcode(self):
        i = self.position
        opcode = self.program[i]
        p3, p2, p1, d, e = f"{opcode:05}"
        instruction = int(d + e)

        a = self.program[i + 1]
        b = self.program[i + 2]
        c = self.program[i + 3]

        k1 = self.program[a] if p1 == "0" else a
        k2 = self.program[b] if p2 == "0" else b
        k3 = self.program[c] if p3 == "0" else c
        return (instruction, k1, k2, k3)

    def process(self):
        instruction, k1, k2, k3 = self.parse_opcode()
        i = self.position
        a = self.program[i + 1]
        _ = self.program[i + 2]
        c = self.program[i + 3]

        match instruction:
            case 1:
                self.program[c] = k1 + k2
                self.position += 4
            case 2:
                self.program[c] = k1 * k2
                self.position += 4
            case 3:
                v = self.input.pop()
                self.program[a] = v
                self.position += 2
            case 4:
                v = self.program[a]
                self.output.append(v)
                self.position += 2
            case 5:
                if k1 != 0:
                    self.position = k2
                else:
                    self.position += 3
            case 6:
                if k1 == 0:
                    self.position = k2
                else:
                    self.position += 3
            case 7:
                if k1 < k2:
                    self.program[c] = 1
                else:
                    self.program[c] = 0
                self.position += 4
            case 8:
                if k1 == k2:
                    self.program[c] = 1
                else:
                    self.program[c] = 0
                self.position += 4

            case 99:
                self.halt = True

    def run(self):
        while not self.halt:
            self.process()

    def run_until_out(self):
        while not self.output:
            self.process()
