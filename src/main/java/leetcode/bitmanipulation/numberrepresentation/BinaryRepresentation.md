# Binary Number System

## Overview

Binary is a base-2 number system that uses only two digits: 0 and 1. It is fundamental to computing because digital electronics can easily represent binary digits (bits) as two voltage levels.

## Binary Counting

The binary number system works like the decimal system but with a base of 2 instead of 10:

| Decimal | Binary | Explanation |
|---------|--------|-------------|
| 0 | 0 | 0 |
| 1 | 1 | 1 |
| 2 | 10 | 1×2¹ + 0×2⁰ = 2 |
| 3 | 11 | 1×2¹ + 1×2⁰ = 3 |
| 4 | 100 | 1×2² + 0×2¹ + 0×2⁰ = 4 |
| 5 | 101 | 1×2² + 0×2¹ + 1×2⁰ = 5 |
| 6 | 110 | 1×2² + 1×2¹ + 0×2⁰ = 6 |
| 7 | 111 | 1×2² + 1×2¹ + 1×2⁰ = 7 |
| 8 | 1000 | 1×2³ + 0×2² + 0×2¹ + 0×2⁰ = 8 |

## Powers of 2

Important powers of 2 to memorize:

| Power | Value | Binary Representation |
|-------|-------|------------------------|
| 2⁰ | 1 | 1 |
| 2¹ | 2 | 10 |
| 2² | 4 | 100 |
| 2³ | 8 | 1000 |
| 2⁴ | 16 | 10000 |
| 2⁵ | 32 | 100000 |
| 2⁶ | 64 | 1000000 |
| 2⁷ | 128 | 10000000 |
| 2⁸ | 256 | 100000000 |
| 2¹⁰ | 1,024 (1K) | 10000000000 |
| 2²⁰ | 1,048,576 (1M) | 100000000000000000000 |
| 2³⁰ | 1,073,741,824 (1G) | 1000000000000000000000000000000 |

## Decimal to Binary Conversion

### Method 1: Division by 2
1. Divide the decimal number by 2
2. Record the remainder (0 or 1)
3. Divide the quotient by 2
4. Repeat until the quotient becomes 0
5. Read the remainders from bottom to top

Example: Convert 42 to binary
```
42 ÷ 2 = 21 remainder 0
21 ÷ 2 = 10 remainder 1
10 ÷ 2 = 5  remainder 0
5  ÷ 2 = 2  remainder 1
2  ÷ 2 = 1  remainder 0
1  ÷ 2 = 0  remainder 1
```
Reading from bottom to top: 101010

### Method 2: Subtraction of Powers of 2
1. Find the largest power of 2 less than or equal to the number
2. Subtract this power of 2 from the number
3. Mark a 1 in the corresponding position
4. Repeat with the remainder
5. Mark 0s in positions where no power of 2 was subtracted

Example: Convert 42 to binary
- Largest power of 2 ≤ 42 is 2⁵ = 32
- 42 - 32 = 10
- Largest power of 2 ≤ 10 is 2³ = 8
- 10 - 8 = 2
- Largest power of 2 ≤ 2 is 2¹ = 2
- 2 - 2 = 0
- Powers used: 2⁵, 2³, 2¹
- Binary: 101010 (1 in positions 5, 3, and 1; 0 elsewhere)

### Java Implementation
```java
public String decimalToBinary(int n) {
    if (n == 0) return "0";
    
    StringBuilder binary = new StringBuilder();
    while (n > 0) {
        binary.insert(0, n % 2);
        n /= 2;
    }
    
    return binary.toString();
}
```

## Binary to Decimal Conversion

To convert binary to decimal, multiply each digit by the corresponding power of 2 and sum the results.

Example: Convert 101010 to decimal
```
1×2⁵ + 0×2⁴ + 1×2³ + 0×2² + 1×2¹ + 0×2⁰
= 32 + 0 + 8 + 0 + 2 + 0
= 42
```

### Java Implementation
```java
public int binaryToDecimal(String binary) {
    int decimal = 0;
    int power = 0;
    
    for (int i = binary.length() - 1; i >= 0; i--) {
        if (binary.charAt(i) == '1') {
            decimal += Math.pow(2, power);
        }
        power++;
    }
    
    return decimal;
}
```

## Signed Binary Representation

### Two's Complement

In computing, negative numbers are commonly represented using two's complement:

1. To negate a number:
   - Flip all bits (1s complement)
   - Add 1 to the result (2s complement)

Example: Represent -5 in 8-bit two's complement
1. Positive 5 in binary: 00000101
2. Flip all bits: 11111010
3. Add 1: 11111011

The leftmost bit is the sign bit (0 for positive, 1 for negative).

### Range of Values

For n-bit two's complement:
- Minimum value: -2^(n-1)
- Maximum value: 2^(n-1) - 1

Example: 8-bit two's complement range is -128 to 127

## Binary Arithmetic

### Addition
```
  1 1 1 (carry)
   0 1 1 0 1
 + 0 0 1 1 1
 = 1 0 1 0 0
```

### Subtraction (using two's complement)
```
 0 1 1 0 1 (13)
-0 0 1 1 1 (7)
=0 0 1 1 0 (two's complement of 7: 1 0 0 1)
 -------
 0 1 1 0 1
+1 0 0 1 0
=1 0 1 1 1 (ignore overflow) = 0 0 1 1 0 (6)
```

## Bitwise Operations

| Operation | Symbol | Example |
|-----------|--------|---------|
| AND | & | 1010 & 1100 = 1000 |
| OR | \| | 1010 \| 1100 = 1110 |
| XOR | ^ | 1010 ^ 1100 = 0110 |
| NOT | ~ | ~1010 = 0101 (ignoring sign extension) |
| Left Shift | << | 1010 << 1 = 10100 |
| Right Shift | >> | 1010 >> 1 = 0101 |

## Binary in Computer Science

### Data Storage
- 8 bits = 1 byte
- 1024 bytes = 1 kilobyte (KB)
- 1024 KB = 1 megabyte (MB)
- 1024 MB = 1 gigabyte (GB)
- 1024 GB = 1 terabyte (TB)

### Data Types
- byte: 8 bits, range -128 to 127
- short: 16 bits, range -32,768 to 32,767
- int: 32 bits, range -2,147,483,648 to 2,147,483,647
- long: 64 bits, range -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807

### Floating Point Representation
- IEEE 754 standard
- 32-bit float: 1 sign bit, 8 exponent bits, 23 mantissa bits
- 64-bit double: 1 sign bit, 11 exponent bits, 52 mantissa bits

## Optimizations

### Checking if a number is odd or even
```java
boolean isEven(int n) {
    return (n & 1) == 0;
}
```

### Multiply/divide by powers of 2
```java
// Multiply by 2^k
int multiply(int n, int k) {
    return n << k;
}

// Divide by 2^k
int divide(int n, int k) {
    return n >> k;
}
```