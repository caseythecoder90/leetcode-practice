# Octal Number System

## Overview

The octal number system is a base-8 number system that uses the digits 0-7. While less common than binary or hexadecimal in modern computing, octal is still relevant for certain applications, particularly in Unix/Linux file permissions and some legacy systems.

## Octal Digits

| Decimal | Octal | Binary |
|---------|-------|--------|
| 0 | 0 | 000 |
| 1 | 1 | 001 |
| 2 | 2 | 010 |
| 3 | 3 | 011 |
| 4 | 4 | 100 |
| 5 | 5 | 101 |
| 6 | 6 | 110 |
| 7 | 7 | 111 |

## Octal Counting

Each position in an octal number represents a power of 8:

| Octal | Calculation | Decimal |
|-------|-------------|---------|
| 1 | 1×8⁰ | 1 |
| 10 | 1×8¹ + 0×8⁰ | 8 |
| 17 | 1×8¹ + 7×8⁰ | 15 |
| 77 | 7×8¹ + 7×8⁰ | 63 |
| 100 | 1×8² + 0×8¹ + 0×8⁰ | 64 |
| 177 | 1×8² + 7×8¹ + 7×8⁰ | 127 |
| 777 | 7×8² + 7×8¹ + 7×8⁰ | 511 |

## Powers of 8

Important powers of 8 to remember:

| Power | Value | Octal |
|-------|-------|-------|
| 8⁰ | 1 | 1 |
| 8¹ | 8 | 10 |
| 8² | 64 | 100 |
| 8³ | 512 | 1000 |
| 8⁴ | 4,096 | 10000 |
| 8⁵ | 32,768 | 100000 |

## Decimal to Octal Conversion

### Method 1: Division by 8
1. Divide the decimal number by 8
2. Record the remainder (0-7)
3. Divide the quotient by 8
4. Repeat until the quotient becomes 0
5. Read the remainders from bottom to top

Example: Convert 83 to octal
```
83 ÷ 8 = 10 remainder 3
10 ÷ 8 = 1  remainder 2
1  ÷ 8 = 0  remainder 1
```
Reading from bottom to top: 123

### Method 2: Grouping Binary Digits
1. Convert the decimal number to binary
2. Group the binary digits into sets of 3, starting from the right
3. Convert each group to its octal equivalent

Example: Convert 83 to octal
- 83 in binary: 1010011
- Grouped: 001 010 011
- Octal: 123

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

## Octal to Decimal Conversion

To convert octal to decimal, multiply each digit by the corresponding power of 8 and sum the results.

Example: Convert 123 to decimal
```
1×8² + 2×8¹ + 3×8⁰
= 1×64 + 2×8 + 3×1
= 64 + 16 + 3
= 83
```

### Java Implementation
```java
public int octalToDecimal(String octal) {
    int decimal = 0;
    
    for (int i = 0; i < octal.length(); i++) {
        int digit = Character.digit(octal.charAt(i), 8);
        decimal = decimal * 8 + digit;
    }
    
    return decimal;
}
```

## Binary to Octal Conversion

1. Group the binary digits into sets of 3, starting from the right
2. Convert each group to its octal equivalent

Example: Convert 1010011 to octal
- Grouped: 001 010 011
- Octal: 123

### Java Implementation
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

## Octal to Binary Conversion

1. Convert each octal digit to its 3-bit binary equivalent
2. Concatenate the binary groups

Example: Convert 123 to binary
- 1 in binary: 001
- 2 in binary: 010
- 3 in binary: 011
- Combined: 001010011

### Java Implementation
```java
public String octalToBinary(String octal) {
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < octal.length(); i++) {
        int digit = Character.digit(octal.charAt(i), 8);
        
        // Convert to 3-bit binary representation
        String bits = Integer.toBinaryString(digit);
        while (bits.length() < 3) {
            bits = "0" + bits;  // Pad with leading zeros
        }
        
        binary.append(bits);
    }
    
    // Remove leading zeros (except if the result is just "0")
    while (binary.length() > 1 && binary.charAt(0) == '0') {
        binary.deleteCharAt(0);
    }
    
    return binary.toString();
}
```

## Hexadecimal to Octal Conversion

To convert hexadecimal to octal:
1. Convert hexadecimal to binary
2. Convert binary to octal

### Java Implementation
```java
public String hexToOctal(String hex) {
    // Convert hex to binary
    String binary = hexToBinary(hex);
    
    // Convert binary to octal
    return binaryToOctal(binary);
}

private String hexToBinary(String hex) {
    StringBuilder binary = new StringBuilder();
    for (int i = 0; i < hex.length(); i++) {
        int digit = Character.digit(hex.charAt(i), 16);
        
        // Convert to 4-bit binary representation
        String bits = Integer.toBinaryString(digit);
        while (bits.length() < 4) {
            bits = "0" + bits;
        }
        
        binary.append(bits);
    }
    
    return binary.toString();
}
```

## Octal to Hexadecimal Conversion

To convert octal to hexadecimal:
1. Convert octal to binary
2. Convert binary to hexadecimal

### Java Implementation
```java
public String octalToHex(String octal) {
    // Convert octal to binary
    String binary = octalToBinary(octal);
    
    // Convert binary to hex
    return binaryToHex(binary);
}

private String binaryToHex(String binary) {
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

## Octal in Computer Science

### Unix/Linux File Permissions

The most common use of octal in modern computing is for Unix/Linux file permissions:

```
chmod 755 file.txt
```

The octal number 755 represents:
- 7 (111 in binary): read + write + execute for owner
- 5 (101 in binary): read + execute for group
- 5 (101 in binary): read + execute for others

| Octal | Binary | Permission |
|-------|--------|------------|
| 0 | 000 | No permissions |
| 1 | 001 | Execute only |
| 2 | 010 | Write only |
| 3 | 011 | Write + Execute |
| 4 | 100 | Read only |
| 5 | 101 | Read + Execute |
| 6 | 110 | Read + Write |
| 7 | 111 | Read + Write + Execute |

Common permission patterns:
- 644: Owner can read/write, others can read
- 755: Owner can read/write/execute, others can read/execute
- 600: Only owner can read/write
- 777: Everyone can read/write/execute

### Octal Notation in Programming

In many programming languages, octal literals are prefixed with a `0`:

```java
int octalValue = 0123;  // Octal 123 (decimal 83)
```

In Java 9+, you can use the `0o` prefix for better readability:

```java
int octalValue = 0o123;  // Octal 123 (decimal 83)
```

### Unix/Linux Special File Modes

Beyond basic permissions, the leftmost octal digit can represent special modes:

| Octal | Binary | Meaning |
|-------|--------|---------|
| 1 | 001 | Sticky bit |
| 2 | 010 | Set group ID (SGID) |
| 4 | 100 | Set user ID (SUID) |

Example:
```
chmod 4755 file  // SUID + rwxr-xr-x
```

## Advantages and Disadvantages of Octal

### Advantages
1. Each octal digit corresponds to exactly 3 binary digits, making conversions simple
2. More compact than binary for representing values
3. Well-established use in Unix/Linux file permissions

### Disadvantages
1. Less intuitive than decimal for humans
2. Not as compact as hexadecimal for representing binary data
3. Easy to confuse with decimal if not properly indicated
4. Less common in modern computing than binary or hexadecimal

## Bitwise Operations in Octal Context

Octal is sometimes used when working with specific bit patterns, especially in older systems:

```java
// Extract a specific 3-bit field (positions 3,4,5) from a byte
int extractField(byte value) {
    return (value >> 3) & 07;  // 07 is octal for 7 (binary 111)
}

// Set a specific 3-bit field in a byte
byte setField(byte value, int fieldValue) {
    // Clear the field first
    value &= ~(07 << 3);
    // Set the new field value
    value |= (fieldValue & 07) << 3;
    return value;
}
```

## Historical Significance

Octal was more prevalent in early computing when word sizes were often multiples of 3 bits (6, 9, 12, etc.). As 8-bit bytes and 16/32/64-bit words became standard, hexadecimal (base-16) became more convenient since each hex digit represents exactly 4 bits.

However, octal remains relevant for specific applications, most notably Unix/Linux file permissions, where each permission set (user, group, others) is naturally represented by 3 bits.