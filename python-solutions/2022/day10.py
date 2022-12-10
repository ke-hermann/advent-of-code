from collections import defaultdict


class VirtualMachine:
    def __init__(self, instructions) -> None:
        self.cycle = 0
        self.task_queue = defaultdict(list)
        self.instructions = instructions
        self.x = 1
        self.signal_strength = 0
        self.crt_screen = [["." for _ in range(40)] for _ in range(6)]

    def exec_instruction(self):
        cmd = self.instructions.pop(0)
        match cmd:
            case ["noop"]:
                self.task_queue[self.cycle + 1].append(0)
                self.step()
            case ["addx", v]:
                self.task_queue[self.cycle + 2].append(int(v))
                self.step(2)
                

    def step(self, count=1):
        for _ in range(count):
            if self.cycle in self.task_queue:
                for i in self.task_queue[self.cycle]:
                    self.x += i
            # part 1
            if self.cycle in [20, 60, 100, 140, 180, 220]:
                self.signal_strength += self.x * self.cycle
            # part 2, draw to screen 
            if self.cycle < 240:
                row = self.cycle // 40
                col = self.cycle % 40
                if self.x - 1 <= col <= self.x + 1:
                    self.crt_screen[row][col] = "#"
            self.cycle += 1


    def run(self):
        while self.instructions:
            self.exec_instruction()
        while self.cycle <= max(self.task_queue.keys()):
            self.step()
            


with open("./resources/day10.txt", "r") as infile:
    data = infile.read().splitlines()
    data = [s.split() for s in data]
    vm = VirtualMachine(data)
    vm.run()
    print(vm.signal_strength)
    for line in vm.crt_screen:
        xs = "".join(line)
        print(xs)