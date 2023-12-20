from collections import deque
import graphviz


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
        global iteration

        if self.name == "dh" and not all(x == 1 for x in self.inputs.values()):
            print(iteration)

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

    G = graphviz.Digraph()
    # prepopulate conj and end nodes
    for n in list(nodes.values()):
        G.node(n.name, label=f"{n.type}:{n.name}")
        for c in n.children:
            G.edge(n.name, c)
            if c not in nodes:
                nodes[c] = Node("untyped", [], c)
            if nodes[c].type == "conj":
                nodes[c].inputs[n.name] = 0

    # G.render("doctest-output/round-table.gv", view=True)
    low_c = 0
    high_c = 0
    iteration = 0

    while iteration < 20000:
        queue = deque([("button", 0, "broadcaster")])
        while queue:
            sender, pulse, cur = queue.popleft()
            xs = list(nodes[cur].send(sender, pulse))
            if xs:
                queue.extend(xs)

        iteration += 1

    print(low_c * high_c)
