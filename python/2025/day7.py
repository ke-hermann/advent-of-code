first, *grid = open('day7.txt')

b = [1] * len(first)
print(b)

for line in grid[::-1]:
    for x in range(len(line)):
        if line[x] == '^':
            b[x] = b[x-1] + b[x+1]

print(b[first.find('S')])
