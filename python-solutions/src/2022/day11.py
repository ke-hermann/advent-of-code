challenge_input = [
    [[93, 54, 69, 66, 71], "* 3", 7, 7, 1],
    [[89, 51, 80, 66], "* 17", 19, 5, 7],
    [[90, 92, 63, 91, 96, 63, 64], "+ 1", 13, 4, 3],
    [[65, 77], "+ 2", 3, 4, 6],
    [[76, 68, 94], "* cur_item", 2, 0, 6],
    [[86, 65, 66, 97, 73, 83], "+ 8", 11, 2, 3],
    [[78], "+ 6", 17, 0, 1],
    [[89, 57, 59, 61, 87, 55, 55, 88], "+ 7", 5, 2, 5],
]


class Monkey:
    def __init__(self, items, op, test, t, f) -> None:
        self.items = items
        self.op = op
        self.test = test
        self.t = t
        self.f = f
        self.inspections = 0

    def turn(self):
        global monkeys
        while self.items:
            self.inspections += 1
            cur_item = self.items.pop(0)
            worry = eval(f"{cur_item} {self.op}") % 9699690
            if worry % self.test == 0:
                monkeys[self.t].items.append(worry)
            else:
                monkeys[self.f].items.append(worry)


monkeys = [Monkey(*xs) for xs in challenge_input]
results = []

for _ in range(10000):
    for monkey in monkeys:
        monkey.turn()

for monkey in monkeys:
    results.append(monkey.inspections)

results.sort()
print(results)
