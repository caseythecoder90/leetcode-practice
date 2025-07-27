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
- Count: 7
- Mastered: 1

### Medium Problems 🟡
- Count: 11
- Mastered: 0

### Hard Problems 🔴
- Count: 0
- Mastered: 0

### SQL Problems 🗄️
- Count: 1
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

## [199] Binary Tree Right Side View
- **Difficulty**: Medium
- **Pattern**: Trees/BFS (Breadth-First Search)
- **First Attempt**: 2025-07-15
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 13 minutes
- **Notes**: Solved on first try after reviewing Binary Tree BFS notes. Pattern recognition was strong - immediately saw this as level-order traversal with rightmost node capture. Next attempt should use DFS approach for variety.
- **Approach**: Level-order BFS traversal, capture only the last (rightmost) node from each level
- **Time Complexity**: O(n)
- **Space Complexity**: O(w) where w is maximum width of tree
- **Tags**: trees, bfs, level-order-traversal, pattern-recognition-success, try-dfs-next-time

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-07-15 | 13 min | BFS Level Order | ✅ | Quick solve with BFS pattern recognition, included DFS alternative for future practice |

---

## [102] Binary Tree Level Order Traversal
- **Difficulty**: Medium
- **Pattern**: Trees/BFS (Breadth-First Search)
- **First Attempt**: 2025-07-16
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 15 minutes
- **Notes**: Classic BFS level-order traversal problem. Used Queue to process nodes level by level, collecting values for each level in separate lists.
- **Approach**: BFS with Queue, process each level completely before moving to next level
- **Time Complexity**: O(n)
- **Space Complexity**: O(w) where w is maximum width of tree
- **Tags**: trees, bfs, level-order-traversal, classic-problem

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-07-16 | 15 min | BFS Level Order | ✅ | Standard BFS implementation |

---

## [111] Minimum Depth of Binary Tree
- **Difficulty**: Easy
- **Pattern**: Trees/BFS (Breadth-First Search)
- **First Attempt**: 2025-07-16
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 10 minutes
- **Notes**: BFS approach is optimal for finding minimum depth as it stops at first leaf node found. Could also use DFS but BFS is more efficient for this specific problem.
- **Approach**: BFS traversal, return depth when first leaf node is encountered
- **Time Complexity**: O(n) worst case, but often much better
- **Space Complexity**: O(w) where w is maximum width of tree
- **Tags**: trees, bfs, minimum-depth, early-termination, easy-win

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-07-16 | 10 min | BFS Early Termination | ✅ | Efficient BFS solution |

---

## [226] Invert Binary Tree
- **Difficulty**: Easy
- **Pattern**: Trees/DFS (Depth-First Search)
- **First Attempt**: 2025-07-16
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 8 minutes
- **Notes**: Simple recursive DFS problem. Swap left and right children for each node, then recursively invert both subtrees.
- **Approach**: Recursive DFS, swap left/right children at each node
- **Time Complexity**: O(n)
- **Space Complexity**: O(h) where h is height of tree
- **Tags**: trees, dfs, recursion, tree-manipulation, easy-win

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-07-16 | 8 min | Recursive DFS | ✅ | Clean recursive implementation |

---

## [103] Binary Tree Zigzag Level Order Traversal
- **Difficulty**: Medium
- **Pattern**: Trees/BFS (Breadth-First Search)
- **First Attempt**: 2025-07-16
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 5 minutes
- **Notes**: Quick solve using BFS with alternating direction flag. Used Collections.reverse() to handle right-to-left traversal for odd levels. Pattern recognition was immediate after practicing other BFS problems.
- **Approach**: Standard BFS level-order traversal with direction alternation per level
- **Time Complexity**: O(n)
- **Space Complexity**: O(w) where w is maximum width of tree
- **Tags**: trees, bfs, zigzag-traversal, level-order-traversal, pattern-mastery

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-07-16 | 5 min | BFS with direction flag | ✅ | Fast solve with established BFS pattern knowledge |

---

## [27] Remove Element
- **Difficulty**: Easy
- **Pattern**: Arrays/Two Pointers
- **First Attempt**: 2025-07-23
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 20 minutes
- **Notes**: Solved independently but made initial mistakes. Used AI assistance to learn optimal fast/slow pointer approach. Good practice for in-place array modification pattern.
- **Approach**: Two pointers technique - fast pointer scans array, slow pointer tracks position for valid elements
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: arrays, two-pointers, in-place-modification, learning-progression, easy-win

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|---------|
| 2025-07-23 | 20 min | Two pointers | ✅ | First attempt with corrections, learned optimal fast/slow pointer solution |
| 2025-07-24 | 15 min | Two pointers (review) | ✅ | Review attempt, implemented with AI-suggested optimization approach |

---

## [26] Remove Duplicates from Sorted Array
- **Difficulty**: Easy
- **Pattern**: Arrays/Two Pointers
- **First Attempt**: 2025-07-24
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 25 minutes (estimated)
- **Notes**: Completed successfully. Another two pointers in-place modification problem similar to Remove Element.
- **Approach**: Two pointers technique - slow pointer tracks position for unique elements, fast pointer scans array
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: arrays, two-pointers, in-place-modification, sorted-array, easy-win

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|---------|
| 2025-07-24 | ~25 min | Two pointers | ✅ | Successfully completed, building confidence with two pointer pattern |

---

## [151] Reverse Words in a String
- **Difficulty**: Medium
- **Pattern**: String Manipulation
- **First Attempt**: 2025-07-24
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 30 minutes (estimated)
- **Notes**: Completed successfully. String processing problem involving trimming, splitting, and reversing.
- **Approach**: Split string into words, filter out empty strings, reverse array and join
- **Time Complexity**: O(n)
- **Space Complexity**: O(n)
- **Tags**: strings, string-processing, reverse-operations, medium-practice

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|---------|
| 2025-07-24 | ~30 min | String split and reverse | ✅ | Successful completion of string manipulation problem |

---

## [80] Remove Duplicates from Sorted Array II
- **Difficulty**: Medium
- **Pattern**: Arrays/Two Pointers
- **First Attempt**: 2025-07-24
- **Status**: ✅ Solved
- **Attempts**: 2
- **Solution Time**: 35 minutes (estimated, second attempt)
- **Notes**: Got stuck initially when tired, but successfully completed after returning with fresh mind. Complex variation allowing up to 2 duplicates requires careful counting logic.
- **Approach**: Two pointers with counting logic - track position for valid elements allowing up to 2 occurrences
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: arrays, two-pointers, in-place-modification, sorted-array, duplicate-handling, persistence-pays-off

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|---------|
| 2025-07-24 | N/A | Attempted two pointers | ❌ | Got stuck when tired, need to revisit with fresh mind |
| 2025-07-25 | ~35 min | Two pointers with counting | ✅ | Successfully completed after rest, counting logic for duplicates worked |

---

## [283] Move Zeroes
- **Difficulty**: Easy
- **Pattern**: Arrays/Two Pointers
- **First Attempt**: 2025-07-25
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 20 minutes (estimated)
- **Notes**: In-place array modification problem. Move all zeros to end while maintaining relative order of non-zero elements.
- **Approach**: Two pointers - one for writing position of non-zero elements, one for scanning
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: arrays, two-pointers, in-place-modification, element-rearrangement, easy-win

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|---------|
| 2025-07-25 | ~20 min | Two pointers write/scan | ✅ | Successfully completed, good pattern practice |

---

## [238] Product of Array Except Self
- **Difficulty**: Medium
- **Pattern**: Arrays/Prefix Sum
- **First Attempt**: Previous attempt (date unknown)
- **Status**: ✅ Solved (Review)
- **Attempts**: 2+ (struggled previously, solved successfully on review)
- **Solution Time**: ~20 minutes (including 5 min debug + 1 min note refresh)
- **Notes**: Had struggled with this problem previously. Needed brief note refresh on left/right products array approach, then solved successfully. Had variable mix-up (i vs n) in update statement that cost 5 extra minutes. Should learn alternative approach next time for more robust understanding.
- **Approach**: Left and right products arrays - compute left products, then right products while building final result
- **Time Complexity**: O(n)
- **Space Complexity**: O(1) extra space (not counting output array)
- **Tags**: arrays, prefix-sum, product-computation, review-success, learn-alternative-approach

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|---------|
| Previous | N/A | Unknown approach | ❌ | Struggled with this problem initially |
| 2025-07-26 | ~20 min | Left/right products | ✅ | Solved after brief note refresh, had variable mix-up debug |

---

## [169] Majority Element
- **Difficulty**: Easy
- **Pattern**: Arrays/Hash Table
- **First Attempt**: 2025-07-26
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 10 minutes
- **Notes**: Solved quickly using HashMap approach. Good pattern recognition for frequency counting problems. Confident with HashMap solutions and can usually get working solution with this approach.
- **Approach**: HashMap to count frequencies, return element with count > n/2
- **Time Complexity**: O(n)
- **Space Complexity**: O(n)
- **Tags**: arrays, hashmap, frequency-counting, majority-element, quick-solve, hashmap-strength

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|---------|
| 2025-07-26 | 10 min | HashMap frequency counting | ✅ | Quick solve, good HashMap pattern recognition |

---

## [1757] Recyclable and Low Fat Products
- **Difficulty**: Easy
- **Pattern**: SQL/Database
- **First Attempt**: 2025-07-24
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 15 minutes (estimated)
- **Notes**: SQL problem focusing on filtering with multiple conditions. Part of expanding interview prep to include database skills.
- **Approach**: SELECT with WHERE clause using AND condition
- **Time Complexity**: N/A (SQL)
- **Space Complexity**: N/A (SQL)
- **Tags**: sql, database, filtering, multiple-conditions, interview-prep-expansion

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|---------|
| 2025-07-24 | ~15 min | SQL WHERE clause | ✅ | Successfully completed SQL problem, expanding skill set |

---