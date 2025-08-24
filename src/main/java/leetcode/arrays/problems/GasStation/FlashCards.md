# Gas Station - Flash Cards

## Problem Recognition Cards

### Card 1: Problem Pattern Recognition
**Q:** What are the key signs that indicate a "Gas Station" type problem?

**A:** 
- Circular route/array mentioned
- "Can complete the circuit once" phrasing
- Need to find optimal starting position  
- Balance/resource tracking required
- Given two arrays (resources and costs)

---

### Card 2: Core Mathematical Insight
**Q:** What is the fundamental mathematical condition for a solution to exist?

**A:** 
**If `sum(gas) >= sum(cost)`, then exactly one valid starting position exists.**
- Necessary condition: Total resources ≥ Total requirements
- Sufficiency: Guaranteed by problem statement (uniqueness)

---

## Algorithm Understanding Cards

### Card 3: Greedy Choice Principle
**Q:** Why can we use a greedy approach? What's the key insight?

**A:** 
**If we can't reach station j from station i, then we can't reach j from any station between i and j-1.**
- Reason: Intermediate stations only add positive gas
- If more gas still can't reach j, less gas definitely can't
- Therefore: Skip all intermediate stations, try j+1

---

### Card 4: Optimal Algorithm Template
**Q:** Write the O(n) optimal solution template.

**A:**
```java
public int canCompleteCircuit(int[] gas, int[] cost) {
    int totalBalance = 0, currentBalance = 0, startStation = 0;
    
    for (int i = 0; i < gas.length; i++) {
        int balance = gas[i] - cost[i];
        totalBalance += balance;
        currentBalance += balance;
        
        if (currentBalance < 0) {
            startStation = i + 1;
            currentBalance = 0;
        }
    }
    
    return totalBalance >= 0 ? startStation : -1;
}
```

---

### Card 5: Why Reset currentBalance to 0?
**Q:** In the greedy algorithm, why do we reset `currentBalance = 0` when it becomes negative?

**A:** 
- We're starting a fresh journey from the next station
- Previous accumulated gas is irrelevant to the new starting point
- We begin with an empty tank at each new candidate start position

---

## Implementation Detail Cards

### Card 6: Circular Array Indexing
**Q:** How do you properly handle circular array indexing?

**A:**
```java
// For visiting all n elements starting from position start:
for (int count = 0; count < n; count++) {
    int currentIndex = (start + count) % n;
    // Process element at currentIndex
}

// For simple next position:
int nextIndex = (currentIndex + 1) % arrayLength;
```

---

### Card 7: Brute Force vs Optimal
**Q:** What are the time complexities and when should you use each approach?

**A:**
- **Brute Force O(n²)**: Try each starting position, simulate full journey
- **Optimal Greedy O(n)**: Single pass with mathematical insight
- **Use brute force for**: Learning, debugging, verification
- **Use optimal for**: Interviews, contests, production

---

### Card 8: State Tracking Variables
**Q:** What are the three key variables in the optimal solution and what does each track?

**A:**
- **`totalBalance`**: Overall feasibility (sum of all gas[i] - cost[i])
- **`currentBalance`**: Current journey status from current candidate
- **`startStation`**: Current candidate for optimal starting position

---

## Common Mistakes Cards

### Card 9: Wrong Loop Condition
**Q:** What's wrong with this loop condition and how do you fix it?
```java
while (currentGas >= 0 && nextStation != startStation)
```

**A:**
**Problems:**
- `nextStation` creates unnecessary complexity
- Doesn't properly track circuit completion
- Can exit early without completing circuit

**Fix:**
```java
for (int visited = 0; visited < n; visited++) {
    int station = (start + visited) % n;
    // Clear progress tracking
}
```

---

### Card 10: State Reset Timing
**Q:** What's the difference between these two approaches to resetting state?
```java
// Approach A
if (failed) {
    currentGas = 0;
    start++;
}

// Approach B  
for (int start = 0; start < n; start++) {
    int currentGas = 0;
    // attempt logic
}
```

**A:**
- **Approach A**: Reset after failure (can cause bugs)
- **Approach B**: Fresh state for each attempt (correct)
- **Key**: Initialize clean state before each new attempt

---

### Card 11: Missing Feasibility Check
**Q:** Why is this return statement incorrect?
```java
return startStation;  // Missing something!
```

**A:**
**Missing the fundamental feasibility check:**
```java
return totalBalance >= 0 ? startStation : -1;
```
**Always verify** that total resources ≥ total requirements, even if you found a candidate.

---

## Conceptual Understanding Cards

### Card 12: Why One Pass Works
**Q:** Explain why the optimal algorithm only needs one pass through the array.

**A:**
1. **Mathematical guarantee**: If solution exists, it's unique
2. **Greedy elimination**: When we fail at position i, we can eliminate all start positions from 0 to i
3. **Forward progress**: Each failure moves our candidate start position forward
4. **Total balance check**: Confirms overall feasibility

---

### Card 13: Trace Through Example
**Q:** Trace through the optimal algorithm with `gas=[1,2,3,4,5]`, `cost=[3,4,5,1,2]`.

**A:**
```
i=0: balance=-2, currentBalance=-2<0 → start=1, reset=0
i=1: balance=-2, currentBalance=-2<0 → start=2, reset=0
i=2: balance=-2, currentBalance=-2<0 → start=3, reset=0
i=3: balance=+3, currentBalance=+3≥0 → continue
i=4: balance=+3, currentBalance=+6≥0 → continue

totalBalance = 0 ≥ 0 → return start=3
```

---

### Card 14: Edge Cases
**Q:** What are the important edge cases to test?

**A:**
1. **Single station**: `gas=[5], cost=[4]` → 0
2. **No solution**: `gas=[2,3,4], cost=[3,4,3]` → -1
3. **Solution at last position**: Test start near end
4. **Exact balance**: Finish with exactly 0 gas
5. **All negative balances except one**: Edge case testing

---

## Problem Variant Cards

### Card 15: What if Multiple Solutions Existed?
**Q:** If the problem didn't guarantee uniqueness, how would the algorithm behave?

**A:**
- The algorithm would return the **first valid starting position** encountered
- Due to left-to-right scanning, this would be the position with the smallest index
- The mathematical property still holds for finding **any** valid solution

---

### Card 16: What if We Wanted All Solutions?
**Q:** How would you modify the algorithm to find all valid starting positions?

**A:**
**Cannot use the greedy optimization!** Must use brute force:
```java
List<Integer> validStarts = new ArrayList<>();
for (int start = 0; start < n; start++) {
    if (canCompleteFrom(start)) {
        validStarts.add(start);
    }
}
```

---

## Complexity Analysis Cards

### Card 17: Space Complexity Analysis
**Q:** What is the space complexity of both approaches and why?

**A:**
- **Both O(1)**: Only use a constant number of variables
- **Optimal**: 3 variables (totalBalance, currentBalance, startStation)
- **Brute Force**: 2-3 variables per iteration (currentGas, visited)
- **No additional data structures** needed

---

### Card 18: Time Complexity Proof
**Q:** Prove that the optimal algorithm is O(n) time complexity.

**A:**
- **Single loop**: We iterate through array exactly once
- **Constant work per iteration**: Just arithmetic operations and comparisons
- **No nested loops**: Unlike brute force which has O(n) inner simulation
- **Total**: n iterations × O(1) work = O(n)

---

## Interview Strategy Cards

### Card 19: Approach Progression
**Q:** How should you approach this problem in an interview setting?

**A:**
1. **Clarify problem**: Understand circular nature, uniqueness guarantee
2. **Brute force first**: "I could try every starting position..." (show understanding)
3. **Identify optimization**: "But I notice a pattern..." (mathematical insight)
4. **Implement optimal**: Single-pass greedy solution
5. **Test edge cases**: Verify with multiple test cases

---

### Card 20: Follow-up Questions
**Q:** What are common follow-up questions for this problem?

**A:**
1. **"What if costs could be 0?"** → Algorithm still works
2. **"What if we start with some initial gas?"** → Add to first station's gas
3. **"Find minimum starting gas needed?"** → Track minimum balance reached
4. **"What if there are multiple valid starts?"** → Use brute force approach
5. **"Prove the greedy choice is optimal"** → Explain mathematical reasoning

---

## Quick Review Cards

### Card 21: 30-Second Summary
**Q:** Explain the Gas Station problem solution in 30 seconds.

**A:** 
Circular array problem. Key insight: if total gas ≥ total cost, exactly one solution exists. Greedy approach: track running balance, when negative reset start to next position. One pass O(n) time, O(1) space. Mathematical guarantee makes this optimal.

---

### Card 22: Red Flags in Implementation
**Q:** What are warning signs that your implementation might be incorrect?

**A:**
- Nested loops in "optimal" solution
- Complex circular indexing logic  
- Not checking total feasibility
- Resetting state at wrong time
- Confusing exit conditions in while loops
- Not handling edge cases (single station, no solution)

---

### Card 23: Key Insight Test
**Q:** Complete this statement: "If I can't reach station j from station i, then..."

**A:** "...I can't reach station j from any station between i and j-1, so I should try starting from station j+1 instead."

**This insight** is what transforms the O(n²) brute force into the O(n) optimal solution.