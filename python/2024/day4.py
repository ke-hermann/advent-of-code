def count_xmas(grid):
    """
    Counts how many times the word "XMAS" appears in the grid
    in any of the 8 directions (horizontal, vertical, diagonal),
    forwards and backwards.

    Args:
        grid: List of strings, all same length, representing the word search grid

    Returns:
        int: Total number of "XMAS" occurrences
    """
    if not grid or not grid[0]:
        return 0

    rows = len(grid)
    cols = len(grid[0])
    word = "XMAS"
    count = 0

    # All 8 directions: (drow, dcol)
    directions = [
        (0, 1),  # right
        (0, -1),  # left
        (1, 0),  # down
        (-1, 0),  # up
        (1, 1),  # down-right
        (1, -1),  # down-left
        (-1, 1),  # up-right
        (-1, -1),  # up-left
    ]

    for r in range(rows):
        for c in range(cols):
            for dr, dc in directions:
                # Try to match the word in this direction starting at (r,c)
                found = True
                for i in range(len(word)):
                    nr = r + i * dr
                    nc = c + i * dc
                    # Check bounds and character match
                    if not (0 <= nr < rows and 0 <= nc < cols):
                        found = False
                        break
                    if grid[nr][nc] != word[i]:
                        found = False
                        break
                if found:
                    count += 1

    return count


with open("day4.txt", "r") as infile:
    data = infile.read().splitlines()
    print(count_xmas(data))
