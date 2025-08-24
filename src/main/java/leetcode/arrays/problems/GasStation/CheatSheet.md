# Gas Station - Cheat Sheet

## Problem Pattern: Circular Array + Greedy

### Quick Recognition Signs
- Circular route/array mentioned
- "Can complete the circuit" type question
- Need to find optimal starting position
- Balance/accumulation tracking required

## Core Mathematical Insight

### The Key Theorem
**If `sum(gas) >= sum(cost)`, then exactly one valid starting position exists**

**Proof Intuition**: If total resources ≥ total requirements, and we're guaranteed uniqueness, then the greedy approach will find it.

## Template Solutions

### Optimal O(n) Greedy Template
```java
public int canCompleteCircuit(int[] gas, int[] cost) {
    int totalBalance = 0, currentBalance = 0, startStation = 0;
    
    for (int i = 0; i < gas.length; i++) {
        int balance = gas[i] - cost[i];
        totalBalance += balance;
        currentBalance += balance;
        
        // If current journey fails, try starting from next station
        if (currentBalance < 0) {
            startStation = i + 1;
            currentBalance = 0;
        }
    }
    
    return totalBalance >= 0 ? startStation : -1;
}
```

### Brute Force O(n²) Template
```java
public int canCompleteCircuitBruteForce(int[] gas, int[] cost) {
    int n = gas.length;
    
    for (int start = 0; start < n; start++) {
        int currentGas = 0;
        boolean canComplete = true;
        
        for (int count = 0; count < n && canComplete; count++) {
            int station = (start + count) % n;
            currentGas += gas[station] - cost[station];
            if (currentGas < 0) canComplete = false;
        }
        
        if (canComplete) return start;
    }
    
    return -1;
}
```

## Key Patterns & Techniques

### 1. Circular Array Handling
```java
// Standard modular arithmetic for circular arrays
int nextIndex = (currentIndex + 1) % arrayLength;

// Visiting all n elements starting from position start
for (int count = 0; count < n; count++) {
    int index = (start + count) % n;
    // Process element at index
}
```

### 2. Balance Tracking Pattern
```java
int totalBalance = 0;     // Overall feasibility check
int currentBalance = 0;   // Current journey status
int candidate = 0;        // Potential starting position

for (int i = 0; i < n; i++) {
    int stepBalance = benefit[i] - cost[i];
    
    totalBalance += stepBalance;
    currentBalance += stepBalance;
    
    if (currentBalance < 0) {
        candidate = i + 1;     // Reset candidate
        currentBalance = 0;    // Reset journey
    }
}
```

### 3. Greedy Choice Logic
```java
// When current strategy fails at position i:
// 1. All positions from lastCandidate to i-1 are invalid
// 2. Next candidate should be i+1
// 3. Reset accumulator for fresh start

if (currentBalance < 0) {
    startCandidate = i + 1;  // Skip to next potential start
    currentBalance = 0;      // Fresh start
}
```

## Common Mistakes & Solutions

### ❌ Wrong Loop Conditions
```java
// WRONG: Confusing exit condition
while (currentGas >= 0 && nextStation != startStation) {
    // Problems:
    // 1. nextStation creates unnecessary complexity
    // 2. Doesn't properly track circuit completion
}

// RIGHT: Clear progress tracking
for (int visited = 0; visited < n; visited++) {
    int station = (start + visited) % n;
    currentGas += gas[station] - cost[station];
    if (currentGas < 0) break;
}
```

### ❌ Incorrect State Management
```java
// WRONG: Reset after failure
if (cannotComplete) {
    currentGas = 0;  // Too late!
    start++;
}

// RIGHT: Reset before new attempt
for (int start = 0; start < n; start++) {
    int currentGas = 0;  // Fresh start each time
    // ... attempt logic
}
```

### ❌ Missing Edge Cases
```java
// WRONG: Not checking fundamental feasibility
return startStation;

// RIGHT: Always verify total balance
return totalGas >= totalCost ? startStation : -1;
```

## Algorithm Selection Guide

| Scenario | Use This Approach |
|----------|------------------|
| Interview/Contest | Optimal O(n) Greedy |
| Learning/Understanding | Start with Brute Force |
| Debugging | Implement both, compare results |
| Follow-up Questions | Know the mathematical proof |

## Step-by-Step Solution Process

### 1. Understand the Problem
- [ ] Identify it's a circular array problem
- [ ] Recognize the balance/accumulation pattern
- [ ] Note the uniqueness guarantee

### 2. Choose Approach
- [ ] **Beginner**: Start with brute force
- [ ] **Intermediate**: Jump to optimal greedy
- [ ] **Advanced**: Explain the mathematical insight

### 3. Implementation Checklist
- [ ] Handle circular indexing correctly
- [ ] Track both total and current balance
- [ ] Reset state properly on failures
- [ ] Return -1 when impossible

### 4. Testing Strategy
```java
// Test cases to verify
int[][] testCases = {
    // Standard case
    {{1,2,3,4,5}, {3,4,5,1,2}},  // Expected: 3
    // No solution
    {{2,3,4}, {3,4,3}},          // Expected: -1
    // Single station
    {{5}, {4}},                   // Expected: 0
    // Edge case
    {{3,1,1}, {1,2,2}}           // Expected: 0
};
```

## Time/Space Complexity Quick Reference

| Approach | Time | Space | When to Use |
|----------|------|-------|-------------|
| Greedy | O(n) | O(1) | Optimal solution |
| Brute Force | O(n²) | O(1) | Learning/verification |
| Simulation | O(n²) | O(1) | Debugging complex cases |

## Interview Talking Points

### "Why does the greedy approach work?"
**Answer**: Mathematical guarantee + uniqueness ensures that when we fail at position i, the optimal start must be after i.

### "How do you handle the circular nature?"
**Answer**: Modular arithmetic for indexing, but the key insight makes full simulation unnecessary.

### "What if there are multiple solutions?"
**Answer**: Problem guarantees uniqueness, but the algorithm would find the first valid starting position.

## Related Problems
- **55. Jump Game** - Similar greedy reasoning
- **45. Jump Game II** - Optimal position selection  
- **53. Maximum Subarray** - Balance tracking pattern
- **121. Best Time to Buy and Sell Stock** - Single-pass optimization

## Quick Implementation Steps
1. Declare: `totalBalance`, `currentBalance`, `candidate`
2. Loop through array once
3. Update both balances with `gas[i] - cost[i]`
4. Reset candidate when `currentBalance < 0`
5. Return `totalBalance >= 0 ? candidate : -1`