def calc_score(seq):
    total = 0
    for i in range(len(seq) - 1):
        total += seq[i] + seq[i + 1]
    return total


def bridge(seq: list, ports: set):
    if not ports:
        yield seq

    no_matches = True
    for a, b in ports:
        if seq[-1] == a:
            yield from bridge(seq + [b], ports - {(a, b)})
            no_matches = False
        if seq[-1] == b:
            yield from bridge(seq + [a], ports - {(a, b)})
            no_matches = False

    if no_matches:
        yield seq


with open("./day24.txt", "r") as infile:
    data = infile.read().splitlines()
    ports = []

    for line in data:
        a, b = line.split("/")
        ports.append((int(a), int(b)))

    starts = [p for p in ports if p[0] == 0 or p[1] == 0]
    xs = []
    for s in starts:
        ports = [p for p in ports if p != s]
        start = [s[0], s[1]] if s[0] == 0 else [s[1], s[0]]
        results = bridge(start, set(ports[:]))
        xs.extend(list(results))

    xs.sort(key=lambda x: (len(x), calc_score(x)))
    highscore = calc_score(xs[-1])
    print(xs[-1])
    print(highscore)
