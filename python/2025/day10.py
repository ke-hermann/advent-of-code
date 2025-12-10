import re 


with open("day10.txt", "r") as infile:
    data = infile.read().splitlines()
    for line in data:
        print(line)
        indicators = re.findall(r"\[.*?\]", line)
        wiring = re.findall(r"\(.*?\)", line)
        joltage = re.findall(r"\{.*?\}", line)

        print(indicators, wiring, joltage)
