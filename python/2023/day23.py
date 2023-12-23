import rustworkx as rx


with open("./day23.in", "r") as infile:
    data = infile.read().splitlines()
    G = rx.PyGraph()
    indices = {}

    for i, row in enumerate(data):
        for j, val in enumerate(row):
            if val not in ".><v^":
                continue

            idx_from = None
            if (i, j) not in indices:
                idx_from = G.add_node((i, j))
                indices[(i, j)] = idx_from
            else:
                idx_from = indices[(i, j)]

            for (x, y) in [(0, -1), (0, 1), (1, 0), (-1, 0)]:
                m, n = i + x, j + y
                if m < 0 or m >= len(data) or n < 0 or n >= len(data[0]):
                    continue
                if data[m][n] not in ".><v^":
                    continue

                idx_to = None
                if (m, n) not in indices:
                    idx_to = G.add_node((m, n))
                    indices[(m, n)] = idx_to
                else:
                    idx_to = indices[(m, n)]

                if (idx_from, idx_to) and (idx_to, idx_from) not in G.edge_list():
                    G.add_edge(idx_from, idx_to, None)


    longest_path = 0
    result = rx.all_simple_paths(G, 0, 9371)
    for r in result:
        l = len(r)
        if l > longest_path:
            longest_path = l
            print(l)

    print(longest_path - 1)
