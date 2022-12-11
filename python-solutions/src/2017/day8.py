from collections import defaultdict

with open("resources/2017/day8.txt", "r") as infile:
    data = infile.read().splitlines()
    instructions = [line.split() for line in data]


cpu = defaultdict(int)
seen = []

for op in instructions:
    match op:
        case [a, x, v, _, b, ">", i]:
            v = int(v)
            if cpu[b] > int(i):
                cpu[a] += v if x == "inc" else -v
        case [a, x, v, _, b, "<", i]:
            v = int(v)
            if cpu[b] < int(i):
                cpu[a] += v if x == "inc" else -v
        case [a, x, v, _, b, "==", i]:
            v = int(v)
            if cpu[b] == int(i):
                cpu[a] += v if x == "inc" else -v
        case [a, x, v, _, b, "<=", i]:
            v = int(v)
            if cpu[b] <= int(i):
                cpu[a] += v if x == "inc" else -v
        case [a, x, v, _, b, ">=", i]:
            v = int(v)
            if cpu[b] >= int(i):
                cpu[a] += v if x == "inc" else -v
        case [a, x, v, _, b, "!=", i]:
            v = int(v)
            if cpu[b] != int(i):
                cpu[a] += v if x == "inc" else -v
    seen.append(max(cpu.values()))

print(max(cpu.values()))
print(max(seen))