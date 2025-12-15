from collections import defaultdict

def neighbors(x, y):
    return [(x + a, y + b) for (a, b) in [(-1, 0), (1, 0), (0, -1), (0, 1)]]


def fill(x, y, pl):
    queue = [(x, y)]
    seen = set()
    while queue:
        x, y = queue.pop()
        yield((x, y))
        tl = [p for p in neighbors(x, y) if D[p] == pl and p not in seen]
        seen.add((x, y))
        queue.extend(tl)


def perimeter(plot, plant):
    count = 0
    for x, y in plot:
        ns = neighbors(x, y)
        count += sum([1 for p in ns if D[p] != plant])
    return count

def count_sides(region_points):
    """
    Calculates the number of continuous sides (straight perimeter segments)
    of a region defined by a set of 2D grid points.
    
    Args:
        region_points (list of tuple): A list of (row, col) or (x, y) integer coordinates.
    
    Returns:
        int: The total number of sides.
    """
    if not region_points:
        return 0
    
    # Convert to set for O(1) lookups
    points = set(region_points)
    sides = 0
    
    for r, c in points:
        
        # --- Check Top Edge ---
        # An edge exists if the neighbor above is empty
        if (r - 1, c) not in points:
            # It starts a new side if the neighbor to the left (r, c-1)
            # either is empty OR has a filled neighbor above it.
            # (i.e., the left neighbor does NOT have a top edge to extend)
            neighbor_left = (r, c - 1)
            neighbor_left_above = (r - 1, c - 1)
            
            # Check if the left neighbor has a matching top edge
            left_has_edge = (neighbor_left in points) and (neighbor_left_above not in points)
            
            if not left_has_edge:
                sides += 1

        # --- Check Bottom Edge ---
        # Edge exists if neighbor below is empty
        if (r + 1, c) not in points:
            neighbor_left = (r, c - 1)
            neighbor_left_below = (r + 1, c - 1)
            
            left_has_edge = (neighbor_left in points) and (neighbor_left_below not in points)
            
            if not left_has_edge:
                sides += 1

        # --- Check Left Edge ---
        # Edge exists if neighbor to left is empty
        if (r, c - 1) not in points:
            # We compare with the neighbor UP (r-1, c) for vertical sides
            neighbor_up = (r - 1, c)
            neighbor_up_left = (r - 1, c - 1)
            
            up_has_edge = (neighbor_up in points) and (neighbor_up_left not in points)
            
            if not up_has_edge:
                sides += 1
        
        # --- Check Right Edge ---
        # Edge exists if neighbor to right is empty
        if (r, c + 1) not in points:
            neighbor_up = (r - 1, c)
            neighbor_up_right = (r - 1, c + 1)
            
            up_has_edge = (neighbor_up in points) and (neighbor_up_right not in points)
            
            if not up_has_edge:
                sides += 1
                
    return sides

def count_runs(intervals):
    intervals.sort()
    sides = 0
    end = None

    for start, stop in intervals:
        if end is None or start != end:
            sides += 1
        end = stop

    return sides

with open("day12.txt", "r") as infile:
    data = infile.read().splitlines()
    D = defaultdict(lambda: ".")
    for line in data:
        for i, row in enumerate(data):
            for j, cell in enumerate(row):
                D[(i, j)] = cell

    ks = set(D.keys())
    groups = []

    while ks:
        x, y = ks.pop()
        plot = set(fill(x, y, D[(x, y)]))
        groups.append((plot, D[(x, y)]))
        ks = ks - plot

    p1 = 0
    p2 = 0
    for plot, plant in groups:
        p1 += len(plot) * perimeter(plot, plant)
        p2 += len(plot) * count_sides(plot)

    print(p1)
    print(p2)

