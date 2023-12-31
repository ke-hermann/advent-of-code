from collections import defaultdict


class InvalidModeError(Exception):
    pass


class VirtualMachine:
    def __init__(self, program) -> None:
        self.position: int = 0
        self.relative_base = 0
        self.halt = 0
        self.input = []
        self.output = []
        self.program = defaultdict(int)
        for i, v in enumerate(program):
            self.program[i] = v

    def handle_parameter(self, k, mode):
        if mode == "0":
            return self.program[k]
        elif mode == "1":
            return k
        elif mode == "2":
            return self.program[k + self.relative_base]
        else:
            raise InvalidModeError

    def parse_opcode(self, i, opcode):
        p3, p2, p1, d, e = f"{opcode:05}"
        instruction = int(d + e)

        a = self.program[i + 1]
        b = self.program[i + 2]
        c = self.program[i + 3]

        k1 = self.handle_parameter(a, p1)
        k2 = self.handle_parameter(b, p2)
        k3 = self.handle_parameter(c, p3)
        return (instruction, k1, k2, k3)

    def process(self):
        opcode = self.program[self.position]
        op_str = f"{opcode:05}"
        instruction, k1, k2, _ = self.parse_opcode(self.position, opcode)
        i = self.position
        a = self.program[i + 1]
        _ = self.program[i + 2]
        c = self.program[i + 3]

        match instruction:
            case 1:
                p = c + self.relative_base if op_str[0] == "2" else c
                self.program[p] = k1 + k2
                self.position += 4
            case 2:
                p = c + self.relative_base if op_str[0] == "2" else c
                self.program[p] = k1 * k2
                self.position += 4
            case 3:
                v = self.input.pop(0)
                p = a + self.relative_base if op_str[2] == "2" else a
                self.program[p] = v
                self.position += 2
            case 4:
                self.output.append(k1)
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
                p = c + self.relative_base if op_str[0] == "2" else c
                if k1 < k2:
                    self.program[p] = 1
                else:
                    self.program[p] = 0
                self.position += 4
            case 8:
                p = c + self.relative_base if op_str[0] == "2" else c
                if k1 == k2:
                    self.program[p] = 1
                else:
                    self.program[p] = 0
                self.position += 4
            case 9:
                self.relative_base += k1
                self.position += 2

            case 99:
                self.halt = True

    def run(self):
        while not self.halt:
            self.process()

    def run_until_out(self):
        while not self.output and not self.halt:
            self.process()
