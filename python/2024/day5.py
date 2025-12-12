def is_ordered(u):
    for a, b in orderings:
        if a in u and b in u:
            if u.index(a) > u.index(b):
                return False 
    return True

    
with open("day5.txt", "r") as infile:
    data = infile.read().split("\n\n")
    orderings = [s.split("|") for s in data[0].split()]
    pagenums = [s.split(",") for s in data[1].split()]
    total = 0

    for p in pagenums:
        if is_ordered(p):
            total += int(p[len(p)//2])

    print(total)


