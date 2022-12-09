with open("./resources/2020/day4.txt", "r") as infile:
    data = infile.read().split("\n\n")
    entries = []
    for e in data:
        s = e.replace("\n", " ")
        xs = dict([t.split(":") for t in s.split()])
        entries.append(xs)

required = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
total = 0

for entry in entries:
    valid = all(x in entry.keys() for x in required)
    total += valid

print(total)