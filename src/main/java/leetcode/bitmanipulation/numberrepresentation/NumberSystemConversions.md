# Number System Conversions

This document provides a comprehensive reference for converting between different number systems in computer science, including decimal, binary, hexadecimal, and octal.

## Conversion Methods Overview

| From \ To | Decimal | Binary | Hexadecimal | Octal |
|-----------|---------|--------|-------------|-------|
| Decimal | - | Division by 2 | Division by 16 | Division by 8 |
| Binary | Sum powers of 2 | - | Group by 4 bits | Group by 3 bits |
| Hexadecimal | Sum powers of 16 | Expand each digit to 4 bits | - | Convert to binary first |
| Octal | Sum powers of 8 | Expand each digit to 3 bits | Convert to binary first | - |

## Direct Conversion Algorithms

### Decimal to Any Base

```java
public String decimalToAnyBase(int decimal, int base) {
    if (decimal == 0) return "0";
    
    StringBuilder result = new StringBuilder();
    String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    while (decimal > 0) {
        int remainder = decimal % base;
        result.insert(0, digits.charAt(remainder));
        decimal /= base;
    }
    
    return result.toString();
}
```

### Any Base to Decimal

```java
public int anyBaseToDecimal(String number, int base) {
    int result = 0;
    String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    number = number.toUpperCase();
    
    for (int i = 0; i < number.length(); i++) {
        char c = number.charAt(i);
        int digit = digits.indexOf(c);
        result = result * base + digit;
    }
    
    return result;
}
```

## Common Base Conversion Table

Here's a conversion table for values 0-16 across the common number systems:

| Decimal | Binary | Hexadecimal | Octal |
|---------|--------|-------------|-------|
| 0 | 0 | 0 | 0 |
| 1 | 1 | 1 | 1 |
| 2 | 10 | 2 | 2 |
| 3 | 11 | 3 | 3 |
| 4 | 100 | 4 | 4 |
| 5 | 101 | 5 | 5 |
| 6 | 110 | 6 | 6 |
| 7 | 111 | 7 | 7 |
| 8 | 1000 | 8 | 10 |
| 9 | 1001 | 9 | 11 |
| 10 | 1010 | A | 12 |
| 11 | 1011 | B | 13 |
| 12 | 1100 | C | 14 |
| 13 | 1101 | D | 15 |
| 14 | 1110 | E | 16 |
| 15 | 1111 | F | 17 |
| 16 | 10000 | 10 | 20 |

## Specific Conversion Methods

### Decimal to Binary

#### Method 1: Division by 2
1. Divide the decimal number by 2
2. Record the remainder (0 or 1)
3. Divide the quotient by 2
4. Repeat until the quotient becomes 0
5. Read the remainders from bottom to top

```java
public String decimalToBinary(int n) {
    return decimalToAnyBase(n, 2);
}
```

#### Method 2: Built-in Java Method
```java
public String decimalToBinary(int n) {
    return Integer.toBinaryString(n);
}
```

### Binary to Decimal

#### Method 1: Sum Powers of 2
1. For each bit position, multiply the bit (0 or 1) by 2 raised to the power of its position
2. Sum these values

```java
public int binaryToDecimal(String binary) {
    return anyBaseToDecimal(binary, 2);
}
```

#### Method 2: Built-in Java Method
```java
public int binaryToDecimal(String binary) {
    return Integer.parseInt(binary, 2);
}
```

### Decimal to Hexadecimal

#### Method 1: Division by 16
1. Divide the decimal number by 16
2. Record the remainder (0-15, represented as 0-F)
3. Divide the quotient by 16
4. Repeat until the quotient becomes 0
5. Read the remainders from bottom to top

```java
public String decimalToHex(int n) {
    return decimalToAnyBase(n, 16);
}
```

#### Method 2: Built-in Java Method
```java
public String decimalToHex(int n) {
    return Integer.toHexString(n).toUpperCase();
}
```

### Hexadecimal to Decimal

#### Method 1: Sum Powers of 16
1. For each digit position, multiply the digit value by 16 raised to the power of its position
2. Sum these values

```java
public int hexToDecimal(String hex) {
    return anyBaseToDecimal(hex, 16);
}
```

#### Method 2: Built-in Java Method
```java
public int hexToDecimal(String hex) {
    return Integer.parseInt(hex, 16);
}
```

### Decimal to Octal

#### Method 1: Division by 8
1. Divide the decimal number by 8
2. Record the remainder (0-7)
3. Divide the quotient by 8
4. Repeat until the quotient becomes 0
5. Read the remainders from bottom to top

```java
public String decimalToOctal(int n) {
    return decimalToAnyBase(n, 8);
}
```

#### Method 2: Built-in Java Method
```java
public String decimalToOctal(int n) {
    return Integer.toOctalString(n);
}
```

### Octal to Decimal

#### Method 1: Sum Powers of 8
1. For each digit position, multiply the digit value by 8 raised to the power of its position
2. Sum these values

```java
public int octalToDecimal(String octal) {
    return anyBaseToDecimal(octal, 8);
}
```

#### Method 2: Built-in Java Method
```java
public int octalToDecimal(String octal) {
    return Integer.parseInt(octal, 8);
}
```

## Binary-Hexadecimal-Octal Direct Conversions

### Binary to Hexadecimal
1. Group binary digits into sets of 4, starting from the right
2. Convert each group to its hexadecimal equivalent

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

### Hexadecimal to Binary
1. Convert each hexadecimal digit to its 4-bit binary equivalent
2. Concatenate the binary groups

```java
public String hexToBinary(String hex) {
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < hex.length(); i++) {
        int decimal = Character.digit(hex.charAt(i), 16);
        String bin = Integer.toBinaryString(decimal);
        
        // Pad to 4 bits
        while (bin.length() < 4) {
            bin = "0" + bin;
        }
        
        binary.append(bin);
    }
    
    // Remove leading zeros (unless result is just "0")
    while (binary.length() > 1 && binary.charAt(0) == '0') {
        binary.deleteCharAt(0);
    }
    
    return binary.toString();
}
```

### Binary to Octal
1. Group binary digits into sets of 3, starting from the right
2. Convert each group to its octal equivalent

```java
public String binaryToOctal(String binary) {
    // Pad with zeros to make length a multiple of 3
    while (binary.length() % 3 != 0) {
        binary = "0" + binary;
    }
    
    StringBuilder octal = new StringBuilder();
    for (int i = 0; i < binary.length(); i += 3) {
        String chunk = binary.substring(i, i + 3);
        int decimal = Integer.parseInt(chunk, 2);
        octal.append(decimal);
    }
    
    return octal.toString();
}
```

### Octal to Binary
1. Convert each octal digit to its 3-bit binary equivalent
2. Concatenate the binary groups

```java
public String octalToBinary(String octal) {
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < octal.length(); i++) {
        int decimal = Character.digit(octal.charAt(i), 8);
        String bin = Integer.toBinaryString(decimal);
        
        // Pad to 3 bits
        while (bin.length() < 3) {
            bin = "0" + bin;
        }
        
        binary.append(bin);
    }
    
    // Remove leading zeros (unless result is just "0")
    while (binary.length() > 1 && binary.charAt(0) == '0') {
        binary.deleteCharAt(0);
    }
    
    return binary.toString();
}
```

### Hexadecimal to Octal
1. Convert hexadecimal to binary
2. Convert binary to octal

```java
public String hexToOctal(String hex) {
    String binary = hexToBinary(hex);
    return binaryToOctal(binary);
}
```

### Octal to Hexadecimal
1. Convert octal to binary
2. Convert binary to hexadecimal

```java
public String octalToHex(String octal) {
    String binary = octalToBinary(octal);
    return binaryToHex(binary);
}
```

## Binary Representation of Negative Numbers

### Signed Magnitude
- The leftmost bit is the sign bit (0 for positive, 1 for negative)
- The remaining bits represent the magnitude

```
+5 = 0 0000101
-5 = 1 0000101
```

### One's Complement
- Positive numbers are represented normally
- Negative numbers are represented by inverting all bits of the positive representation

```
+5 = 00000101
-5 = 11111010
```

### Two's Complement (Most Common)
- Positive numbers are represented normally
- Negative numbers are represented by inverting all bits and adding 1

```
+5 = 00000101
-5 = 11111011
```

## Floating-Point Conversions

### Decimal Fractions to Binary
1. Multiply the fractional part by 2
2. Record the integer part (0 or 1)
3. Repeat with the new fractional part until it becomes 0 or you reach desired precision
4. Read the bits from top to bottom

Example: Convert 0.625 to binary
```
0.625 × 2 = 1.25  (record 1)
0.25 × 2 = 0.5    (record 0)
0.5 × 2 = 1.0     (record 1)
```
Result: 0.101 (binary)

### IEEE 754 Floating-Point Format

#### Single Precision (32-bit)
- 1 bit for sign
- 8 bits for exponent (biased by 127)
- 23 bits for fraction (implied leading 1)

#### Double Precision (64-bit)
- 1 bit for sign
- 11 bits for exponent (biased by 1023)
- 52 bits for fraction (implied leading 1)

## Practical Conversion Examples

### IP Address Conversion
Converting IPv4 address from decimal to binary:

```java
public String ipToBinary(String ip) {
    String[] octets = ip.split("\\.");
    StringBuilder binary = new StringBuilder();
    
    for (String octet : octets) {
        int decimal = Integer.parseInt(octet);
        String bin = Integer.toBinaryString(decimal);
        
        // Pad to 8 bits
        while (bin.length() < 8) {
            bin = "0" + bin;
        }
        
        binary.append(bin).append(".");
    }
    
    // Remove trailing dot
    return binary.substring(0, binary.length() - 1);
}
```

### RGB Color Conversion
Converting RGB color to hexadecimal:

```java
public String rgbToHex(int r, int g, int b) {
    return String.format("#%02X%02X%02X", r, g, b);
}
```

Converting hexadecimal color to RGB:

```java
public int[] hexToRgb(String hex) {
    // Remove # if present
    if (hex.startsWith("#")) {
        hex = hex.substring(1);
    }
    
    int r = Integer.parseInt(hex.substring(0, 2), 16);
    int g = Integer.parseInt(hex.substring(2, 4), 16);
    int b = Integer.parseInt(hex.substring(4, 6), 16);
    
    return new int[] {r, g, b};
}
```

## Base64 Encoding/Decoding

Base64 is a binary-to-text encoding scheme that represents binary data in ASCII format:

```java
import java.util.Base64;

// Encoding
public String encodeBase64(byte[] data) {
    return Base64.getEncoder().encodeToString(data);
}

// Decoding
public byte[] decodeBase64(String base64) {
    return Base64.getDecoder().decode(base64);
}
```

## Choosing the Right Number System

| Number System | Best Used For |
|---------------|---------------|
| Decimal | Human-readable values, input/output |
| Binary | Raw bit manipulation, logical operations |
| Hexadecimal | Memory addresses, compact binary representation, color codes |
| Octal | Unix file permissions, legacy systems |

## Performance Considerations

When implementing number system conversions:

1. Use built-in language methods when available (faster and less error-prone)
2. For custom implementations, choose algorithms that minimize string operations
3. Pre-compute lookup tables for frequent conversions
4. Consider bit-manipulation techniques for performance-critical code
5. Be aware of overflow/underflow issues with fixed-size integer types