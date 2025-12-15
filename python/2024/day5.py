def is_ordered(u):
    for a, b in orderings:
        if a in u and b in u:
            if u.index(a) > u.index(b):
                return False 
    return True


def fix_pagenums(p):
    while not is_ordered(p):
        for a, b in orderings:
            if a in p and b in p:
                i1 = p.index(a)
                i2 = p.index(b)
                if p.index(a) > p.index(b):
                    p[i1], p[i2] = p[i2], p[i1]
    

    
with open("day5.txt", "r") as infile:
    data = infile.read().split("\n\n")
    orderings = [s.split("|") for s in data[0].split()]
    pagenums = [s.split(",") for s in data[1].split()]
    incorrect = []
    total = 0

    for p in pagenums:
        if is_ordered(p):
            total += int(p[len(p)//2])
        else:
            incorrect.append(p)

    # part 1
    print(total)
    # part 2
    total_p2 = 0
    for page in incorrect:
        fix_pagenums(page)
        total_p2 += int(page[len(page)//2])
    print(total_p2)

