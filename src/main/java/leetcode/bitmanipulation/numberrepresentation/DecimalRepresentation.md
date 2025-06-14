# Decimal Number System

## Overview

The decimal (base-10) number system is the standard number system we use in everyday life. It uses ten digits (0-9) to represent values. While computers internally work with binary, decimal is the system humans typically use for input and output.

## Decimal Counting

Each position in a decimal number represents a power of 10:

| Position | ... | Thousands | Hundreds | Tens | Ones |
|----------|-----|-----------|----------|------|------|
| Value    | ... | 10³       | 10²      | 10¹  | 10⁰  |
| Example: 2,753 | ... | 2×10³=2000 | 7×10²=700 | 5×10¹=50 | 3×10⁰=3 |

## Powers of 10

Important powers of 10:

| Power | Value | Name |
|-------|-------|------|
| 10⁰ | 1 | One |
| 10¹ | 10 | Ten |
| 10² | 100 | Hundred |
| 10³ | 1,000 | Thousand |
| 10⁶ | 1,000,000 | Million |
| 10⁹ | 1,000,000,000 | Billion |
| 10¹² | 1,000,000,000,000 | Trillion |

## Integer Representation in Computers

### Fixed-Width Integers

Computers represent integers using a fixed number of bits:

| Type | Bits | Range (Signed) | Range (Unsigned) |
|------|------|----------------|------------------|
| byte | 8 | -128 to 127 | 0 to 255 |
| short | 16 | -32,768 to 32,767 | 0 to 65,535 |
| int | 32 | -2,147,483,648 to 2,147,483,647 | 0 to 4,294,967,295 |
| long | 64 | -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 | 0 to 18,446,744,073,709,551,615 |

### BigInteger

For arbitrary precision arithmetic in Java:

```java
import java.math.BigInteger;

BigInteger a = new BigInteger("123456789012345678901234567890");
BigInteger b = new BigInteger("987654321098765432109876543210");
BigInteger sum = a.add(b);
```

## Decimal Arithmetic

### Addition
```
  1 (carry)
   2 5
 + 1 7
 = 4 2
```

### Subtraction
```
  4 (borrow)
   5 0
 - 2 7
 = 2 3
```

### Multiplication
```
     2 5
   × 1 7
  ------
     1 7 5  (25 × 7)
   2 5 0    (25 × 10)
  ------
   4 2 5    (result)
```

### Division
```
      1 7
    ------
13 ) 2 2 1
    1 3
    ------
      9 1
      9 1
      ---
        0
```

## Decimal to Binary Conversion

### Method 1: Division by 2
1. Divide the decimal number by 2
2. Record the remainder (0 or 1)
3. Divide the quotient by 2
4. Repeat until the quotient becomes 0
5. Read the remainders from bottom to top

Example: Convert 25 to binary
```
25 ÷ 2 = 12 remainder 1
12 ÷ 2 = 6  remainder 0
6  ÷ 2 = 3  remainder 0
3  ÷ 2 = 1  remainder 1
1  ÷ 2 = 0  remainder 1
```
Reading from bottom to top: 11001

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

## Decimal to Hexadecimal Conversion

### Method 1: Division by 16
1. Divide the decimal number by 16
2. Record the remainder (0-15, represented as 0-F)
3. Divide the quotient by 16
4. Repeat until the quotient becomes 0
5. Read the remainders from bottom to top

Example: Convert 2023 to hexadecimal
```
2023 ÷ 16 = 126 remainder 7
126  ÷ 16 = 7   remainder 14 (E)
7    ÷ 16 = 0   remainder 7
```
Reading from bottom to top: 7E7

### Java Implementation
```java
public String decimalToHex(int n) {
    if (n == 0) return "0";
    
    StringBuilder hex = new StringBuilder();
    char[] hexChars = "0123456789ABCDEF".toCharArray();
    
    while (n > 0) {
        hex.insert(0, hexChars[n % 16]);
        n /= 16;
    }
    
    return hex.toString();
}
```

## Decimal to Octal Conversion

### Method: Division by 8
1. Divide the decimal number by 8
2. Record the remainder (0-7)
3. Divide the quotient by 8
4. Repeat until the quotient becomes 0
5. Read the remainders from bottom to top

Example: Convert 100 to octal
```
100 ÷ 8 = 12 remainder 4
12  ÷ 8 = 1  remainder 4
1   ÷ 8 = 0  remainder 1
```
Reading from bottom to top: 144

### Java Implementation
```java
public String decimalToOctal(int n) {
    if (n == 0) return "0";
    
    StringBuilder octal = new StringBuilder();
    while (n > 0) {
        octal.insert(0, n % 8);
        n /= 8;
    }
    
    return octal.toString();
}
```

## Decimal Fractions

Decimal fractions represent parts of a whole using powers of 10:

| Fraction | Decimal | Representation |
|----------|---------|----------------|
| 1/10 | 0.1 | 1×10⁻¹ |
| 1/100 | 0.01 | 1×10⁻² |
| 1/1000 | 0.001 | 1×10⁻³ |

### Binary Representation Issues

Some decimal fractions cannot be represented exactly in binary, which leads to floating-point precision issues:

Example: 0.1 in binary is 0.0001100110011... (infinitely repeating)

This is why floating-point calculations sometimes produce results like:
```
0.1 + 0.2 = 0.30000000000000004
```

## Scientific Notation

For very large or small numbers, scientific notation is used:

| Decimal | Scientific Notation |
|---------|---------------------|
| 1,234,000 | 1.234 × 10⁶ |
| 0.000001234 | 1.234 × 10⁻⁶ |

In programming, this is often written with "e" notation:
```
1.234e6  (1.234 × 10⁶)
1.234e-6 (1.234 × 10⁻⁶)
```

## Floating-Point Representation

Computers typically use IEEE 754 floating-point standard:

### Single Precision (32-bit)
- 1 bit for sign
- 8 bits for exponent
- 23 bits for fraction (mantissa)

### Double Precision (64-bit)
- 1 bit for sign
- 11 bits for exponent
- 52 bits for fraction (mantissa)

### Java Implementation
```java
float f = 1.234f;  // 32-bit floating point
double d = 1.234;  // 64-bit floating point (default)
```

## BigDecimal for Precision

For exact decimal arithmetic (e.g., financial calculations):

```java
import java.math.BigDecimal;

BigDecimal a = new BigDecimal("0.1");
BigDecimal b = new BigDecimal("0.2");
BigDecimal sum = a.add(b);  // Exactly 0.3
```

## Decimal in Computer Science

### Character Encoding

ASCII represents characters using decimal values 0-127:

| Decimal | Character |
|---------|-----------|
| 48-57 | Digits 0-9 |
| 65-90 | Uppercase A-Z |
| 97-122 | Lowercase a-z |

### Parsing Strings to Numbers

```java
// String to integer
String str = "123";
int num = Integer.parseInt(str);

// String to double
String str = "123.45";
double num = Double.parseDouble(str);
```

### Formatting Numbers as Strings

```java
// Integer to String
int num = 123;
String str = Integer.toString(num);
// or
String str = String.valueOf(num);
// or
String str = String.format("%d", num);

// Double to String
double num = 123.45;
String str = String.format("%.2f", num);  // "123.45" (2 decimal places)
```

## Number Systems Comparison

| Number System | Base | Digits |
|---------------|------|--------|
| Binary | 2 | 0, 1 |
| Octal | 8 | 0-7 |
| Decimal | 10 | 0-9 |
| Hexadecimal | 16 | 0-9, A-F |

## Performance Considerations

### Integer vs. Floating-Point Operations

- Integer operations are generally faster than floating-point operations
- Use integers when possible (e.g., storing cents instead of dollars and cents)

### BigInteger and BigDecimal

- Provide arbitrary precision but are much slower than primitive types
- Use only when necessary for large numbers or exact decimal arithmetic