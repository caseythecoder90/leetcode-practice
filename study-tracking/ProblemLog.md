# LeetCode Problem Log

## How to Use This Log

This file tracks your progress on individual LeetCode problems. For each problem you attempt, add an entry with the following format:

### Problem Template
```markdown
## [Problem Number] Problem Name
- **Difficulty**: Easy/Medium/Hard
- **Pattern**: [Pattern Name] (e.g., Two Pointers, DP, etc.)
- **First Attempt**: YYYY-MM-DD
- **Status**: ✅ Solved | ❌ Need Review | 🔄 In Progress | ⭐ Mastered
- **Attempts**: [Number of times attempted]
- **Solution Time**: [Time taken to solve]
- **Notes**: [Your observations, key insights, mistakes made]
- **Approach**: [Brief description of your solution approach]
- **Time Complexity**: O(?)
- **Space Complexity**: O(?)
- **Tags**: [Additional tags like "interview-favorite", "tricky", "easy-win"]

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| YYYY-MM-DD | X min | [approach] | ✅/❌ | [notes] |

---
```

## Problems by Difficulty

### Easy Problems ✅
- Count: 0
- Mastered: 0

### Medium Problems 🟡
- Count: 0
- Mastered: 0

### Hard Problems 🔴
- Count: 0
- Mastered: 0

---

## Problem Entries

<!-- Add your problem entries below this line -->

## [1] Two Sum
- **Difficulty**: Easy
- **Pattern**: HashMap/Hash Table
- **First Attempt**: 2024-01-15
- **Status**: ⭐ Mastered
- **Attempts**: 3
- **Solution Time**: 8 minutes (latest attempt)
- **Notes**: Classic problem - remember to use HashMap for O(n) solution instead of brute force O(n²)
- **Approach**: Use HashMap to store complement values and their indices
- **Time Complexity**: O(n)
- **Space Complexity**: O(n)
- **Tags**: interview-favorite, fundamental, easy-win

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2024-01-15 | 25 min | Brute force nested loops | ❌ | TLE on large inputs |
| 2024-01-18 | 15 min | HashMap lookup | ✅ | Much faster, got the concept |
| 2024-01-22 | 8 min | HashMap (review) | ✅ | Solid understanding now |

---

## [739] Daily Temperatures
- **Difficulty**: Medium
- **Pattern**: Monotonic Stack
- **First Attempt**: 2024-01-20
- **Status**: ✅ Solved
- **Attempts**: 2
- **Solution Time**: 35 minutes (latest attempt)
- **Notes**: Good example of monotonic decreasing stack. Key insight: when temp[i] > temp[stack.top()], we found the answer for stack.top()
- **Approach**: Use decreasing monotonic stack to track indices, calculate distance when popping
- **Time Complexity**: O(n)
- **Space Complexity**: O(n)
- **Tags**: monotonic-stack, temperature-series, medium-practice

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2024-01-20 | 50 min | Brute force then stack | ✅ | Struggled with stack logic initially |
| 2024-01-25 | 35 min | Monotonic stack | ✅ | Much cleaner implementation |

---

## [901] Online Stock Span
- **Difficulty**: Medium
- **Pattern**: Monotonic Stack
- **First Attempt**: 2025-07-11
- **Status**: ✅ Solved
- **Attempts**: 2
- **Solution Time**: 45 minutes total
- **Notes**: First solved with for loop approach on my own but not optimal. Then studied the stack solution and implemented with AI assistance to understand the pattern better
- **Approach**: Initially used for loop, then learned monotonic decreasing stack to track [price, span] pairs
- **Time Complexity**: O(n) amortized (stack), O(n²) worst case (for loop)
- **Space Complexity**: O(n)
- **Tags**: monotonic-stack, stock-analysis, medium-practice, learning-progression

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-07-11 | 20 min | For loop (brute force) | ✅ | Worked but not optimal, solved independently |
| 2025-07-11 | 25 min | Monotonic stack | ✅ | Better solution with AI assistance after studying pattern |
| 2025-07-11 | 15 min | Review and practice | ✅ | Reviewed problem and implemented again for reinforcement |

---

## [435] Non-overlapping Intervals
- **Difficulty**: Medium
- **Pattern**: Intervals/Greedy
- **First Attempt**: 2025-07-12
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 30 minutes
- **Notes**: Greedy approach - sort by end time and count overlapping intervals to remove. Understanding concepts but struggle to pick correct strategy initially.
- **Approach**: Sort intervals by end time, track last non-overlapping interval, count removals needed
- **Time Complexity**: O(n log n)
- **Space Complexity**: O(1)
- **Tags**: intervals, greedy-algorithm, medium-practice, strategy-selection-challenge

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-07-12 | 30 min | Greedy (sort by end) | ✅ | Clean solution on first attempt |

---

## [452] Minimum Number of Arrows to Burst Balloons
- **Difficulty**: Medium
- **Pattern**: Intervals/Greedy Point Coverage
- **First Attempt**: 2025-07-12
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 25 minutes
- **Notes**: Similar greedy strategy to Non-overlapping Intervals. Sort by end position and count minimum arrows. Concepts clear but strategy selection remains challenging.
- **Approach**: Sort balloons by end position, track arrow positions, count minimum arrows needed
- **Time Complexity**: O(n log n)
- **Space Complexity**: O(1)
- **Tags**: intervals, greedy-algorithm, point-coverage, medium-practice, strategy-selection-challenge

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-07-12 | 25 min | Greedy (sort by end) | ✅ | Applied pattern from previous intervals problem |

---

## [1161] Maximum Level Sum of a Binary Tree
- **Difficulty**: Medium
- **Pattern**: Trees/BFS (Breadth-First Search)
- **First Attempt**: 2025-04-08
- **Status**: ✅ Solved
- **Attempts**: 2
- **Solution Time**: 20 minutes (latest attempt)
- **Notes**: Second time solving - after reviewing BFS traversal pattern, was able to solve independently. Need to memorize core patterns and data structures for tree problems.
- **Approach**: Level-order traversal using Queue, calculate sum for each level, track maximum
- **Time Complexity**: O(n)
- **Space Complexity**: O(w) where w is maximum width of tree
- **Tags**: trees, bfs, level-order-traversal, pattern-memorization-needed, repeat-solve

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-04-08 | 35 min | BFS with Queue | ✅ | First time solving - took longer to work through the logic |
| 2025-07-13 | 20 min | BFS with Queue | ✅ | Second solve after BFS pattern review - faster implementation |

---