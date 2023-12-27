import networkx as nx

with open("day25.in", "r") as infile:
    data = infile.read().splitlines()
    G = nx.Graph()
    for line in data:
        l, r = line.split(":")
        G.add_node(l)
        for x in r.strip().split(" "):
            G.add_edge(l, x.strip())

    nx.write_graphml(G, "./graph.graphml")

    G.remove_edge("xgz", "klk")
    G.remove_edge("nvf", "bvz")
    G.remove_edge("cbl", "vmq")

    print([len(x) for x in nx.connected_components(G)])
