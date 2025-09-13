# Sliding Window Problems - Curated List

## Problem Categories

### 🟢 Easy (Fundamentals)

#### 1. Maximum Sum Subarray of Size K
- **Pattern:** Fixed Window
- **Key Concept:** Basic sliding window
- **Learning:** How to slide and update efficiently

#### 2. Average of Subarrays of Size K (LC 643)
- **Pattern:** Fixed Window
- **Key Concept:** Running average
- **Learning:** Maintain window state

#### 3. Maximum Number of Vowels in Substring (LC 1456)
- **Pattern:** Fixed Window
- **Key Concept:** Character counting
- **Learning:** Track specific elements

#### 4. Contains Duplicate II (LC 219)
- **Pattern:** Fixed Window with Set
- **Key Concept:** Window with k distance
- **Learning:** Using HashSet in window

---

### 🟡 Medium (Core Problems)

#### 5. Longest Substring Without Repeating Characters (LC 3) ⭐
- **Pattern:** Variable Window + HashSet
- **Key Concept:** Expand and contract
- **Learning:** Classic variable window problem

#### 6. Longest Substring with At Most Two Distinct Characters (LC 159)
- **Pattern:** Variable Window + HashMap
- **Key Concept:** Character frequency
- **Learning:** At most k distinct pattern

#### 7. Longest Substring with At Most K Distinct Characters (LC 340)
- **Pattern:** Variable Window + HashMap
- **Key Concept:** Generalized k distinct
- **Learning:** HashMap for frequency tracking

#### 8. Fruit Into Baskets (LC 904)
- **Pattern:** At Most 2 Distinct
- **Key Concept:** Maximum window with constraint
- **Learning:** Real-world problem mapping

#### 9. Max Consecutive Ones III (LC 1004) ⭐
- **Pattern:** Variable Window with Counter
- **Key Concept:** Flip at most k zeros
- **Learning:** Window with modifications

#### 10. Longest Repeating Character Replacement (LC 424)
- **Pattern:** Variable Window + Frequency
- **Key Concept:** Replace k characters
- **Learning:** Complex validity condition

#### 11. Permutation in String (LC 567) ⭐
- **Pattern:** Fixed Window + HashMap
- **Key Concept:** Anagram detection
- **Learning:** Pattern matching

#### 12. Find All Anagrams in String (LC 438)
- **Pattern:** Fixed Window + HashMap
- **Key Concept:** Multiple pattern matches
- **Learning:** Sliding pattern matching

#### 13. Minimum Size Subarray Sum (LC 209) ⭐
- **Pattern:** Variable Shrinkable Window
- **Key Concept:** Minimum window with sum ≥ target
- **Learning:** Shrinking for optimization

#### 14. Subarray Product Less Than K (LC 713)
- **Pattern:** Variable Window + Count
- **Key Concept:** Count valid subarrays
- **Learning:** Counting windows

#### 15. Number of Substrings Containing All Three Characters (LC 1358)
- **Pattern:** Variable Window + Counter
- **Key Concept:** Contains all required
- **Learning:** Multiple requirements

---

### 🔴 Hard (Advanced)

#### 16. Minimum Window Substring (LC 76) ⭐⭐
- **Pattern:** Variable Window + Two HashMaps
- **Key Concept:** Complex pattern matching
- **Learning:** Ultimate string matching problem

#### 17. Sliding Window Maximum (LC 239) ⭐⭐
- **Pattern:** Fixed Window + Deque
- **Key Concept:** Monotonic deque
- **Learning:** Advanced data structure usage

#### 18. Substring with Concatenation of All Words (LC 30)
- **Pattern:** Multiple Fixed Windows
- **Key Concept:** Word-level matching
- **Learning:** Complex sliding window

#### 19. Longest Substring with At Least K Repeating Characters (LC 395)
- **Pattern:** Divide and Conquer + Sliding Window
- **Key Concept:** Multiple passes
- **Learning:** Combining techniques

#### 20. Sliding Window Median (LC 480)
- **Pattern:** Fixed Window + Two Heaps
- **Key Concept:** Dynamic median
- **Learning:** Complex state management

#### 21. Count of Unique Numbers in Subarrays (LC 1852)
- **Pattern:** Fixed Window + HashMap
- **Key Concept:** Distinct counting
- **Learning:** Efficiency with maps

---

## Study Path Recommendations

### 🎯 Beginner Path (Master the Basics)
1. Maximum Sum Subarray of Size K
2. Contains Duplicate II
3. Longest Substring Without Repeating Characters
4. Minimum Size Subarray Sum

### 🚀 Intermediate Path (Build Proficiency)
1. Longest Substring Without Repeating Characters
2. Max Consecutive Ones III
3. Permutation in String
4. Find All Anagrams in String
5. Fruit Into Baskets

### 💪 Advanced Path (Interview Ready)
1. Longest Repeating Character Replacement
2. Minimum Window Substring
3. Sliding Window Maximum
4. Substring with Concatenation of All Words

### 🏆 Pattern Mastery Order

#### Phase 1: Fixed Window
- Maximum Sum Subarray of Size K
- Average of Subarrays
- Maximum Vowels in Substring

#### Phase 2: Variable Window Basics
- Longest Substring Without Repeating
- Minimum Size Subarray Sum
- Max Consecutive Ones III

#### Phase 3: HashMap Patterns
- Longest K Distinct Characters
- Permutation in String
- Find All Anagrams

#### Phase 4: Complex Windows
- Longest Repeating Character Replacement
- Minimum Window Substring
- Sliding Window Maximum

---

## Problem Patterns Quick Reference

### By Data Structure Used

**HashSet Problems:**
- Longest Substring Without Repeating Characters
- Contains Duplicate II

**HashMap Problems:**
- Longest K Distinct Characters
- Fruit Into Baskets
- Minimum Window Substring
- Find All Anagrams

**Deque Problems:**
- Sliding Window Maximum
- Sliding Window Minimum

**Simple Counter:**
- Max Consecutive Ones III
- Maximum Vowels in Substring

### By Window Type

**Fixed Size:**
- Maximum Sum Subarray of Size K
- Find All Anagrams
- Sliding Window Maximum
- Average of Subarrays

**Variable Expanding:**
- Longest Substring Without Repeating
- Longest K Distinct Characters
- Max Consecutive Ones III

**Variable Shrinking:**
- Minimum Size Subarray Sum
- Minimum Window Substring
- Smallest Subarray with Sum Greater Than K

### By Complexity

**O(n) Time, O(1) Space:**
- Maximum Sum Subarray
- Minimum Size Subarray Sum
- Max Consecutive Ones III

**O(n) Time, O(k) Space:**
- Longest K Distinct Characters
- Sliding Window Maximum
- Find All Anagrams

**O(n) Time, O(n) Space:**
- Minimum Window Substring
- Substring with Concatenation

---

## Tips for Each Difficulty Level

### 🟢 Easy Problems
- Focus on understanding basic window sliding
- Practice calculating window size
- Master the add-new-remove-old pattern
- Don't overthink - these have straightforward solutions

### 🟡 Medium Problems
- Pay attention to when to expand vs shrink
- HashMap/HashSet usage is common
- Practice the "at most k" pattern
- Variable window problems dominate this level

### 🔴 Hard Problems
- Often combine sliding window with other techniques
- May require preprocessing or postprocessing
- Complex state management (multiple maps, deques)
- Edge cases become critical

---

## Common Mistakes by Problem Type

### String Problems
- Forgetting character case sensitivity
- Not handling empty strings
- Off-by-one in substring extraction

### Sum Problems
- Not handling negative numbers
- Integer overflow in products
- Wrong initialization (0 vs Integer.MAX_VALUE)

### Pattern Matching
- Incorrect frequency comparison
- Not updating match count properly
- Forgetting to restore state when shrinking

### K-Constraint Problems
- Confusion between "exactly k" vs "at most k"
- Wrong shrinking condition
- Not maintaining k properly when sliding

---

## Interview Frequency (Based on LeetCode Premium Data)

### Top 5 Most Asked
1. 🔥 Longest Substring Without Repeating Characters
2. 🔥 Minimum Window Substring
3. 🔥 Sliding Window Maximum
4. 🔥 Find All Anagrams in String
5. 🔥 Longest Repeating Character Replacement

### Rising in Popularity
- Max Consecutive Ones III
- Fruit Into Baskets
- Minimum Size Subarray Sum

### Company-Specific Favorites
- **Google:** Longest Substring with At Most K Distinct
- **Facebook:** Minimum Window Substring
- **Amazon:** Sliding Window Maximum
- **Microsoft:** Longest Substring Without Repeating

---

## Time Investment Guide

### Quick Wins (30 min each)
- Maximum Sum Subarray of Size K
- Contains Duplicate II
- Maximum Vowels in Substring

### Standard Practice (45-60 min each)
- Longest Substring Without Repeating
- Find All Anagrams
- Minimum Size Subarray Sum

### Deep Dives (60-90 min each)
- Minimum Window Substring
- Sliding Window Maximum
- Longest Repeating Character Replacement

---

## Notes

⭐ = Must-do problem for interviews
⭐⭐ = Frequently asked in top tech companies

Problems are ordered within each difficulty by recommended solving order, not by problem number.