import networkx as nx

with open("./resources/2022/day12.txt", "r") as infile:
    data = infile.read().splitlines()
    heights = {}
    G = nx.DiGraph()

    for i, row in enumerate(data):
        for j, v in enumerate(row):
            heights[(i, j)] = v
        
    target = [k for k in heights.keys() if heights[k] == "E"][0]
    start = [k for k in heights.keys() if heights[k] == "S"][0]
    heights[target] = "z"
    heights[start] = "s"

    for (x, y) in heights.keys():
        adjacent = [(x + 1, y), (x - 1, y), (x, y + 1), (x, y - 1)]
        adjacent = [p for p in adjacent if p in heights.keys()]
        adjacent = [
            p for p in adjacent if (ord(heights[p]) - ord(heights[(x, y)])) <= 1
        ]
        for a in adjacent:
            G.add_edge((x, y), a)

    result = nx.shortest_path(G, start, target)
    print(target, len(result))