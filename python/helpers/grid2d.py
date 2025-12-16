from collections import defaultdict
from typing import Iterable, Tuple, Any


class Grid2D:
    def __init__(self, filepath: str, default: Any = None):
        """
        filepath: text file where each line is a row and each character is a cell
        default: value returned for out-of-bounds or unset cells
        """
        self.default = default
        self.grid = defaultdict(lambda: self.default)
        self.width = 0
        self.height = 0

        with open(filepath, "r", encoding="utf-8") as f:
            for y, line in enumerate(f):
                line = line.rstrip("\n")
                self.width = max(self.width, len(line))
                for x, ch in enumerate(line):
                    self.grid[(x, y)] = ch
                self.height += 1

    # ------------------------------------------------------------------
    # Cell access
    # ------------------------------------------------------------------

    def get(self, x: int, y: int) -> Any:
        return self.grid[(x, y)]

    def set(self, x: int, y: int, value: Any) -> None:
        self.grid[(x, y)] = value

    # ------------------------------------------------------------------
    # Neighbors
    # ------------------------------------------------------------------

    def neighbors4(self, x: int, y: int) -> Iterable[Tuple[int, int, Any]]:
        """Up, Down, Left, Right"""
        directions = [
            (0, -1),
            (0, 1),
            (-1, 0),
            (1, 0),
        ]
        for dx, dy in directions:
            nx, ny = x + dx, y + dy
            yield nx, ny, self.grid[(nx, ny)]

    def neighbors8(self, x: int, y: int) -> Iterable[Tuple[int, int, Any]]:
        """4-neighbors plus diagonals"""
        for dy in (-1, 0, 1):
            for dx in (-1, 0, 1):
                if dx == 0 and dy == 0:
                    continue
                nx, ny = x + dx, y + dy
                yield nx, ny, self.grid[(nx, ny)]

    # ------------------------------------------------------------------
    # Row / column rotation (cyclic)
    # ------------------------------------------------------------------

    def rotate_row(self, y: int, steps: int = 1) -> None:
        """Rotate row y to the right by `steps`"""
        steps %= self.width
        row = [self.grid[(x, y)] for x in range(self.width)]
        row = row[-steps:] + row[:-steps]
        for x, value in enumerate(row):
            self.grid[(x, y)] = value

    def rotate_column(self, x: int, steps: int = 1) -> None:
        """Rotate column x downward by `steps`"""
        steps %= self.height
        col = [self.grid[(x, y)] for y in range(self.height)]
        col = col[-steps:] + col[:-steps]
        for y, value in enumerate(col):
            self.grid[(x, y)] = value


    def pretty_print(self) -> None:
        for y in range(self.height):
            row = "".join(str(self.grid[(x, y)]) for x in range(self.width))
            print(row)
