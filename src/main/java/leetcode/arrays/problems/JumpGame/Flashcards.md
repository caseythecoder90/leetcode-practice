# Jump Game - Flashcards

## Problem Identification

**Q: How do you recognize this as a greedy problem?**
A: **Key indicators:**
- Return type is boolean (true/false)
- Question asks "can you reach" (feasibility, not optimization)
- Don't need to find the actual path or count paths
- Maximum jump length given (not exact jumps)

**Q: What's the difference between Jump Game I and Jump Game II?**
A:
- **Jump Game I**: "Can you reach the end?" → Boolean → **Greedy (reachability)**
- **Jump Game II**: "Minimum jumps to reach end?" → Integer → **Greedy (BFS-like) or DP**

**Q: Why does greedy work for reachability but not always for optimization?**
A: For reachability, if greedy can't reach a position, no other strategy can either. For optimization, greedy might miss the globally optimal solution by making locally optimal choices.

## Core Algorithm

**Q: What is the greedy strategy for Jump Game?**
A: **Track the farthest reachable position** as you iterate through the array.
```java
int farthest = 0;
for (int i = 0; i < nums.length; i++) {
    if (i > farthest) return false;  // Unreachable
    farthest = Math.max(farthest, i + nums[i]);
    if (farthest >= nums.length - 1) return true;
}
```

**Q: What does the `farthest` variable represent?**
A: The maximum index we can reach from any position we've visited so far. It's a **reachability boundary** - we can reach any position ≤ farthest.

**Q: Why do we check `if (i > farthest) return false`?**
A: If the current position `i` is beyond our farthest reachable position, it means we can't actually get to position `i`, so we definitely can't reach the end.

## Algorithm Walkthrough

**Q: Trace through [2,3,1,1,4] step by step.**
A:
```
i=0: farthest = max(0, 0+2) = 2 (can reach indices 0,1,2)
i=1: farthest = max(2, 1+3) = 4 (can reach indices 0,1,2,3,4)
Since 4 ≥ 4 (last index), return true
```

**Q: Trace through [3,2,1,0,4] step by step.**
A:
```
i=0: farthest = max(0, 0+3) = 3 (can reach 0,1,2,3)
i=1: farthest = max(3, 1+2) = 3 (still 0,1,2,3)
i=2: farthest = max(3, 2+1) = 3 (still 0,1,2,3)
i=3: farthest = max(3, 3+0) = 3 (stuck! nums[3]=0)
i=4: 4 > 3, so position 4 is unreachable → return false
```

**Q: What's the "zero trap" pattern?**
A: Getting stuck at a position with value 0 that you can't jump over.
Example: `[1,0,1]` - from position 0, you can only reach position 1, but nums[1]=0 means you can't jump further.

## Edge Cases and Implementation

**Q: What are the key edge cases to consider?**
A:
1. **Single element**: `[0]` → `true` (already at the end)
2. **Immediate zero**: `[0,1]` → `false` if array length > 1
3. **Large jump**: `[9]` → `true` (can jump way past the end)
4. **All zeros**: `[1,0,0,0]` → `false` (blocked after first step)

**Q: Why do we use `>= nums.length - 1` instead of `>= nums.length`?**
A: Array indices are 0-based, so the last valid index is `nums.length - 1`. We want to check if we can reach or exceed the last valid position.

**Q: What's the time and space complexity?**
A:
- **Time**: O(n) - single pass through the array
- **Space**: O(1) - only tracking one variable (farthest)

## Alternative Approaches

**Q: How does the backward greedy approach work?**
A: Start from the end and work backwards, tracking the leftmost position that can reach the target:
```java
int lastGoodIndex = nums.length - 1;
for (int i = nums.length - 2; i >= 0; i--) {
    if (i + nums[i] >= lastGoodIndex) {
        lastGoodIndex = i;
    }
}
return lastGoodIndex == 0;
```

**Q: When would you use DP instead of greedy for this problem?**
A: **Never for the basic problem!** DP is O(n²) time and O(n) space, much worse than greedy. However, DP might be needed for variations like:
- Count all possible paths to the end
- Find path with minimum cost
- Handle constraints that break the greedy property

**Q: What's wrong with trying to find the actual path?**
A: It adds unnecessary complexity. The problem only asks IF you can reach the end, not HOW. Finding the path would require backtracking or storing parent pointers, making it more complex than needed.

## Pattern Recognition

**Q: How do you identify reachability problems vs optimization problems?**
A:
**Reachability** (use greedy):
- "Can you reach..."
- "Is it possible to..."
- Boolean return type
- Don't need the actual solution path

**Optimization** (use DP/BFS):
- "Minimum/maximum..."
- "Find the best..."
- Integer return type
- May need the actual optimal solution

**Q: What other problems use the "farthest reachable" pattern?**
A:
- Jump Game II (minimum jumps)
- Gas Station (can complete circuit)
- Video Stitching (minimum clips)
- Meeting Rooms (can attend all meetings)

## Common Mistakes

**Q: What's the most common implementation mistake?**
A: Forgetting the early termination check `if (farthest >= nums.length - 1) return true`. Without it, the algorithm still works but is less efficient.

**Q: What's wrong with this approach: "try all possible jump lengths at each position"?**
A: This leads to exponential time complexity O(2^n) because you're exploring all possible paths. The key insight is that we only need to track reachability, not enumerate all paths.

**Q: Why might someone incorrectly think this needs DP?**
A: They might confuse it with Jump Game II (minimum jumps) or think they need to explore all possible paths. The key realization is that this is a feasibility problem, not an optimization problem.

## Interview Strategy

**Q: How should you approach this problem in an interview?**
A:
1. **Clarify**: "We need to determine IF we can reach the end, not find the optimal path"
2. **Pattern**: "This is a reachability problem, perfect for greedy"
3. **Strategy**: "Track the farthest position we can reach"
4. **Complexity**: "O(n) time, O(1) space with early termination"
5. **Test**: Walk through both positive and negative examples

**Q: What should you mention about optimizations?**
A: 
- **Early termination**: Exit immediately when we can reach the end
- **Space efficiency**: Only need one variable, not an array
- **Comparison**: Much better than O(n²) DP or O(2^n) recursive approaches
