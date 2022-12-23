from dataclasses import dataclass
from numbers import Number


op = {
    "+": lambda x, y: x + y,
    "-": lambda x, y: x - y,
    "*": lambda x, y: x * y,
    "/": lambda x, y: x / y,
}


@dataclass
class Monkey:
    x: str
    f: str
    y: str
    out: int = None


with open("resources/2022/day21.txt", "r") as infile:
    data = infile.read().splitlines()
    testing = True
    low = 0
    high = 9999999999999999

for i in range(50):

    monkeys = {}
    for line in data:
        s = line.replace(":", "").split()
        if len(s[1:]) == 1:
            monkeys[s[0]] = Monkey(None, None, None, int(s[1]))
        else:
            monkeys[s[0]] = Monkey(*s[1:])

    monkeys["humn"] = Monkey(None, None, None, (low + high) // 2)

    while monkeys["root"].out is None:
        for name, m in monkeys.items():
            if m.out is not None:
                continue
            if type(m.x) == str and monkeys[m.x].out:
                m.x = monkeys[m.x].out
            if type(m.y) == str and monkeys[m.y].out:
                m.y = monkeys[m.y].out

            if isinstance(m.x, Number) and isinstance(m.y, Number):
                m.out = op[m.f](m.x, m.y)

    if monkeys["root"].out > 0:
        low = (low + high) // 2
    else:
        high = (low + high) // 2

    r = monkeys["root"]
    print(low, high, abs(r.x - r.y))
