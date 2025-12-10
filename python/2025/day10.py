import re 
import pulp
from itertools import permutations

def solve_min_presses(buttons, targets):
    m = len(targets)
    k = len(buttons)
    prob = pulp.LpProblem("MinPresses", pulp.LpMinimize)
    x = [pulp.LpVariable(f"x{j}", lowBound=0, cat='Integer') for j in range(k)]
    prob += pulp.lpSum(x)
    for p in range(m):
        prob += pulp.lpSum(x[j] for j in range(k) if p in buttons[j]) == targets[p]
    status = prob.solve(pulp.PULP_CBC_CMD(msg=0))
    if status != 1:
        print("unsolvable, something went wrong")
        assert False
    return int(pulp.value(prob.objective))

with open("day10.txt", "r") as infile:
    data = infile.read().splitlines()
    presses = 0
    presses_p2 = 0

    for line in data:
        indicators = list(re.findall(r"\[.*?\]", line)[0][1:-1])
        wiring = re.findall(r"\(.*?\)", line)
        wiring = [[int(x) for x in s[1:-1].split(",")] for s in wiring]
        joltage = re.findall(r"\{.*?\}", line)[0]

        i = 1
        run = True

        # part 1 
        while run:
            for p in permutations(wiring, i):
                target = list("." * len(indicators))
                for button in p:
                    for x in button:
                        target[x] = "#" if target[x] == "." else "."
                if target == indicators:
                    run = False
                    presses += i
                    break
            else:
                i += 1
    
        # part 2 
        joltage_ints = [int(x) for x in re.findall(r"\d+", joltage)]
        presses_p2 +=  solve_min_presses(wiring, joltage_ints)

    # p1 solution
    print(presses)
    print(presses_p2)

