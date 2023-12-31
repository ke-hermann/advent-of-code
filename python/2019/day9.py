from virtualmachine import VirtualMachine

with open("day9.txt", "r") as infile:
    data = infile.read().strip()
    PROGRAM = [int(x) for x in data.split(",")]

computer = VirtualMachine(PROGRAM)
computer.input.append(2)
computer.run()
print(computer.output)
