from itertools import combinations, islice
from collections import deque

def sliding_window(iterable, n):
    iterator = iter(iterable)
    window = deque(islice(iterator, n - 1), maxlen=n)
    for x in iterator:
        window.append(x)
        yield tuple(window)

def intersects(rect_corner1, rect_corner2, segments):
    # Normalize rectangle bounds
    rx1, ry1 = rect_corner1
    rx2, ry2 = rect_corner2
    min_x = min(rx1, rx2)
    max_x = max(rx1, rx2)
    min_y = min(ry1, ry2)
    max_y = max(ry1, ry2)
    
    for seg in segments:
        (sx1, sy1), (sx2, sy2) = seg
        
        if sx1 == sx2:  # Vertical segment
            x = sx1
            y_min_seg = min(sy1, sy2)
            y_max_seg = max(sy1, sy2)
            
            # Check if x is within rectangle's x-range and y-ranges overlap
            if min_x < x < max_x and max(y_min_seg, min_y) < min(y_max_seg, max_y):
                return True
        
        elif sy1 == sy2:  # Horizontal segment
            y = sy1
            x_min_seg = min(sx1, sx2)
            x_max_seg = max(sx1, sx2)
            
            # Check if y is within rectangle's y-range and x-ranges overlap
            if min_y < y < max_y and max(x_min_seg, min_x) < min(x_max_seg, max_x):
                return True
    
    return False

def sq_size(a, b):
    return (1 + abs(a[0] - b[0])) * (1 + abs(a[1] - b[1]))

with open("day9.txt", "r") as infile:
    data = infile.read().splitlines()
    tiles = []

    for line in data:
        a, b = [int(x) for x in line.split(",")]
        tiles.append((a, b))

    # part 1
    print(max([sq_size(a, b) for (a, b) in combinations(tiles, 2)]))

    # part 2
    candidates = []
    for square in combinations(tiles, 2):
        if not intersects(square[0], square[1], sliding_window(tiles, 2)):
            candidates.append(square)

    print(max([sq_size(a, b) for (a, b) in candidates]))

