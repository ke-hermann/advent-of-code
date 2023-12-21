def gen_code(p):
    x = 20151125
    for _ in range(1, p):
        x = (x * 252533) % 33554393 

    return x

COL = 2981 
ROW = 3075
index = 0

for i in range(1, ROW + 1):
    index += i
for j in range(COL - 1):
    index += ROW + j

hash = gen_code(index)
print(hash)

