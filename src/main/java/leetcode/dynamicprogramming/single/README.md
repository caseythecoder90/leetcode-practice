# Single-Dimensional Dynamic Programming

## What is Single-Dimensional DP?

Single-dimensional DP refers to problems where the **state can be represented by a single parameter**. This means we only need a 1D array (or just a few variables) to store our intermediate results.

**Key Characteristic**: `dp[i]` represents the optimal solution (or count of solutions) for a subproblem involving the first `i` elements, or up to position `i`, or for input size `i`.

## Why Start with 1D DP?

1. **Simpler to visualize**: Only one dimension to think about
2. **Easier state transitions**: Usually depends on 1-3 previous states
3. **Natural progression**: Most 1D problems have intuitive recursive relationships
4. **Foundation for 2D**: Understanding 1D DP makes 2D much easier

## Core Patterns in Single-Dimensional DP

### Pattern 1: Linear Sequence (Fibonacci-like)
**Structure**: `dp[i]` depends on one or more previous values
**Recurrence**: `dp[i] = f(dp[i-1], dp[i-2], ..., dp[i-k])`

### Pattern 2: Decision Making
**Structure**: At each position, make an optimal choice
**Recurrence**: `dp[i] = max/min(choice1, choice2, ..., choiceK)`

### Pattern 3: Counting
**Structure**: Count number of ways to achieve something
**Recurrence**: `dp[i] = sum(ways_from_different_paths)`

## Detailed Step-by-Step Approach

### Step 1: Understand the Problem
**Questions to Ask:**
- What are we optimizing? (max, min, count)
- What choices do we have at each step?
- What information do we need to make the optimal choice?

### Step 2: Define the State
**Template**: `dp[i]` represents _____ for the first `i` elements (or up to position `i`)

**Examples**:
- Fibonacci: `dp[i]` = the ith Fibonacci number
- Climbing Stairs: `dp[i]` = number of ways to reach step i
- House Robber: `dp[i]` = maximum money robbed from houses 0 to i

### Step 3: Find the Recurrence Relation
**Process**:
1. Look at position `i`
2. Consider all possible ways to reach position `i`
3. Express `dp[i]` in terms of previous states

### Step 4: Identify Base Cases
**Base cases** are the smallest subproblems that can be solved directly.

**Common patterns**:
- `dp[0]` = what happens with empty input or at position 0
- `dp[1]` = what happens with one element

### Step 5: Determine Fill Order
**Bottom-up**: Start from base cases, work toward final answer
**Order**: Usually `i` from `0` to `n` or `1` to `n`

## Detailed Examples

### Example 1: Fibonacci Numbers (The Classic)

**Problem**: Find the nth Fibonacci number where F(0)=0, F(1)=1, F(n)=F(n-1)+F(n-2)

#### Step-by-Step Analysis:

1. **Understand**: We need the nth number in the Fibonacci sequence
2. **State**: `dp[i]` = the ith Fibonacci number
3. **Recurrence**: `dp[i] = dp[i-1] + dp[i-2]` for i ≥ 2
4. **Base Cases**: `dp[0] = 0`, `dp[1] = 1`
5. **Fill Order**: i from 2 to n

#### Implementation:
```java
public int fibonacci(int n) {
    if (n <= 1) return n;
    
    int[] dp = new int[n + 1];
    dp[0] = 0;  // Base case
    dp[1] = 1;  // Base case
    
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i-1] + dp[i-2];  // Recurrence
    }
    
    return dp[n];
}
```

#### Trace Example (n=5):
```
i=0: dp[0] = 0
i=1: dp[1] = 1  
i=2: dp[2] = dp[1] + dp[0] = 1 + 0 = 1
i=3: dp[3] = dp[2] + dp[1] = 1 + 1 = 2
i=4: dp[4] = dp[3] + dp[2] = 2 + 1 = 3
i=5: dp[5] = dp[4] + dp[3] = 3 + 2 = 5
```

### Example 2: Climbing Stairs (Decision Making)

**Problem**: You can climb 1 or 2 steps at a time. How many ways to reach step n?

#### Step-by-Step Analysis:

1. **Understand**: Count number of distinct ways to reach the top
2. **State**: `dp[i]` = number of ways to reach step i
3. **Recurrence**: To reach step i, we can come from step i-1 (climb 1) or step i-2 (climb 2)
   So: `dp[i] = dp[i-1] + dp[i-2]`
4. **Base Cases**: 
   - `dp[0] = 1` (one way to stay at ground - do nothing)
   - `dp[1] = 1` (one way to reach step 1 - climb 1 step)
5. **Fill Order**: i from 2 to n

#### Implementation:
```java
public int climbStairs(int n) {
    if (n <= 1) return 1;
    
    int[] dp = new int[n + 1];
    dp[0] = 1;  // Base case
    dp[1] = 1;  // Base case
    
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i-1] + dp[i-2];  // Can come from i-1 or i-2
    }
    
    return dp[n];
}
```

#### Visual Understanding:
```
To reach step 3:
- From step 2: climb 1 step
- From step 1: climb 2 steps
Total ways = ways_to_reach_step_2 + ways_to_reach_step_1
```

### Example 3: House Robber (Optimization)

**Problem**: Rob houses in a line, can't rob adjacent houses. Maximize money.

#### Step-by-Step Analysis:

1. **Understand**: At each house, decide whether to rob it or not
2. **State**: `dp[i]` = maximum money that can be robbed from houses 0 to i
3. **Recurrence**: At house i, we have two choices:
   - Rob house i: `dp[i-2] + nums[i]` (can't rob i-1)
   - Don't rob house i: `dp[i-1]`
   Take the maximum: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`
4. **Base Cases**: 
   - `dp[0] = nums[0]` (only one house, rob it)
   - `dp[1] = max(nums[0], nums[1])` (rob the better of first two)
5. **Fill Order**: i from 2 to n-1

#### Implementation:
```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    
    int[] dp = new int[nums.length];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);
    
    for (int i = 2; i < nums.length; i++) {
        dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
    }
    
    return dp[nums.length - 1];
}
```

#### Decision Tree Example: [2, 7, 9, 3, 1]
```
House 0: Rob 2, total = 2
House 1: Max(rob 7, keep 2) = 7
House 2: Max(keep 7, rob 9+2) = 11
House 3: Max(keep 11, rob 3+7) = 11  
House 4: Max(keep 11, rob 1+11) = 12
```

## Common Thinking Patterns

### 1. "What Do I Need to Know?"
At each position i, ask: "What information from previous positions helps me make the optimal decision here?"

### 2. "What Are My Choices?"
List all possible actions at position i, then see which previous states each choice depends on.

### 3. "Build From Simple Cases"
Start with the simplest cases (base cases) and see the pattern for building up.

## Common Mistakes and How to Fix Them

### Mistake 1: Unclear State Definition
❌ **Problem**: "dp[i] represents something about position i" (too vague)
✅ **Solution**: Be precise: "dp[i] = maximum sum ending at position i"

### Mistake 2: Wrong Base Cases
❌ **Problem**: Not handling edge cases properly
✅ **Solution**: Trace through small examples (n=0, n=1, n=2) manually

### Mistake 3: Index Confusion
❌ **Problem**: Mixing up array indices with DP state indices
✅ **Solution**: Be explicit about what each index represents

### Mistake 4: Missing State Transitions  
❌ **Problem**: Not considering all ways to reach a state
✅ **Solution**: Systematically list all possible previous states

## Space Optimization Techniques

Many 1D DP problems can be optimized from O(n) space to O(1) space when we only need the last few states.

### Example: Fibonacci with O(1) Space
```java
public int fibonacci(int n) {
    if (n <= 1) return n;
    
    int prev2 = 0;  // dp[i-2]
    int prev1 = 1;  // dp[i-1]
    
    for (int i = 2; i <= n; i++) {
        int current = prev1 + prev2;  // dp[i]
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

## Practice Progression

### Beginner Level (Start Here)
1. **Fibonacci Numbers** - Learn basic recurrence
2. **Climbing Stairs** - Understand counting problems
3. **Tribonacci** - Practice with 3 previous states

### Intermediate Level
4. **House Robber** - Learn optimization decisions
5. **Min Cost Climbing Stairs** - Practice cost optimization
6. **Decode Ways** - Complex counting with conditions

### Advanced Level
7. **Word Break** - Decision with variable transitions
8. **Coin Change** - Unbounded knapsack in 1D
9. **Longest Increasing Subsequence** - Complex state definition

## Debugging Tips

### 1. Trace Small Examples by Hand
```java
// Add debug prints
System.out.println("dp[" + i + "] = " + dp[i]);

// Print entire array
System.out.println(Arrays.toString(dp));
```

### 2. Verify Base Cases First
Always test your base cases with the simplest inputs before testing complex cases.

### 3. Check Recurrence Logic
Walk through the recurrence relation step by step to ensure it matches your understanding.

### 4. Test Edge Cases
- Empty input (n=0)
- Single element (n=1)  
- Two elements (n=2)

## Key Takeaways

1. **State Definition is Crucial**: Spend time getting this right
2. **Base Cases Matter**: They're the foundation of your solution
3. **Trace Examples**: Understanding beats memorization
4. **Start Simple**: Master easy problems before moving to hard ones
5. **Space Optimization**: Often possible when only recent states matter

Remember: The goal isn't to memorize solutions, but to understand the thinking process behind identifying states, transitions, and base cases. Once you master this thinking, you can solve new DP problems you've never seen before!