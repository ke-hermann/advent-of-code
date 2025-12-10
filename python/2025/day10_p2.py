import numpy as np
from scipy.optimize import linprog
from collections import deque


with open("day10.txt") as f:
    ls = f.read().strip().split("\n")

tasks = []
for l in ls:
    toggles, *buttons, counters = l.split()
    toggles = set(i for i, x in enumerate(toggles[1:-1]) if x == "#")
    moves = [set(map(int, b[1:-1].split(","))) for b in buttons]
    counters = tuple(map(int, counters[1:-1].split(",")))
    tasks.append((toggles, moves, counters))


def solve1(goal, moves):
    q = deque()
    q.append((set(), 0))
    seen = set()
    while q:
        curr, steps = q.popleft()
        if curr == goal:
            return steps
        for m in moves:
            newset = curr ^ m
            if newset in seen:
                continue
            seen.add(frozenset(newset))
            q.append((newset, steps + 1))


print(sum(solve1(goal, moves) for goal, moves, _ in tasks))


# Part 2
def solve2(goal, moves):
    c = [1] * len(moves)
    A_eq = [[i in m for m in moves] for i in range(len(goal))]
    return linprog(c, A_eq=A_eq, b_eq=goal, integrality=True).fun


print(sum(solve2(counters, moves) for _, moves, counters in tasks))
