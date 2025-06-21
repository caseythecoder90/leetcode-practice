# Bit Manipulation Pattern - Flashcards

## Basic Operations

**Q: What are the 7 bitwise operators in Java and their symbols?**
A:
- `&` (AND): Both bits must be 1
- `|` (OR): At least one bit must be 1  
- `^` (XOR): Bits must be different
- `~` (NOT): Inverts all bits
- `<<` (Left Shift): Shifts bits left (multiplies by 2^n)
- `>>` (Right Shift): Shifts bits right with sign extension
- `>>>` (Unsigned Right Shift): Shifts bits right, fills with 0

**Q: What are the results of these bit operations: 5 & 3, 5 | 3, 5 ^ 3?**
A:
- `5 & 3 = 1` (0101 & 0011 = 0001)
- `5 | 3 = 7` (0101 | 0011 = 0111)  
- `5 ^ 3 = 6` (0101 ^ 0011 = 0110)

## Common Bit Tricks

**Q: How do you check if the i-th bit is set?**
A:
```java
boolean isBitSet(int num, int pos) {
    return (num & (1 << pos)) != 0;
}
```

**Q: How do you set, clear, and toggle the i-th bit?**
A:
```java
// Set bit
int setBit(int num, int pos) {
    return num | (1 << pos);
}

// Clear bit  
int clearBit(int num, int pos) {
    return num & ~(1 << pos);
}

// Toggle bit
int toggleBit(int num, int pos) {
    return num ^ (1 << pos);
}
```

**Q: What are two ways to count set bits (Hamming Weight)?**
A:
**Method 1 - Basic approach:**
```java
int count = 0;
while (num > 0) {
    count += num & 1;
    num >>= 1;
}
```

**Method 2 - Brian Kernighan's algorithm (faster):**
```java
int count = 0;
while (num > 0) {
    num &= (num - 1);  // Clears least significant set bit
    count++;
}
```

## XOR Properties

**Q: What are the key properties of XOR that make it useful?**
A:
1. `x ^ x = 0` (any number XOR with itself is 0)
2. `x ^ 0 = x` (any number XOR with 0 is itself)
3. XOR is commutative: `a ^ b = b ^ a`
4. XOR is associative: `(a ^ b) ^ c = a ^ (b ^ c)`
5. `x ^ y ^ x = y` (can be used to find single number)

**Q: How can you swap two numbers using XOR without extra space?**
A:
```java
a = a ^ b;
b = a ^ b;  // Now b = original a
a = a ^ b;  // Now a = original b
```

## Power of Two

**Q: How do you check if a number is a power of 2 using bit manipulation?**
A:
```java
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```
This works because powers of 2 have exactly one bit set, and `n & (n-1)` clears the least significant set bit.

**Q: What does `n & (n-1)` do and when is it useful?**
A: It clears the least significant set bit. Useful for:
- Counting set bits (Brian Kernighan's algorithm)
- Checking if number is power of 2
- Removing the rightmost 1 bit

## Advanced Operations

**Q: How do you isolate the least significant set bit?**
A:
```java
int isolateLSB(int n) {
    return n & (-n);
}
```
This gives you just the rightmost set bit.

**Q: What's the difference between `>>` and `>>>` operators?**
A:
- `>>` (signed right shift): Preserves sign bit, fills with sign bit
- `>>>` (unsigned right shift): Always fills with 0, treats number as unsigned

**Q: How do you multiply/divide by powers of 2 efficiently?**
A:
- Multiply by 2^k: `n << k`
- Divide by 2^k: `n >> k` (for positive numbers)

## Problem Patterns

**Q: What are the 6 main bit manipulation problem patterns?**
A:
1. **Bit Masks**: Using specific bit patterns to extract/modify bits
2. **Power of Two**: Checking if only one bit is set
3. **LSB Manipulation**: Techniques with `n & (n-1)` and `n & -n`
4. **XOR Properties**: Finding single numbers, swapping
5. **Bit Counting**: Counting set bits efficiently
6. **Bit Shifting**: Fast multiplication/division by powers of 2

**Q: When should you use bit manipulation over regular arithmetic?**
A:
- When working with flags or state representation
- When you need very fast operations
- When space is constrained (bits can represent multiple boolean states)
- For specific algorithms like finding single number in array
- In low-level programming or embedded systems

## Common Pitfalls

**Q: What are the main pitfalls to avoid in bit manipulation?**
A:
1. **Sign extension**: Be careful with `>>` vs `>>>` for negative numbers
2. **Overflow**: Left shifting can cause overflow
3. **Negative numbers**: Bit operations on negative numbers can be tricky
4. **32-bit assumption**: Remember integers are 32-bit in Java
5. **Precedence**: Use parentheses to ensure correct operation order