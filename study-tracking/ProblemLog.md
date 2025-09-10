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
- Count: 13
- Mastered: 1

### Medium Problems 🟡
- Count: 22
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

## [380] Insert Delete GetRandom O(1)
- **Difficulty**: Medium
- **Pattern**: Design/HashMap/Array
- **First Attempt**: 2025-08-24
- **Status**: ✅ Solved
- **Attempts**: 2 (fixed implementation with AI assistance)
- **Solution Time**: ~60 minutes total
- **Notes**: Original solution failed on large test case due to removing from map but not from list. Learned critical "swap-with-last" technique for O(1) removal from ArrayList. HashMap + ArrayList combination is key insight.
- **Approach**: HashMap (value→index) + ArrayList (values), swap element to remove with last element before removing from end
- **Time Complexity**: O(1) average for all operations
- **Space Complexity**: O(n)
- **Tags**: design-data-structure, hashmap-arraylist-combo, swap-with-last-technique, o1-operations

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-08-24 | 30 min | HashMap + ArrayList (incorrect remove) | ❌ | Failed test case - only removed from map, not list |
| 2025-08-24 | 30 min | HashMap + ArrayList (swap-with-last) | ✅ | Correct implementation learned with AI assistance |

---

## [274] H-Index
- **Difficulty**: Medium
- **Pattern**: Arrays/Sorting
- **First Attempt**: Recent (date unknown)
- **Status**: ✅ Solved
- **Attempts**: 1+
- **Solution Time**: ~30 minutes (estimated)
- **Notes**: Array problem with comprehensive study materials created. Multiple solution approaches available in repository.
- **Approach**: Sort array in descending order, find first position where citations[i] <= i+1
- **Time Complexity**: O(n log n)
- **Space Complexity**: O(1)
- **Tags**: arrays, sorting, h-index, comprehensive-study-materials

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| Recent | ~30 min | Sorting approach | ✅ | Completed with study materials |

---

## [45] Jump Game II
- **Difficulty**: Medium  
- **Pattern**: Arrays/Greedy
- **First Attempt**: Recent (date unknown)
- **Status**: ✅ Solved
- **Attempts**: 1+
- **Solution Time**: ~45 minutes (estimated)
- **Notes**: Advanced greedy problem with comprehensive study materials. Includes DP vs Greedy comparison and pattern recognition guides.
- **Approach**: Greedy approach tracking current reach and farthest reach with jump counting
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: arrays, greedy, jump-problems, advanced-greedy, dp-vs-greedy-comparison

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| Recent | ~45 min | Greedy approach | ✅ | Completed with comprehensive study materials |

---

## [189] Rotate Array
- **Difficulty**: Medium
- **Pattern**: Arrays/Cyclic Replacement
- **First Attempt**: Recent (date unknown)
- **Status**: ✅ Solved
- **Attempts**: 1+
- **Solution Time**: ~40 minutes (estimated)  
- **Notes**: Multiple solution approaches with detailed study materials. Covers reverse method and cyclic replacement techniques.
- **Approach**: Multiple approaches - reverse method and cyclic replacement
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: arrays, rotation, cyclic-replacement, reverse-method, multiple-approaches

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| Recent | ~40 min | Multiple approaches | ✅ | Comprehensive study materials created |

---

## [134] Gas Station
- **Difficulty**: Medium
- **Pattern**: Arrays/Greedy
- **First Attempt**: 2025-08-24
- **Status**: ✅ Solved (with assistance)
- **Attempts**: 2 (original solution had bugs, fixed with optimal approach)
- **Solution Time**: ~90 minutes total
- **Notes**: Original brute force approach had logical issues with loop conditions and state management. Learned optimal O(n) greedy solution with mathematical insight. Key: if sum(gas) >= sum(cost), exactly one solution exists.
- **Approach**: Optimal greedy - track cumulative balance, reset start position when balance goes negative
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: greedy-algorithm, circular-array, mathematical-insight, debugging-practice, optimal-solution-learning

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-08-24 | 45 min | Brute force (buggy) | ❌ | Loop condition and state management issues |
| 2025-08-24 | 45 min | Optimal greedy | ✅ | Learned mathematical insight with AI assistance |

---

## [6] Zigzag Conversion
- **Difficulty**: Medium
- **Pattern**: String/Simulation/Pattern Recognition
- **First Attempt**: 2025-08-27
- **Status**: ✅ Solved
- **Attempts**: 1 (learned with comprehensive explanation and visualization)
- **Solution Time**: ~90 minutes total (including study materials)
- **Notes**: Initially confusing pattern, but clear once visualized. Key insights: zigzag movement with direction changes at boundaries, cycle length = 2×numRows-2. Implemented both StringBuilder array and mathematical approaches.
- **Approach**: StringBuilder array simulation (intuitive) and mathematical pattern calculation (optimal)
- **Time Complexity**: O(n)
- **Space Complexity**: O(n) for array approach, O(1) for mathematical
- **Tags**: string-simulation, pattern-recognition, directional-movement, cycle-detection, visualization-helpful

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-08-27 | 90 min | Both StringBuilder and Math | ✅ | Comprehensive understanding with visualization and study materials |

---

## [238] Product of Array Except Self
- **Difficulty**: Medium
- **Pattern**: Arrays/Prefix Products
- **First Attempt**: 2025-09-04 (22 hours ago)
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 2 ms runtime
- **Notes**: Already had comprehensive solution materials in repository. Optimal O(n) solution using left and right products with O(1) extra space (not counting output array).
- **Approach**: Two-pass algorithm - store left products in result array, then calculate right products on-the-fly
- **Time Complexity**: O(n)
- **Space Complexity**: O(1) extra space
- **Tags**: arrays, prefix-products, two-pass-algorithm, optimal-space, existing-solution

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-09-04 | Submitted only | Left/Right products | ✅ | 2 ms runtime, solution already existed in repo |

---

## [14] Longest Common Prefix
- **Difficulty**: Easy
- **Pattern**: String Processing/Vertical Scanning
- **First Attempt**: 2025-09-04 (23 hours ago)
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: 1 ms runtime
- **Notes**: String processing problem using vertical scanning approach. Compare characters column by column across all strings until first mismatch found.
- **Approach**: Vertical scanning - use first string as reference, compare character by character with all other strings
- **Time Complexity**: O(S) where S is sum of all characters in all strings
- **Space Complexity**: O(1) extra space
- **Tags**: string-processing, vertical-scanning, character-comparison, early-termination

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-09-04 | Submitted only | Vertical scanning | ✅ | 1 ms runtime, clean implementation |

---

## [13] Roman to Integer
- **Difficulty**: Easy
- **Pattern**: String Processing/Hash Map
- **First Attempt**: 2025-09-04 (23 hours ago)
- **Status**: ✅ Solved
- **Attempts**: 3 (improved from 11 ms to 7 ms)
- **Solution Time**: 7 ms final runtime
- **Notes**: String processing with subtractive cases. Key insight: traverse right-to-left, if current value < previous value then subtract (subtractive case), else add.
- **Approach**: Right-to-left traversal with HashMap lookup, handle subtractive cases (IV, IX, XL, XC, CD, CM)
- **Time Complexity**: O(n)
- **Space Complexity**: O(1) - fixed size hash map
- **Tags**: string-processing, hashmap-lookup, subtractive-cases, right-to-left-traversal, roman-numerals

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-09-04 | Submitted | Right-to-left HashMap | ✅ | 11 ms runtime, initial solution |
| 2025-09-04 | Submitted | Right-to-left HashMap | ✅ | 7 ms runtime, optimized |
| 2025-09-04 | Submitted | Right-to-left HashMap | ✅ | 7 ms runtime, final submission |

---

## [12] Integer to Roman
- **Difficulty**: Medium
- **Pattern**: String Building/Greedy Algorithm
- **First Attempt**: 2025-09-04 (estimated)
- **Status**: ✅ Solved (Independent Solution!)
- **Attempts**: 1 (self-solved, then optimized)
- **Solution Time**: Self-solved independently
- **Notes**: Excellent independent solution! Used smart first digit check (4 or 9) to separate basic symbols from subtractive cases. Working solution with clean structure, then learned greedy optimization. Shows significant problem-solving improvement.
- **Approach**: Original - separate mappings with first digit check; Optimized - combined greedy approach
- **Time Complexity**: O(1) - maximum 13 symbols possible
- **Space Complexity**: O(1) - fixed size data structures
- **Tags**: independent-solution, first-digit-check, greedy-optimization, problem-solving-growth, string-building

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-09-04 | Self-solved | First digit check with dual mappings | ✅ | Independent working solution - major milestone! |
| 2025-09-04 | Study session | Learned greedy optimization | ✅ | Combined mappings for cleaner approach |

---

## [345] Reverse Vowels of a String
- **Difficulty**: Easy
- **Pattern**: Two Pointers
- **First Attempt**: 2025-09-05 (estimated, from review)
- **Status**: ✅ Solved (Memorized/Mastered)
- **Attempts**: 1 (already mastered)
- **Solution Time**: Already known - memorized solution
- **Notes**: Excellent optimal solution! Perfect two-pointer implementation with Set lookup. Already interview-ready code with optimal O(n) time and space complexity. Planning multiple approach practice for interview preparation.
- **Approach**: Two pointers from ends, Set for O(1) vowel lookup, in-place swapping
- **Time Complexity**: O(n)
- **Space Complexity**: O(n) for char array (unavoidable due to String immutability)
- **Tags**: two-pointers, memorized-solution, optimal-implementation, interview-ready, multiple-approach-practice

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-09-05 | Review only | Two pointers with Set lookup | ✅ | Already mastered - optimal solution, planning multi-approach practice |

---

## [125] Valid Palindrome
- **Difficulty**: Easy
- **Pattern**: Two Pointers
- **First Attempt**: 2025-09-06
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: ~20 minutes
- **Notes**: Classic two pointer problem for string validation. Used left and right pointers converging toward middle, skipping non-alphanumeric characters and comparing case-insensitive.
- **Approach**: Two pointers from ends, skip invalid characters, compare normalized characters
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: two-pointers, string-validation, case-insensitive, character-filtering

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-09-06 | ~20 min | Two pointers with filtering | ✅ | Clean implementation, handled edge cases well |

---

## [392] Is Subsequence
- **Difficulty**: Easy
- **Pattern**: Two Pointers
- **First Attempt**: 2025-09-07
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: ~15 minutes
- **Notes**: Two pointer tracking problem. One pointer for subsequence string, one for main string. Advance subsequence pointer only when characters match.
- **Approach**: Two pointers tracking through both strings, advance subsequence pointer on match
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: two-pointers, subsequence-matching, string-tracking, greedy-matching

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-09-07 | ~15 min | Two pointer tracking | ✅ | Straightforward implementation, good pattern recognition |

---

## [167] Two Sum II - Input Array Is Sorted
- **Difficulty**: Medium
- **Pattern**: Two Pointers
- **First Attempt**: 2025-09-08
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: ~25 minutes
- **Notes**: Binary search variant using two pointers. Takes advantage of sorted array property - if sum too small move left pointer right, if too large move right pointer left.
- **Approach**: Two pointers from ends, adjust based on sum comparison with target
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Tags**: two-pointers, sorted-array, binary-search-variant, sum-problems

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-09-08 | ~25 min | Two pointers with sum comparison | ✅ | Excellent use of sorted array property |

---

## [15] 3Sum
- **Difficulty**: Medium
- **Pattern**: Two Pointers
- **First Attempt**: 2025-09-09
- **Status**: ✅ Solved
- **Attempts**: 1
- **Solution Time**: ~45 minutes
- **Notes**: Complex two pointer problem requiring sorting and duplicate handling. For each element, use two pointers to find complementary pair. Skip duplicates at all levels to avoid duplicate triplets.
- **Approach**: Sort array, fix first element, use two pointers for remaining pair, skip duplicates
- **Time Complexity**: O(n²)
- **Space Complexity**: O(1) excluding output array
- **Tags**: two-pointers, triplet-finding, duplicate-handling, sorting-required, complex-two-pointers

### Attempt History
| Date | Time Taken | Approach | Result | Notes |
|------|------------|----------|--------|-------|
| 2025-09-09 | ~45 min | Sorted array + two pointers | ✅ | Challenging problem, handled duplicates correctly |

---