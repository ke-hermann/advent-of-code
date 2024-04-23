import hashlib

DOOR_ID = "reyedfim"

solution = ["?"] * 8
i = 0

while "?" in solution:
    s = f"{DOOR_ID}{i}"
    result = hashlib.md5(s.encode("utf-8")).hexdigest()
    if result.startswith("00000"):
        p = result[5]
        c = result[6]
        if p in "01234567" and solution[int(p)] == "?":
            solution[int(p)] = c
            print(solution)
    i += 1

pw = "".join(solution)
print(pw)
