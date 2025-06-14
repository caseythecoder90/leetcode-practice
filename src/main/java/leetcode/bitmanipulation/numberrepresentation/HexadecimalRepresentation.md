# Hexadecimal Number System

## Overview

Hexadecimal (hex) is a base-16 number system that uses 16 distinct symbols: the digits 0-9 and the letters A-F (or a-f). Hexadecimal is commonly used in computing as a more human-friendly way to represent binary values, where each hex digit corresponds to exactly 4 binary digits (bits).

## Hexadecimal Digits

| Decimal | Hexadecimal | Binary |
|---------|-------------|--------|
| 0 | 0 | 0000 |
| 1 | 1 | 0001 |
| 2 | 2 | 0010 |
| 3 | 3 | 0011 |
| 4 | 4 | 0100 |
| 5 | 5 | 0101 |
| 6 | 6 | 0110 |
| 7 | 7 | 0111 |
| 8 | 8 | 1000 |
| 9 | 9 | 1001 |
| 10 | A | 1010 |
| 11 | B | 1011 |
| 12 | C | 1100 |
| 13 | D | 1101 |
| 14 | E | 1110 |
| 15 | F | 1111 |

## Hexadecimal Counting

Each position in a hexadecimal number represents a power of 16:

| Hexadecimal | Calculation | Decimal |
|-------------|-------------|---------|
| 1 | 1×16⁰ | 1 |
| 10 | 1×16¹ + 0×16⁰ | 16 |
| 1F | 1×16¹ + 15×16⁰ | 31 |
| FF | 15×16¹ + 15×16⁰ | 255 |
| 100 | 1×16² + 0×16¹ + 0×16⁰ | 256 |
| 1A9 | 1×16² + 10×16¹ + 9×16⁰ | 425 |
| FFFF | 15×16³ + 15×16² + 15×16¹ + 15×16⁰ | 65,535 |

## Powers of 16

Important powers of 16 to remember:

| Power | Value | Hexadecimal |
|-------|-------|-------------|
| 16⁰ | 1 | 1 |
| 16¹ | 16 | 10 |
| 16² | 256 | 100 |
| 16³ | 4,096 | 1000 |
| 16⁴ | 65,536 | 10000 |
| 16⁸ | 4,294,967,296 | 100000000 |

## Decimal to Hexadecimal Conversion

### Method 1: Division by 16
1. Divide the decimal number by 16
2. Record the remainder (0-15, represented as 0-F)
3. Divide the quotient by 16
4. Repeat until the quotient becomes 0
5. Read the remainders from bottom to top

Example: Convert 427 to hexadecimal
```
427 ÷ 16 = 26 remainder 11 (B)
26 ÷ 16 = 1  remainder 10 (A)
1 ÷ 16 = 0   remainder 1  (1)
```
Reading from bottom to top: 1AB

### Method 2: Grouping Binary Digits
1. Convert the decimal number to binary
2. Group the binary digits into sets of 4, starting from the right
3. Convert each group to its hexadecimal equivalent

Example: Convert 427 to hexadecimal
- 427 in binary: 110101011
- Grouped: 0001 1010 1011
- Hexadecimal: 1AB

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

## Hexadecimal to Decimal Conversion

To convert hexadecimal to decimal, multiply each digit by the corresponding power of 16 and sum the results.

Example: Convert 1AB to decimal
```
1×16² + 10×16¹ + 11×16⁰
= 256 + 160 + 11
= 427
```

### Java Implementation
```java
public int hexToDecimal(String hex) {
    int decimal = 0;
    String digits = "0123456789ABCDEF";
    hex = hex.toUpperCase();
    
    for (int i = 0; i < hex.length(); i++) {
        char c = hex.charAt(i);
        int d = digits.indexOf(c);
        decimal = 16 * decimal + d;
    }
    
    return decimal;
}
```

## Binary to Hexadecimal Conversion

1. Group the binary digits into sets of 4, starting from the right
2. Convert each group to its hexadecimal equivalent

Example: Convert 11010110 to hexadecimal
- Grouped: 1101 0110
- Hexadecimal: D6

### Java Implementation
```java
public String binaryToHex(String binary) {
    // Pad with zeros to make length a multiple of 4
    while (binary.length() % 4 != 0) {
        binary = "0" + binary;
    }
    
    StringBuilder hex = new StringBuilder();
    for (int i = 0; i < binary.length(); i += 4) {
        String chunk = binary.substring(i, i + 4);
        int decimal = Integer.parseInt(chunk, 2);
        hex.append(Integer.toHexString(decimal).toUpperCase());
    }
    
    return hex.toString();
}
```

## Hexadecimal to Binary Conversion

1. Convert each hexadecimal digit to its 4-bit binary equivalent
2. Concatenate the binary groups

Example: Convert D6 to binary
- D (13) in binary: 1101
- 6 in binary: 0110
- Combined: 11010110

### Java Implementation
```java
public String hexToBinary(String hex) {
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < hex.length(); i++) {
        char c = hex.charAt(i);
        int decimal = Character.digit(c, 16);
        
        // Convert to 4-bit binary representation
        String bin = Integer.toBinaryString(decimal);
        while (bin.length() < 4) {
            bin = "0" + bin;  // Pad with leading zeros
        }
        
        binary.append(bin);
    }
    
    return binary.toString();
}
```

## Hexadecimal in Computer Science

### Common Uses

1. **Memory Addresses**: Hexadecimal is commonly used to represent memory addresses in computing
   ```
   0x7FFEE400B9C8
   ```

2. **Color Representation**: RGB colors in web development
   ```
   #FF5733 (RGB: R=255, G=87, B=51)
   ```

3. **Byte Representation**: Each byte can be represented as two hex digits
   ```
   00 FF C3 52 9A
   ```

4. **Machine Code and Assembly**: Hexadecimal is often used to represent machine code
   ```
   48 65 6C 6C 6F ("Hello" in ASCII/hex)
   ```

5. **Error Codes**: System error codes are often displayed in hexadecimal
   ```
   0x0000000C
   ```

### Hexadecimal Notation

In programming languages, hexadecimal literals are typically prefixed:
- C, C++, Java, JavaScript: `0x` prefix (e.g., `0x1A3F`)
- HTML/CSS: `#` prefix (e.g., `#1A3F`)
- Assembly: `$` or `0x` or `h` suffix (e.g., `$1A3F`, `0x1A3F`, or `1A3Fh`)

## Bitwise Operations with Hexadecimal

Hexadecimal is often used when working with bitwise operations because it's more compact than binary and each hex digit represents exactly 4 bits.

| Operation | Example | Result |
|-----------|---------|--------|
| AND (&) | 0xA5 & 0xF0 | 0xA0 |
| OR (\|) | 0xA5 \| 0x0F | 0xAF |
| XOR (^) | 0xA5 ^ 0xFF | 0x5A |
| NOT (~) | ~0xA5 | 0xFFFFFF5A (in 32-bit) |
| Left Shift (<<) | 0xA5 << 4 | 0xA50 |
| Right Shift (>>) | 0xA5 >> 4 | 0x0A |

## Common Hexadecimal Values

| Hexadecimal | Decimal | Binary | Significance |
|-------------|---------|--------|-------------|
| 0x00 | 0 | 00000000 | Null byte |
| 0x01 | 1 | 00000001 | Smallest non-zero value |
| 0x0A | 10 | 00001010 | Line feed (LF) |
| 0x0D | 13 | 00001101 | Carriage return (CR) |
| 0x1F | 31 | 00011111 | Unit separator |
| 0x20 | 32 | 00100000 | Space |
| 0x7F | 127 | 01111111 | Delete |
| 0x80 | 128 | 10000000 | Smallest negative value in two's complement (8-bit) |
| 0xFF | 255 | 11111111 | Maximum 8-bit value |
| 0x100 | 256 | 100000000 | 2⁸ |
| 0xFFFF | 65535 | 1111111111111111 | Maximum 16-bit value |
| 0xFFFFFFFF | 4294967295 | 32 ones | Maximum 32-bit value |

## Hexadecimal Bitmasks

Common bitmasks used in programming:

| Mask | Purpose |
|------|---------|
| 0xFF | Mask to extract the least significant byte |
| 0xFF00 | Mask to extract the second byte |
| 0xF0 | Mask to extract the high nibble of a byte |
| 0x0F | Mask to extract the low nibble of a byte |
| 0xAAAAAAAA | Alternate bits (10101010...) |
| 0x55555555 | Alternate bits (01010101...) |
| 0x33333333 | Pattern of two bits (00110011...) |
| 0x0F0F0F0F | Pattern of four bits (00001111...) |

## Applying Hexadecimal in Bit Manipulation

### Extract a Byte from an Integer
```java
int extractByte(int value, int bytePosition) {
    return (value >> (bytePosition * 8)) & 0xFF;
}
```

### Set a Byte in an Integer
```java
int setByte(int value, int bytePosition, int byteValue) {
    int mask = 0xFF << (bytePosition * 8);
    return (value & ~mask) | ((byteValue & 0xFF) << (bytePosition * 8));
}
```

### Swap the Order of Bytes (Endianness)
```java
int swapEndianness(int value) {
    return ((value & 0xFF) << 24) |
           ((value & 0xFF00) << 8) |
           ((value & 0xFF0000) >> 8) |
           ((value & 0xFF000000) >>> 24);
}
```