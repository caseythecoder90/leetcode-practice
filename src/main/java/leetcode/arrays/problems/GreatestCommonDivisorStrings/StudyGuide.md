# Greatest Common Divisor (GCD) - Study Guide

## What is GCD?

The **Greatest Common Divisor (GCD)** of two or more integers is the largest positive integer that divides each of the integers without remainder.

### Mathematical Definition
For integers `a` and `b`, GCD(a, b) is the largest integer `d` such that:
- `d` divides `a` (i.e., `a % d == 0`)
- `d` divides `b` (i.e., `b % d == 0`)

## GCD Examples with Numbers

### Example 1: GCD(12, 18)
```
Divisors of 12: 1, 2, 3, 4, 6, 12
Divisors of 18: 1, 2, 3, 6, 9, 18
Common divisors: 1, 2, 3, 6
Greatest Common Divisor: 6
```

### Example 2: GCD(48, 18)
```
Step-by-step using Euclidean Algorithm:
48 = 18 × 2 + 12
18 = 12 × 1 + 6
12 = 6 × 2 + 0

Since remainder is 0, GCD(48, 18) = 6
```

## Euclidean Algorithm

The **Euclidean Algorithm** is an efficient method to find GCD:

### Algorithm Steps
1. If `b = 0`, then GCD(a, b) = a
2. Otherwise, GCD(a, b) = GCD(b, a % b)
3. Repeat until remainder is 0

### Visual Example: GCD(48, 18)
```
Step 1: GCD(48, 18)
        48 ÷ 18 = 2 remainder 12
        So: GCD(48, 18) = GCD(18, 12)

Step 2: GCD(18, 12)  
        18 ÷ 12 = 1 remainder 6
        So: GCD(18, 12) = GCD(12, 6)

Step 3: GCD(12, 6)
        12 ÷ 6 = 2 remainder 0
        So: GCD(12, 6) = 6

Answer: GCD(48, 18) = 6
```

### Code Implementation
```java
public int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}
```

## GCD Applied to Strings

### String Division Concept
A string `t` "divides" string `s` if:
- `s` can be formed by concatenating `t` multiple times
- Example: "AB" divides "ABABAB" because "ABABAB" = "AB" + "AB" + "AB"

### Key Insight for String GCD
If two strings have a common divisor, then:
1. The length of the GCD string must divide both string lengths
2. The GCD string length = GCD(len(str1), len(str2))
3. Both strings must be formed from the same repeating pattern

## Worked Example: String GCD

### Example: str1 = "ABABAB", str2 = "ABAB"

#### Step 1: Check if common divisor exists
```
str1 + str2 = "ABABAB" + "ABAB" = "ABABABAB"
str2 + str1 = "ABAB" + "ABABAB" = "ABABABAB"
Equal? YES → Common divisor may exist
```

#### Step 2: Find GCD of lengths
```
len(str1) = 6, len(str2) = 4
GCD(6, 4):
  6 = 4 × 1 + 2
  4 = 2 × 2 + 0
  GCD(6, 4) = 2
```

#### Step 3: Extract candidate string
```
Candidate = str1.substring(0, 2) = "AB"
```

#### Step 4: Verify the answer
```
Does "AB" divide "ABABAB"? 
"ABABAB" = "AB" + "AB" + "AB" ✓

Does "AB" divide "ABAB"?
"ABAB" = "AB" + "AB" ✓

Answer: "AB"
```

### Visual Representation
```
str1: A B A B A B    (length 6)
      └─┘ └─┘ └─┘    (3 repetitions of "AB")

str2: A B A B        (length 4)  
      └─┘ └─┘        (2 repetitions of "AB")

GCD length: GCD(6, 4) = 2
GCD string: "AB" (first 2 characters)
```

## Why the Algorithm Works

### Mathematical Foundation
1. **Necessary Condition**: If strings have a common divisor of length `k`, then `k` must divide both string lengths
2. **Sufficient Check**: The concatenation test `str1 + str2 == str2 + str1` ensures both strings have the same repeating pattern
3. **Optimality**: GCD of lengths gives the maximum possible divisor length

### Concatenation Test Explanation
If both strings are built from the same pattern `P`:
- str1 = P^m (P repeated m times)
- str2 = P^n (P repeated n times)
- str1 + str2 = P^m + P^n = P^(m+n)
- str2 + str1 = P^n + P^m = P^(n+m) = P^(m+n)
- Therefore: str1 + str2 == str2 + str1

## Common Pitfalls

1. **Forgetting the concatenation check**: Always verify str1 + str2 == str2 + str1
2. **Wrong GCD calculation**: Use proper Euclidean algorithm
3. **Off-by-one errors**: Remember substring(0, gcdLength) not substring(0, gcdLength-1)
4. **Edge cases**: Handle empty strings and single character strings properly

## Practice Tips

1. Always start with the concatenation check
2. Manually trace through the GCD calculation
3. Verify your answer by checking if it actually divides both strings
4. Consider edge cases: empty strings, identical strings, no common divisor