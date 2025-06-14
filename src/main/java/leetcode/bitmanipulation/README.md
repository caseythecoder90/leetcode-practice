# Bit Manipulation

## Overview
Bit manipulation involves manipulating individual bits in a number using bitwise operations. This is a powerful technique for solving various problems efficiently.

## Key Concepts

### Binary Representation
- Each number is represented as a sequence of bits (0s and 1s)
- In Java, integers are 32-bit signed numbers
- The leftmost bit is the sign bit (0 for positive, 1 for negative)

### Bitwise Operators

| Operator | Description | Example |
|----------|-------------|---------|
| `&` | AND | `5 & 3 = 1` (0101 & 0011 = 0001) |
| `\|` | OR | `5 \| 3 = 7` (0101 \| 0011 = 0111) |
| `^` | XOR | `5 ^ 3 = 6` (0101 ^ 0011 = 0110) |
| `~` | NOT | `~5 = -6` (Inverts all bits) |
| `<<` | Left Shift | `5 << 1 = 10` (0101 << 1 = 1010) |
| `>>` | Right Shift | `5 >> 1 = 2` (0101 >> 1 = 0010) |
| `>>>` | Unsigned Right Shift | `5 >>> 1 = 2` (0101 >>> 1 = 0010) |

### Common Bit Operations

#### Check if a bit is set
```java
boolean isBitSet(int num, int pos) {
    return (num & (1 << pos)) != 0;
}
```

#### Set a bit
```java
int setBit(int num, int pos) {
    return num | (1 << pos);
}
```

#### Clear a bit
```java
int clearBit(int num, int pos) {
    return num & ~(1 << pos);
}
```

#### Toggle a bit
```java
int toggleBit(int num, int pos) {
    return num ^ (1 << pos);
}
```

#### Count set bits (Hamming Weight)
```java
int countSetBits(int num) {
    int count = 0;
    while (num > 0) {
        count += num & 1;
        num >>= 1;
    }
    return count;
}
```

#### Faster method using Brian Kernighan's algorithm
```java
int countSetBits(int num) {
    int count = 0;
    while (num > 0) {
        num &= (num - 1);  // Clears the least significant set bit
        count++;
    }
    return count;
}
```

## Problem Patterns

1. **Bit Masks**: Using a specific bit pattern to extract or modify certain bits
2. **Power of Two**: A number is a power of 2 if only one bit is set
3. **Manipulating the Least Significant Bit**: Techniques like `n & (n-1)` and `n & -n`
4. **XOR Properties**: Used for finding single numbers, swapping, etc.
5. **Bit Counting**: Counting set bits in an integer
6. **Bit Shifting**: Multiplication/division by powers of 2

## Performance Benefits

- Bit manipulation operations are generally faster than arithmetic operations
- They can reduce space complexity by using bits to represent states
- Particularly useful for optimizing algorithms dealing with flags, sets, or state machines

## Real-World Applications

- Network protocols (IP addresses, subnet masks)
- Graphics programming (color manipulation, pixel operations)
- Cryptography (hashing, encryption)
- Compression algorithms
- Low-level system programming

## Common Pitfalls

- Sign extension with right shift (`>>` vs `>>>`)
- Overflow with left shift
- Bit operations on negative numbers
- Forgetting that integers are 32-bit in Java