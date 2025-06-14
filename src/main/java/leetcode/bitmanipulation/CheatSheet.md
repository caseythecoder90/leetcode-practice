# Bit Manipulation Cheat Sheet

## Basic Operations

### Get Bit
```java
boolean getBit(int num, int i) {
    return ((num & (1 << i)) != 0);
}
```

### Set Bit
```java
int setBit(int num, int i) {
    return num | (1 << i);
}
```

### Clear Bit
```java
int clearBit(int num, int i) {
    int mask = ~(1 << i);
    return num & mask;
}
```

### Clear Bits MSB through i (inclusive)
```java
int clearBitsMSBthroughI(int num, int i) {
    int mask = (1 << i) - 1;
    return num & mask;
}
```

### Clear Bits i through 0 (inclusive)
```java
int clearBitsIthrough0(int num, int i) {
    int mask = (-1 << (i + 1));
    return num & mask;
}
```

### Update Bit
```java
int updateBit(int num, int i, boolean value) {
    int valueNum = value ? 1 : 0;
    int mask = ~(1 << i);
    return (num & mask) | (valueNum << i);
}
```

## Bit Tricks

### Check if Power of 2
```java
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

### Count Set Bits (Brian Kernighan's algorithm)
```java
int countSetBits(int n) {
    int count = 0;
    while (n != 0) {
        n &= (n - 1); // Clear the least significant set bit
        count++;
    }
    return count;
}
```

### Find Log Base 2 of 32-bit Integer
```java
int log2(int n) {
    int result = 0;
    while (n > 1) {
        n >>= 1;
        result++;
    }
    return result;
}
```

### Least Significant Bit
```java
int lsb(int n) {
    return n & -n;
}
```

### Turn off Rightmost Set Bit
```java
int turnOffRightmostSetBit(int n) {
    return n & (n - 1);
}
```

### Isolate Rightmost 0 Bit
```java
int isolateRightmostZeroBit(int n) {
    return ~n & (n + 1);
}
```

### Check if ith Bit is Set
```java
boolean isIthBitSet(int n, int i) {
    return (n & (1 << i)) != 0;
}
```

### XOR Properties
1. `a ^ 0 = a`
2. `a ^ a = 0`
3. `a ^ b ^ a = b` (useful for finding single number)
4. `(a ^ b) ^ c = a ^ (b ^ c)` (associative)

### Swap Two Numbers Without Temp Variable
```java
void swap(int[] arr, int i, int j) {
    if (i != j) {
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
    }
}
```

## Common Bit Masks

```java
// Mask with n least significant bits set to 1
int lsbMask(int n) {
    return (1 << n) - 1;
}

// Hexadecimal masks for 32-bit integers
int MASK_0xFF = 0xFF;               // 8 least significant bits
int MASK_0xFFFF = 0xFFFF;           // 16 least significant bits
int MASK_0xFFFFFF = 0xFFFFFF;       // 24 least significant bits
int MASK_0x55555555 = 0x55555555;   // Even bits (0, 2, 4, ...) [0101 0101...]
int MASK_0x33333333 = 0x33333333;   // [0011 0011...]
int MASK_0x0F0F0F0F = 0x0F0F0F0F;   // [0000 1111...]
int MASK_0x00FF00FF = 0x00FF00FF;   // [0000 0000 1111 1111...]
```

## Bit Shift Operations

```java
// Multiply by 2^k
int multiplyByPowerOf2(int n, int k) {
    return n << k;
}

// Divide by 2^k
int divideByPowerOf2(int n, int k) {
    return n >> k;
}

// Modulo operation when divisor is a power of 2
int moduloPowerOf2(int n, int k) {
    return n & ((1 << k) - 1);
}
```

## Advanced Bit Manipulation

### Reverse Bits
```java
int reverseBits(int n) {
    n = ((n & 0xFFFF0000) >>> 16) | ((n & 0x0000FFFF) << 16);
    n = ((n & 0xFF00FF00) >>> 8) | ((n & 0x00FF00FF) << 8);
    n = ((n & 0xF0F0F0F0) >>> 4) | ((n & 0x0F0F0F0F) << 4);
    n = ((n & 0xCCCCCCCC) >>> 2) | ((n & 0x33333333) << 2);
    n = ((n & 0xAAAAAAAA) >>> 1) | ((n & 0x55555555) << 1);
    return n;
}
```

### Count Trailing Zeros
```java
int countTrailingZeros(int n) {
    if (n == 0) return 32;
    int count = 0;
    while ((n & 1) == 0) {
        count++;
        n >>= 1;
    }
    return count;
}
```

### Find Parity (even or odd number of set bits)
```java
boolean hasParity(int n) {
    boolean parity = false;
    while (n != 0) {
        parity = !parity;
        n &= (n - 1);
    }
    return parity;
}
```

### Next Power of 2
```java
int nextPowerOf2(int n) {
    n--;
    n |= n >> 1;
    n |= n >> 2;
    n |= n >> 4;
    n |= n >> 8;
    n |= n >> 16;
    return n + 1;
}
```

## Common Interview Problems

1. Find the single number in an array where all other numbers appear twice
   ```java
   int singleNumber(int[] nums) {
       int result = 0;
       for (int num : nums) {
           result ^= num;
       }
       return result;
   }
   ```

2. Count number of bits to flip to convert A to B
   ```java
   int bitsToFlip(int a, int b) {
       return countSetBits(a ^ b);
   }
   ```

3. Check if a number is a power of 4
   ```java
   boolean isPowerOf4(int n) {
       // Check if only one bit is set (power of 2)
       // AND check if that bit is at an even position
       return n > 0 && (n & (n - 1)) == 0 && (n & 0xAAAAAAAA) == 0;
   }
   ```