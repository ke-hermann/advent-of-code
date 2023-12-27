from vm import VirtualMachine


with open("resources/day5.txt", "r") as infile:
    data = infile.read().strip().split(", ")
    pg = [int(x) for x in data]
    print(data)
