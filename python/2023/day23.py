START = (0, 1)
END = (140, 139)


def intersect_dist(cur, dist, seen):
    if cur in intersections:
        return (cur, dist)

    n = [p for p in neighbors[cur] if p not in seen][0]
    return intersect_dist(n, dist + 1, seen | {cur})


def bfs(node, score, seen):
    if node == END:
        yield score

    for p, dist in graph[node]:
        if p in seen:
            continue
        yield from bfs(p, score + dist, seen | {node})


with open("./day23.in", "r") as infile:
    data = infile.read().splitlines()
    neighbors = {}
    intersections = [START, END]
    graph = {}

    for i, row in enumerate(data):
        for j, val in enumerate(row):
            if val not in ".><v^":
                continue
            ec = 0

            for x, y in [(0, -1), (0, 1), (1, 0), (-1, 0)]:
                m, n = i + x, j + y
                if m < 0 or m >= len(data) or n < 0 or n >= len(data[0]):
                    continue
                if data[m][n] not in ".><v^":
                    continue
                ec += 1

                neighbors.setdefault((i, j), []).append((m, n))
            if ec >= 3:
                intersections.append((i, j))

    for i in intersections:
        for n in neighbors[i]:
            (t, d) = intersect_dist(n, 1, {i})
            graph.setdefault(i, []).append((t, d))

    result = bfs(START, 0, set([START]))
    print(max(result))
