import re


def cleanup_string(s: str) -> str:
    result = []
    i = 0
    active = True  # Start in active mode

    while i < len(s):
        # Check for 'don't()'
        if s[i : i + 7] == "don't()":
            active = False
            i += 7
            continue

        # Check for 'do()'
        if s[i : i + 4] == "do()":
            active = True
            i += 4
            continue

        # If in active mode, collect the current character
        if active:
            result.append(s[i])

        i += 1

    return "".join(result)


pattern = r"mul\s*\(\s*\d+\s*,\s*\d+\s*\)"
with open("day3.txt", "r") as infile:
    data = infile.read()
    expressions = re.findall(pattern, data)
    total = 0
    for e in expressions:
        a, b = [int(x) for x in re.findall(r"\d+", e)]
        total += a * b

    print(total)

    total_p2 = 0
    data_p2 = cleanup_string(data)
    expressions_p2 = re.findall(pattern, data_p2)

    for e in expressions_p2:
        a, b = [int(x) for x in re.findall(r"\d+", e)]
        total_p2 += a * b

    print(total_p2)
