def is_monotonic_increasing_or_decreasing(nums):
    """
    Returns True if the list is entirely non-decreasing OR entirely non-increasing.
    Empty lists and single-element lists are considered monotonic.
    """
    if len(nums) <= 1:
        return True

    # Check if non-decreasing
    increasing = all(nums[i] <= nums[i + 1] for i in range(len(nums) - 1))

    # Check if non-increasing
    decreasing = all(nums[i] >= nums[i + 1] for i in range(len(nums) - 1))

    return increasing or decreasing


def adjacent_diff_between_1_and_3(nums):
    """
    Returns True if every pair of adjacent numbers differs by at least 1 and at most 3.
    Returns True for empty lists or single-element lists (no adjacent pairs).
    """
    if len(nums) <= 1:
        return True

    for i in range(len(nums) - 1):
        diff = abs(nums[i] - nums[i + 1])
        if diff < 1 or diff > 3:
            return False
    return True


def is_valid_sequence(nums):
    """
    Returns True if the list is valid:
    - Either the original list satisfies both conditions, OR
    - There exists exactly one index we can remove so that the remaining list does.
    """
    nums = list(nums)  # ensure we work with a list

    # Case 1: Already good without removing anything
    if is_monotonic_increasing_or_decreasing(nums) and adjacent_diff_between_1_and_3(
        nums
    ):
        return True

    # Case 2: Try removing each one element and check
    n = len(nums)
    if n <= 1:
        return True  # single element or empty is always valid

    for i in range(n):
        # Create new list without element at index i
        candidate = nums[:i] + nums[i + 1 :]

        if is_monotonic_increasing_or_decreasing(
            candidate
        ) and adjacent_diff_between_1_and_3(candidate):
            return True

    return False


with open("day2.txt", "r") as infile:
    data = infile.read().splitlines()
    p1 = 0
    p2 = 0

    for line in data:
        xs = [int(x) for x in line.split(" ")]
        p1 += is_monotonic_increasing_or_decreasing(
            xs
        ) and adjacent_diff_between_1_and_3(xs)

        p2 += is_valid_sequence(xs)

    print(p1)
    print(p2)
