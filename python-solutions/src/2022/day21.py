from dataclasses import dataclass
from numbers import Number


op = {'+': lambda x, y: x + y,
     '-': lambda x, y: x - y,
     '*': lambda x, y: x * y,
     '/': lambda x, y: x / y }

@dataclass
class Monkey:
    x: str 
    f: str 
    y: str
    out: int = None

with open("resources/2022/day21.txt", "r") as infile:
    data = infile.read().splitlines()
    monkeys = {}
    for line in data:
        s = line.replace(":", "").split()
        if len(s[1:]) == 1:
            monkeys[s[0]] = Monkey(None, None, None, int(s[1]))
        else:
            monkeys[s[0]] = Monkey(*s[1:])

    while monkeys["root"].out is None:
        for name, m in monkeys.items():
            if m.out is not None:
                continue 
            if type(m.x) == str and  monkeys[m.x].out:
                m.x = monkeys[m.x].out
            if type(m.y) == str and monkeys[m.y].out:
                m.y = monkeys[m.y].out

            if isinstance(m.x, Number) and isinstance(m.y, Number):
                m.out = op[m.f](m.x, m.y) 

    print(monkeys["root"])
        
