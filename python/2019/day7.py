from vm import VirtualMachine
from itertools import permutations

PROGRAM = [
    3,
    26,
    1001,
    26,
    -4,
    26,
    3,
    27,
    1002,
    27,
    2,
    27,
    1,
    27,
    26,
    27,
    4,
    27,
    1001,
    28,
    -1,
    28,
    1005,
    28,
    6,
    99,
    0,
    0,
    5,
]


def run_amplifiers(perm):
    signal = 0
    for p in perm:
        amplifier = VirtualMachine(PROGRAM)
        amplifier.input.append(signal)
        amplifier.input.append(p)
        amplifier.run()
        signal = amplifier.output[0]

    return signal


perms = permutations(range(5, 10), 5)
result = max(perms, key=run_amplifiers)
max_signal = run_amplifiers(result)
print(max_signal)
