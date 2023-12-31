from virtualmachine import VirtualMachine
from itertools import permutations

with open("day7.txt", "r") as infile:
    data = infile.read().strip()
    PROGRAM = [int(x) for x in data.split(",")]


def run_amplifiers(perm):
    signal = 0
    for p in perm:
        amplifier = VirtualMachine(PROGRAM)
        amplifier.input.append(p)
        amplifier.input.append(signal)
        amplifier.run()
        signal = amplifier.output[0]

    return signal


def chain_amplifiers(perm):
    amps = []
    signal = 0

    for p in perm:
        amplifier = VirtualMachine(PROGRAM)
        amplifier.input.append(p)
        amps.append(amplifier)

    while any(amp.halt == False for amp in amps):
        cur = amps.pop(0)
        cur.input.append(signal)
        cur.run_until_out()
        if len(cur.output) == 0:
            break
        else:
            signal = cur.output.pop()
            amps.append(cur)

    return signal


perms1 = permutations(range(5), 5)
perms2 = permutations(range(5, 10), 5)

result_p1 = max(perms1, key=run_amplifiers)
max_signal = run_amplifiers(result_p1)
print("part1:", max_signal)

result_p2 = max(perms2, key=chain_amplifiers)
max_signal_p2 = chain_amplifiers(result_p2)
print("part2:", max_signal_p2)
