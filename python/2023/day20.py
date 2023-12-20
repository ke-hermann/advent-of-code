from pprint import pprint
from collections import deque


class Node:
    def __init__(self, t, children, name):
        self.type = (
            "flip"
            if t == "%"
            else "broadcaster"
            if t == "B"
            else "conj"
            if t == "&"
            else "untyped"
        )
        self.children = children
        self.inputs = {}
        self.status = False
        self.name = name

    def send(self, sender, pulse):
        global low_c
        global high_c

        if pulse == 1:
            high_c += 1
        else:
            low_c += 1

        if self.type == "untyped":
            return []

        elif self.type == "conj":
            self.inputs[sender] = pulse

            for c in self.children:
                if all(x == 1 for x in self.inputs.values()):
                    yield (self.name, 0, c)
                else:
                    yield (self.name, 1, c)

        elif self.type == "broadcaster":
            for c in self.children:
                yield (self.name, 0, c)

        elif self.type == "flip":
            if pulse == 1:
                return []
            else:
                for c in self.children:
                    yield (self.name, 1 if not self.status else 0, c)
                self.status = not self.status

    def __repr__(self) -> str:
        return (
            f"{self.name}, {self.type}, {self.children}, {self.inputs}, {self.status}"
        )


with open("./day20.txt", "r") as infile:
    data = infile.read().splitlines()
    nodes = {}
    for line in data:
        k, t = line.split(" -> ")
        l, rest = k[0], k[1:]
        ts = t.split(", ")
        if k == "broadcaster":
            nodes["broadcaster"] = Node("B", ts, k)
        else:
            n = Node(l, ts, rest)
            nodes[rest] = n

    # prepopulate conj and end nodes
    for n in list(nodes.values()):
        for c in n.children:
            if c not in nodes:
                nodes[c] = Node("untyped", [], c)
            if nodes[c].type == "conj":
                nodes[c].inputs[n.name] = 0

    low_c = 0
    high_c = 0

    for _ in range(1000):
        queue = deque([("button", 0, "broadcaster")])
        while queue:
            sender, pulse, cur = queue.popleft()
            xs = list(nodes[cur].send(sender, pulse))
            if xs:
                queue.extend(xs)

    print(low_c * high_c)
