def print_grid(grid_dict, empty_char='.', padding=1, invert_y=False):
    """
    Pretty prints a dictionary of coordinates {(x, y): value} as a grid.
    
    Args:
        grid_dict (dict): Keys are (x, y) tuples, values are what to print.
        empty_char (str): What to print for missing coordinates.
        padding (int): Horizontal spacing between cells.
        invert_y (bool): If True, prints y=0 at the bottom (Cartesian style). 
                         If False, prints y=0 at the top (Matrix/Screen style).
    """
    if not grid_dict:
        print("Grid is empty.")
        return

    # 1. Determine the boundaries of the grid
    xs = [x for x, y in grid_dict.keys()]
    ys = [y for x, y in grid_dict.keys()]
    
    min_x, max_x = min(xs), max(xs)
    min_y, max_y = min(ys), max(ys)

    # 2. Determine cell width for alignment
    # We check the length of every value and the empty_char to find the widest one
    all_values = [str(v) for v in grid_dict.values()]
    max_len = max([len(v) for v in all_values] + [len(empty_char)])
    cell_width = max_len + padding

    # 3. Determine the range for Y (Rows)
    # Standard console prints top-to-bottom.
    if invert_y:
        y_range = range(max_y, min_y - 1, -1) # e.g., 5, 4, 3...
    else:
        y_range = range(min_y, max_y + 1)     # e.g., 0, 1, 2...

    # 4. Print the grid
    for y in y_range:
        row_str = ""
        for x in range(min_x, max_x + 1):
            # Get value or default, convert to string
            val = str(grid_dict.get((x, y), empty_char))
            # Pad the string to ensure column alignment
            row_str += val.ljust(cell_width)
        print(row_str)
