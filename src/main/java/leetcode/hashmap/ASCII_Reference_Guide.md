# ASCII Reference Guide for Array-Based Frequency Maps

## Quick Reference Table

### Most Common ASCII Values for Coding Problems

| Character | ASCII Value | Binary | Hex | Memory Trick |
|-----------|------------|--------|-----|--------------|
| **'0'** | **48** | 0011 0000 | 0x30 | "0 at 48" |
| '1' | 49 | 0011 0001 | 0x31 | |
| ... | ... | ... | ... | |
| **'9'** | **57** | 0011 1001 | 0x39 | "9 at 57" |
| | | | | |
| **'A'** | **65** | 0100 0001 | 0x41 | "A at 65" (Medicare age) |
| 'B' | 66 | 0100 0010 | 0x42 | |
| ... | ... | ... | ... | |
| **'Z'** | **90** | 0101 1010 | 0x5A | "Z at 90" |
| | | | | |
| **'a'** | **97** | 0110 0001 | 0x61 | "a at 97" (32 after 'A') |
| 'b' | 98 | 0110 0010 | 0x62 | |
| ... | ... | ... | ... | |
| **'z'** | **122** | 0111 1010 | 0x7A | "z at 122" |

### Special Characters

| Character | ASCII Value | Description |
|-----------|------------|-------------|
| Space ' ' | 32 | Space character |
| '!' | 33 | Exclamation |
| '"' | 34 | Double quote |
| '#' | 35 | Hash/Pound |
| '$' | 36 | Dollar |
| '%' | 37 | Percent |
| '&' | 38 | Ampersand |
| ''' | 39 | Single quote |
| '(' | 40 | Left parenthesis |
| ')' | 41 | Right parenthesis |
| '*' | 42 | Asterisk |
| '+' | 43 | Plus |
| ',' | 44 | Comma |
| '-' | 45 | Hyphen/Minus |
| '.' | 46 | Period |
| '/' | 47 | Forward slash |

## Key Patterns to Remember

### The Magic Numbers
- **48**: Start of digits ('0')
- **65**: Start of uppercase ('A')
- **97**: Start of lowercase ('a')
- **32**: Difference between upper and lowercase

### Memory Tricks
1. **Digits**: '0' = 48, just remember "48" and add 0-9
2. **Uppercase**: 'A' = 65 (Medicare eligibility age in US)
3. **Lowercase**: 'a' = 97 (65 + 32)
4. **Case difference**: lowercase - uppercase = 32 (power of 2!)

## Array-Based Frequency Mapping Patterns

### Pattern 1: Lowercase Letters Only (Most Common)
```java
int[] freq = new int[26];  // 26 letters

// Storing frequency
for (char c : str.toCharArray()) {
    freq[c - 'a']++;  // 'a' becomes index 0, 'b' becomes 1, etc.
}

// Why it works:
// 'a' - 'a' = 97 - 97 = 0
// 'b' - 'a' = 98 - 97 = 1
// 'z' - 'a' = 122 - 97 = 25
```

### Pattern 2: Uppercase Letters Only
```java
int[] freq = new int[26];

for (char c : str.toCharArray()) {
    freq[c - 'A']++;  // 'A' becomes index 0, 'B' becomes 1, etc.
}

// Why it works:
// 'A' - 'A' = 65 - 65 = 0
// 'B' - 'A' = 66 - 65 = 1
// 'Z' - 'A' = 90 - 65 = 25
```

### Pattern 3: Digits Only
```java
int[] freq = new int[10];  // 10 digits

for (char c : str.toCharArray()) {
    freq[c - '0']++;  // '0' becomes index 0, '1' becomes 1, etc.
}

// Why it works:
// '0' - '0' = 48 - 48 = 0
// '1' - '0' = 49 - 48 = 1
// '9' - '0' = 57 - 48 = 9
```

### Pattern 4: All ASCII Characters (0-127)
```java
int[] freq = new int[128];  // Full ASCII table

for (char c : str.toCharArray()) {
    freq[c]++;  // Direct indexing, no subtraction needed
}

// Examples:
// freq[32] = count of spaces
// freq[65] = count of 'A'
// freq[97] = count of 'a'
```

### Pattern 5: Extended ASCII (0-255)
```java
int[] freq = new int[256];  // Extended ASCII

for (char c : str.toCharArray()) {
    freq[c]++;
}
```

### Pattern 6: Letters Only (Mixed Case)
```java
int[] freq = new int[52];  // 26 uppercase + 26 lowercase

for (char c : str.toCharArray()) {
    if (c >= 'A' && c <= 'Z') {
        freq[c - 'A']++;  // Uppercase: indices 0-25
    } else if (c >= 'a' && c <= 'z') {
        freq[c - 'a' + 26]++;  // Lowercase: indices 26-51
    }
}
```

## Visual ASCII Table (Printable Characters)

```
Dec  Char | Dec  Char | Dec  Char | Dec  Char
---------|----------|----------|----------
32  space | 48   0   | 64   @   | 80   P
33   !    | 49   1   | 65   A   | 81   Q
34   "    | 50   2   | 66   B   | 82   R
35   #    | 51   3   | 67   C   | 83   S
36   $    | 52   4   | 68   D   | 84   T
37   %    | 53   5   | 69   E   | 85   U
38   &    | 54   6   | 70   F   | 86   V
39   '    | 55   7   | 71   G   | 87   W
40   (    | 56   8   | 72   H   | 88   X
41   )    | 57   9   | 73   I   | 89   Y
42   *    | 58   :   | 74   J   | 90   Z
43   +    | 59   ;   | 75   K   | 91   [
44   ,    | 60   <   | 76   L   | 92   \
45   -    | 61   =   | 77   M   | 93   ]
46   .    | 62   >   | 78   N   | 94   ^
47   /    | 63   ?   | 79   O   | 95   _

Dec  Char | Dec  Char | Dec  Char
---------|----------|----------
96   `    | 106  j   | 116  t
97   a    | 107  k   | 117  u
98   b    | 108  l   | 118  v
99   c    | 109  m   | 119  w
100  d    | 110  n   | 120  x
101  e    | 111  o   | 121  y
102  f    | 112  p   | 122  z
103  g    | 113  q   | 123  {
104  h    | 114  r   | 124  |
105  i    | 115  s   | 125  }
                      | 126  ~
```

## Common Interview Patterns

### 1. Check if Character is Letter
```java
// Method 1: Using Character class
if (Character.isLetter(c)) { }

// Method 2: Manual check
if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) { }
```

### 2. Convert Case
```java
// Uppercase to lowercase
char lower = (char)(c + 32);  // Only if c is uppercase
char lower = Character.toLowerCase(c);  // Safe method

// Lowercase to uppercase
char upper = (char)(c - 32);  // Only if c is lowercase
char upper = Character.toUpperCase(c);  // Safe method

// Toggle case
char toggled = (char)(c ^ 32);  // XOR with 32 toggles case
```

### 3. Check Character Type
```java
// Is digit?
if (c >= '0' && c <= '9') { }

// Is lowercase?
if (c >= 'a' && c <= 'z') { }

// Is uppercase?
if (c >= 'A' && c <= 'Z') { }

// Is alphanumeric?
if ((c >= '0' && c <= '9') ||
    (c >= 'a' && c <= 'z') ||
    (c >= 'A' && c <= 'Z')) { }
```

## Practice Examples

### Example 1: Count All Characters
```java
String s = "Hello World!";
int[] count = new int[128];  // Full ASCII

for (char c : s.toCharArray()) {
    count[c]++;
}

// Results:
// count[32] = 1  (space)
// count[33] = 1  (!)
// count[72] = 1  (H)
// count[101] = 1 (e)
// count[108] = 3 (l)
// count[111] = 2 (o)
// etc.
```

### Example 2: Lowercase Only
```java
String s = "hello";
int[] count = new int[26];

for (char c : s.toCharArray()) {
    count[c - 'a']++;
}

// Results:
// count[7] = 1   (h: 104 - 97 = 7)
// count[4] = 1   (e: 101 - 97 = 4)
// count[11] = 2  (l: 108 - 97 = 11)
// count[14] = 1  (o: 111 - 97 = 14)
```

### Example 3: Case Insensitive
```java
String s = "HeLLo";
int[] count = new int[26];

for (char c : s.toCharArray()) {
    char lower = Character.toLowerCase(c);
    count[lower - 'a']++;
}

// All letters treated as lowercase
```

## Quick Conversion Formulas

| From | To | Formula |
|------|-----|---------|
| Character | ASCII value | `(int) c` |
| ASCII value | Character | `(char) value` |
| Lowercase letter | Array index | `c - 'a'` |
| Uppercase letter | Array index | `c - 'A'` |
| Digit character | Numeric value | `c - '0'` |
| Array index | Lowercase letter | `(char)(index + 'a')` |
| Array index | Uppercase letter | `(char)(index + 'A')` |
| Numeric value | Digit character | `(char)(value + '0')` |

## Tips for Remembering

1. **The Rule of 32**: Uppercase and lowercase differ by 32
   - 'A' (65) + 32 = 'a' (97)
   - This is 2^5, which is why bit manipulation works

2. **Digit Pattern**: Digits start at 48
   - '0' = 48, and digits are consecutive
   - To get numeric value: subtract 48 (or '0')

3. **Letter Patterns**:
   - Uppercase: A=65 to Z=90 (26 letters)
   - Lowercase: a=97 to z=122 (26 letters)
   - Gap between Z and a: 6 characters

4. **Common Array Sizes**:
   - 26: Lowercase OR uppercase only
   - 52: Both cases separately
   - 62: Alphanumeric (26+26+10)
   - 128: Full ASCII
   - 256: Extended ASCII

## When to Use Each Approach

| Scenario | Array Size | Reason |
|----------|------------|--------|
| Lowercase only | 26 | Most memory efficient |
| Mixed case (separate) | 52 | Keep cases distinct |
| Mixed case (together) | 26 | Convert to same case first |
| With digits | 36 or 62 | Include 0-9 |
| Any ASCII | 128 | Simple, no calculation needed |
| Unknown characters | HashMap | Flexible, no size limit |

Remember: **Array indexing is about 10x faster than HashMap**, but requires knowing the character range in advance!