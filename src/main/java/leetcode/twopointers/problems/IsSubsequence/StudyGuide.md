# Study Guide: Is Subsequence

## Why This is a Two-Pointers Problem

The "Is Subsequence" problem is a classic example of the **Two-Pointers** pattern because:

1. **Sequential Processing**: We need to process both strings sequentially
2. **Independent Movement**: The two pointers can move at different speeds
3. **Order Preservation**: Must maintain order, can't go backwards
4. **Greedy Matching**: Always match characters when possible, advance both pointers

## Algorithm Walkthrough

### Visualization Example:
```
s = "abc"     <-- sPtr (0)
t = "ahbgdc"  <-- tPtr (0)

Round 1: s[0]='a' == t[0]='a' → MATCH! sPtr=1, tPtr=1
         s = "abc"   <-- sPtr (1)
         t = "ahbgdc" <-- tPtr (1)

Round 2: s[1]='b' != t[1]='h' → NO MATCH! tPtr=2
         s = "abc"   <-- sPtr (1)
         t = "ahbgdc" <-- tPtr (2)

Round 3: s[1]='b' == t[2]='b' → MATCH! sPtr=2, tPtr=3
         s = "abc"   <-- sPtr (2)
         t = "ahbgdc" <-- tPtr (3)

Round 4: s[2]='c' != t[3]='g' → NO MATCH! tPtr=4
         s = "abc"   <-- sPtr (2)
         t = "ahbgdc" <-- tPtr (4)

Round 5: s[2]='c' == t[4]='c' → MATCH! sPtr=3, tPtr=5
         s = "abc"   <-- sPtr (3)
         t = "ahbgdc" <-- tPtr (5)

Result: sPtr == s.length() → TRUE
```

## Key Insights

### Why Two-Pointers Works Here

1. **No Backtracking Needed**: Unlike some substring problems, we never need to backtrack
2. **Greedy is Optimal**: Always taking the earliest possible match works
3. **Preserves Relative Order**: Moving forward maintains sequence integrity

### Edge Cases Understanding

- **Empty s**: Always true, no characters need to match
- **Empty t with non-empty s**: Impossible, no characters available
- **Single character s**: Simple match/no-match decision
- **All matching characters**: Both pointers advance together

## Pattern Recognition

### When to Use Two-Pointers:
- Processing multiple sequences or arrays
- Need to compare/move through data structures sequentially
- Can solve with O(n) or O(n log n) time
- Preserve some ordering property

### Related Two-Pointer Problems:
- **Merge Sorted Array** (merge two arrays in-place)
- **Remove Duplicates** (from sorted array)
- **Remove Element** (remove all occurrences of value)
- **Move Zeroes** (move all zeros to end)
- **Reverse Vowels** (in-place string manipulation)

## Complexity Analysis Deep Dive

### Time Complexity: O(n + m)
- **Worst Case**: Every character in s requires scanning most/all of t
- **Best Case**: All characters found at beginning of t
- **Realistic Case**: Depends on how "spread out" the subsequence is

### Space Complexity: O(1)
- Only using constant extra space for pointers
- No additional data structures needed
- Makes it memory-efficient for large strings

## Alternative Solutions Analysis

### Your Original Solution (StringBuilder)
```java
// Analyze: s="abc", t="ahbgdc"
// i=0, builder="", s.char[0]='a'
//   while(i<6): 'a'=='a' → builder="a", i=1, break ✓
// i=1, s.char[1]='b'
//   while(i<6): 'h'!='b', i=2
//   while(i<6): 'b'=='b' → builder="ab", i=3, break ✓
// i=3, s.char[2]='c'
//   while(i<6): 'g'!='c', i=4
//   while(i<6): 'c'=='c' → builder="abc", i=5, break ✓

// Final: "abc".equals("abc") → true
```
**PROS**: Intuitive, builds result string
**CONS**: O(n) extra space, unnecessary string operations

### IndexOf Approach
```java
int start = 0;
for (char c : s.toCharArray()) {
    int found = t.indexOf(c, start);
    if (found == -1) return false;
    start = found + 1;
}
```
**PROS**: Very clean, easy to read
**CONS**: indexOf() is O(m) operation, makes overall O(n*m) worst case

### Two-Pointer Optimized (Best)
```java
int sPtr = 0, tPtr = 0;
while (sPtr < s.length() && tPtr < t.length()) {
    if (s.charAt(sPtr) == t.charAt(tPtr)) sPtr++;
    tPtr++;
}
return sPtr == s.length();
```
**PROS**: O(n+m) time, O(1) space, most efficient
**CONS**: Requires understanding of two-pointer pattern

## Follow-up Optimization Strategy

### For Multiple Queries (k ≥ 10⁹)

Instead of O(n+m) per query, we want to amortize preprocessing:

#### Approach 1: Position Index Map
```java
Map<Character, List<Integer>> charPositions = new HashMap<>();
for (int i = 0; i < t.length(); i++) {
    charPositions.computeIfAbsent(t.charAt(i), k -> new ArrayList<>()).add(i);
}
// Then use binary search for each character in s
```
- **Preprocessing**: O(m) time, O(m) space
- **Each Query**: O(n log m) time (binary search for each character)
- **Total for k queries**: O(m + k*n*log m)

#### Approach 2: Jump Table (like KMP)
```java
// Create jump table to skip when possible
int[] jump = new int[t.length() + 1];
// Similar to KMP failure function but for subsequence checking
```
- **Preprocessing**: O(m) time
- **Each Query**: Potentially faster than O(n+m)
- **Best for**: When t has repetitive structure

#### Which to Choose?
- Use **Position Map + Binary Search** for most cases
- Use **Jump Table** when t has lots of repetition
- Consider **Trie/Binary Search Tree** for very large alphabets

## Interview Tips

1. **Clarify the follow-up**: Ask about number of queries expected
2. **Start simple**: Explain basic solution first
3. **Optimize iteratively**: Show understanding of trade-offs
4. **Consider constraints**: Empty strings, edge cases
5. **Practice explanation**: Verbalize your thought process

## Common Implementation Errors

1. **Forgot to advance t pointer**: Loops forever
2. **Wrong return condition**: tPtr == t.length() vs sPtr == s.length()
3. **Missing empty string checks**: Edge cases fail
4. **Using expensive operations**: Multiple indexOf() calls
5. **Not handling character not found**: Infinite loop potential
