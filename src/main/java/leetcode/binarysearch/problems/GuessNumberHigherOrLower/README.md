# 374. Guess Number Higher or Lower

## Problem Description

We are playing the Guess Game. The game is as follows:

I pick a number from `1` to `n`. You have to guess which number I picked.

Every time you guess a number, I will tell you whether the number I picked is higher or lower than your guess.

You call a pre-defined API `int guess(int num)`, which returns three possible results:
- `-1`: Your guess is higher than the number I picked (i.e. `num > pick`)
- `1`: Your guess is lower than the number I picked (i.e. `num < pick`)
- `0`: Your guess is correct (i.e. `num == pick`)

Return the number that I picked.

## Examples

**Example 1:**
```
Input: n = 10, pick = 6
Output: 6
Explanation: I pick the number 6.
Your first guess could be:
- guess(8) returns -1 (too high)
- guess(4) returns 1 (too low)  
- guess(6) returns 0 (correct!)
```

**Example 2:**
```
Input: n = 1, pick = 1
Output: 1
Explanation: Only one possible number.
```

**Example 3:**
```
Input: n = 2, pick = 1
Output: 1
Explanation: I pick the number 1.
- guess(2) returns -1 (too high)
- guess(1) returns 0 (correct!)
```

## Constraints

- `1 <= n <= 2^31 - 1`
- `1 <= pick <= n`

## Approach Analysis

### Pattern Recognition

This is a **classic binary search problem** because:
- We have a **sorted search space**: numbers from 1 to n
- We can **eliminate half** the search space with each guess
- The API gives us **direction information** (higher/lower/correct)

### Why Binary Search?

**Brute Force**: Try every number from 1 to n → O(n) time
**Binary Search**: Eliminate half each time → O(log n) time

For n = 2^31 - 1, this means ~31 guesses instead of 2 billion!

### Algorithm Visualization

```
Search space: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], pick = 6

Step 1: guess(5) → returns 1 (too low)
        [1, 2, 3, 4, 5] eliminated
        Search: [6, 7, 8, 9, 10]

Step 2: guess(8) → returns -1 (too high)  
        [8, 9, 10] eliminated
        Search: [6, 7]

Step 3: guess(6) → returns 0 (correct!)
        Found: 6
```

## Solution Approach

### Template: Classic Binary Search

This problem uses the **classic binary search template** because we're looking for an exact match.

```java
while (left <= right) {
    int mid = left + (right - left) / 2;
    int result = guess(mid);
    
    if (result == 0) return mid;      // Found it!
    else if (result == 1) left = mid + 1;   // Too low, search right
    else right = mid - 1;             // Too high, search left
}
```

### Key Insights

1. **API Translation**:
    - `guess(num) == -1` → `num > pick` → search left half
    - `guess(num) == 1` →