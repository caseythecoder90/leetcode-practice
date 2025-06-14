# XOR Operations - Study Sheet

## What is XOR?

XOR (Exclusive OR) is a bitwise operation that returns 1 when bits are different, 0 when they're the same.

### Truth Table
```
A | B | A XOR B
--|---|--------
0 | 0 |   0
0 | 1 |   1
1 | 0 |   1
1 | 1 |   0
```

### Example
```
5 XOR 3:
  101  (5 in binary)
^ 011  (3 in binary)
-----
  110  (6 in decimal)
```

## Key Properties

1. **Commutative**: `a ^ b = b ^ a`
2. **Associative**: `(a ^ b) ^ c = a ^ (b ^ c)`
3. **Identity**: `a ^ 0 = a`
4. **Self-inverse**: `a ^ a = 0`
5. **Distributive over AND**: `a ^ (b & c) = (a ^ b) & (a ^ c)`

## Common XOR Patterns

### Pattern 1: Finding Single Element
When all elements appear twice except one:
```java
public int findSingle(int[] nums) {
    int result = 0;
    for (int num : nums) {
        result ^= num;
    }
    return result;
}
```

### Pattern 2: Swapping Without Extra Variable
```java
// Swap two variables without temp
a = a ^ b;
b = a ^ b;  // b = (a ^ b) ^ b = a
a = a ^ b;  // a = (a ^ b) ^ a = b
```

### Pattern 3: Toggle Bit at Position
```java
public int toggleBit(int num, int pos) {
    return num ^ (1 << pos);
}
```

### Pattern 4: Check if Two Numbers Have Same Sign
```java
public boolean sameSign(int a, int b) {
    return (a ^ b) >= 0;
}
```

## LeetCode Problems Using XOR

### Easy
- **136. Single Number** - Find element appearing once when others appear twice
- **268. Missing Number** - Find missing number in array 0 to n
- **389. Find the Difference** - Find added character between two strings

### Medium
- **137. Single Number II** - Find element appearing once when others appear thrice
- **260. Single Number III** - Find two elements appearing once when others appear twice
- **421. Maximum XOR of Two Numbers** - Find maximum XOR of any two numbers

### Hard
- **995. Minimum Number of K Consecutive Bit Flips** - Minimize flips to make all 1s

## Template Solutions

### Single Number (136)
```java
public int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) {
        result ^= num;
    }
    return result;
}

// Stream alternative (Java 8+)
public int singleNumber(int[] nums) {
    return Arrays.stream(nums).reduce(0, (a, b) -> a ^ b);
}
```

### Missing Number (268)
```java
public int missingNumber(int[] nums) {
    int n = nums.length;
    int result = n; // Start with the missing index
    for (int i = 0; i < n; i++) {
        result ^= i ^ nums[i];
    }
    return result;
}

// Alternative approach
public int missingNumber(int[] nums) {
    int expectedXor = 0;
    int actualXor = 0;
    
    for (int i = 0; i <= nums.length; i++) {
        expectedXor ^= i;
    }
    
    for (int num : nums) {
        actualXor ^= num;
    }
    
    return expectedXor ^ actualXor;
}
```

### Single Number III (260)
```java
public int[] singleNumber(int[] nums) {
    // XOR all numbers to get xor of the two unique numbers
    int xor = 0;
    for (int num : nums) {
        xor ^= num;
    }
    
    // Find rightmost set bit to distinguish the two numbers
    int diff = xor & (-xor);
    
    // Separate numbers into two groups and XOR each group
    int a = 0, b = 0;
    for (int num : nums) {
        if ((num & diff) != 0) {
            a ^= num;
        } else {
            b ^= num;
        }
    }
    
    return new int[]{a, b};
}
```

### Find the Difference (389)
```java
public char findTheDifference(String s, String t) {
    char result = 0;
    
    // XOR all characters in both strings
    for (char c : s.toCharArray()) {
        result ^= c;
    }
    
    for (char c : t.toCharArray()) {
        result ^= c;
    }
    
    return result;
}
```

## XOR Tricks & Tips

### Check if Number is Power of 2
```java
public boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

### Count Set Bits (Hamming Weight)
```java
public int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
        count++;
        n &= n - 1; // Remove rightmost set bit
    }
    return count;
}

// Alternative using built-in method
public int hammingWeight(int n) {
    return Integer.bitCount(n);
}
```

### Get Rightmost Set Bit
```java
public int rightmostSetBit(int n) {
    return n & (-n);
}
```

### Check if Bit is Set at Position
```java
public boolean isBitSet(int num, int pos) {
    return (num & (1 << pos)) != 0;
}
```

### Set Bit at Position
```java
public int setBit(int num, int pos) {
    return num | (1 << pos);
}
```

### Clear Bit at Position
```java
public int clearBit(int num, int pos) {
    return num & ~(1 << pos);
}
```

## Time & Space Complexity

Most XOR operations are:
- **Time**: O(1) for single operation, O(n) for array traversal
- **Space**: O(1) - no extra space needed

## When to Use XOR

- Finding unique elements when others appear in pairs
- Bit manipulation problems
- Swapping variables without temp storage
- Checking if bits are different
- Cryptography and checksums
- Problems involving cancellation of duplicates
- Toggle operations (on/off states)

## Common Pitfalls

1. **Integer Overflow**: While XOR doesn't cause overflow, be careful with intermediate calculations
2. **Sign Bit**: XOR preserves the sign bit pattern in Java's two's complement
3. **Operator Precedence**: XOR has lower precedence than comparison operators
4. **Edge Cases**: Always test with single elements, empty arrays, and negative numbers
5. **Bit Position**: Remember Java uses 0-based bit indexing (rightmost bit is position 0)

## Java-Specific Notes

- Use `^` operator for XOR
- Use `^=` for compound assignment
- `Integer.bitCount(n)` counts set bits
- Bit shifting: `<<` (left), `>>` (arithmetic right), `>>>` (logical right)
- Java integers are 32-bit signed values
- Use `0b` prefix for binary literals: `int x = 0b1010;`

## Practice Strategy

1. Start with basic XOR properties and single number problems
2. Practice bit manipulation fundamentals
3. Move to problems requiring multiple XOR operations
4. Study problems combining XOR with other techniques (sliding window, two pointers)
5. Time yourself - XOR solutions are usually very efficient

## Related Concepts

- **AND (&)**: Both bits must be 1
- **OR (|)**: At least one bit must be 1
- **NOT (~)**: Flips all bits
- **Left Shift (<<)**: Multiply by 2^n
- **Right Shift (>>)**: Divide by 2^n