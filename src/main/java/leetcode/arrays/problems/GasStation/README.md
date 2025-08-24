# 134. Gas Station

**Difficulty:** Medium  
**Topics:** Array, Greedy  

## Problem Statement

There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].

You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station. You begin the journey with an empty tank at one of the gas stations.

Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to be unique.

## Key Insights

### Mathematical Foundation

1. **Necessary Condition**: `sum(gas) >= sum(cost)`
   - If total gas available < total cost needed, no solution exists

2. **Sufficiency**: If total gas >= total cost, exactly one solution exists
   - This is guaranteed by the problem statement

3. **Greedy Choice**: If we can't reach station j from station i, then we can't reach j from any station between i and j-1
   - **Proof**: If stations i to k can't reach j, and we have positive gas at some intermediate station, then starting from i (which has even less accumulated gas) definitely can't reach j

## Solution Approaches

### Approach 1: Optimal Greedy Solution (O(n))

**Core Idea**: 
- Track cumulative gas balance
- When balance goes negative, reset start position to next station
- The mathematical guarantee ensures the final start position is correct

```java
public int canCompleteCircuit(int[] gas, int[] cost) {
    int totalGas = 0, totalCost = 0;
    int currentGas = 0, startStation = 0;
    
    for (int i = 0; i < gas.length; i++) {
        totalGas += gas[i];
        totalCost += cost[i];
        currentGas += gas[i] - cost[i];
        
        // If we can't proceed, try starting from next station
        if (currentGas < 0) {
            startStation = i + 1;
            currentGas = 0;
        }
    }
    
    return totalGas >= totalCost ? startStation : -1;
}
```

**Why This Works:**
1. We track the running gas balance
2. When balance becomes negative at station i, we know:
   - We can't complete the circuit starting from any station 0 to i
   - We should try starting from station i+1
3. The total gas check ensures solution existence

### Approach 2: Brute Force (O(n²))

Try every possible starting position and simulate the journey.

```java
public int canCompleteCircuitBruteForce(int[] gas, int[] cost) {
    int n = gas.length;
    
    for (int start = 0; start < n; start++) {
        int currentGas = 0;
        int stationsTraveled = 0;
        
        for (int i = start; stationsTraveled < n; i = (i + 1) % n) {
            currentGas += gas[i] - cost[i];
            if (currentGas < 0) break;
            stationsTraveled++;
        }
        
        if (stationsTraveled == n) return start;
    }
    
    return -1;
}
```

## Analysis of Your Original Solution

### Issues Identified:

1. **Incorrect Loop Condition**:
   ```java
   while (currentGasLevel >= 0 && nextStation != startStation)
   ```
   - Problem: Checking `nextStation != startStation` instead of tracking actual progress
   - Should track number of stations visited or use a different exit condition

2. **Unnecessary Pointer Management**:
   ```java
   nextStation = (nextStation + 1) % totalStations;  // Not needed
   ```
   - Creates confusion and doesn't add value

3. **Gas Level Management**:
   ```java
   currentGasLevel = 0;  // Reset happens after failed attempt
   ```
   - Should reset before trying new starting position

4. **Early Exit Logic**:
   - Loop can exit due to negative gas before completing circuit
   - The check `if (currentStation == startStation)` happens after loop exit

### Fixed Brute Force Version:
```java
// Clear progress tracking
int stationsTraveled = 0;

for (int i = start; stationsTraveled < n; i = (i + 1) % n) {
    currentGas += gas[i] - cost[i];
    if (currentGas < 0) break;  // Can't proceed
    stationsTraveled++;         // Track progress
}

if (stationsTraveled == n) return start;  // Completed circuit
```

## Step-by-Step Example

**Input**: `gas = [1,2,3,4,5]`, `cost = [3,4,5,1,2]`

### Optimal Solution Trace:
```
i=0: currentGas = 0 + (1-3) = -2 < 0 → startStation = 1, reset currentGas = 0
i=1: currentGas = 0 + (2-4) = -2 < 0 → startStation = 2, reset currentGas = 0  
i=2: currentGas = 0 + (3-5) = -2 < 0 → startStation = 3, reset currentGas = 0
i=3: currentGas = 0 + (4-1) = 3 ≥ 0 → continue
i=4: currentGas = 3 + (5-2) = 6 ≥ 0 → continue

totalGas = 15, totalCost = 15 → 15 ≥ 15 ✓
Return startStation = 3
```

### Verification from Station 3:
```
Start at 3: gas = 4, tank = 4
Go to 4: tank = 4-1+5 = 8  
Go to 0: tank = 8-2+1 = 7
Go to 1: tank = 7-3+2 = 6
Go to 2: tank = 6-4+3 = 5
Go to 3: tank = 5-5 = 0 (exactly enough!)
```

## Complexity Analysis

| Approach | Time Complexity | Space Complexity | Notes |
|----------|----------------|------------------|-------|
| Optimal Greedy | O(n) | O(1) | Single pass with constant space |
| Brute Force | O(n²) | O(1) | Try each start position |
| Your Original | O(n²) | O(1) | But has logical bugs |

## Common Pitfalls

1. **Off-by-one errors** in modular arithmetic
2. **Incorrect loop conditions** for circular arrays
3. **Not handling edge cases** (single station, no solution)
4. **Forgetting the mathematical insight** that makes O(n) solution possible

## Edge Cases

1. **Single Station**: `gas=[5], cost=[4]` → Should return 0
2. **No Solution**: `gas=[2,3,4], cost=[3,4,3]` → Should return -1  
3. **Solution at Last Position**: Important to test
4. **Exact Gas Match**: Where you finish with exactly 0 gas

## Key Takeaways

1. **Greedy algorithms** often have mathematical proofs behind their correctness
2. **Circular array problems** require careful index management
3. **Early termination conditions** in loops need careful consideration
4. **Simulation vs. Mathematical insight** - sometimes there's a more elegant approach than brute force

The optimal O(n) solution is a beautiful example of how mathematical insights can transform a seemingly complex problem into an elegant single-pass algorithm.