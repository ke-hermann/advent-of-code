from vm import VirtualMachine


with open("resources/day5.txt", "r") as infile:
    data = infile.read().strip().split(",")
    pg = [int(x) for x in data]

    # pg = [1002, 4, 3, 4, 33]
    vm = VirtualMachine(pg)
    vm.input.append(5)
    vm.run()
