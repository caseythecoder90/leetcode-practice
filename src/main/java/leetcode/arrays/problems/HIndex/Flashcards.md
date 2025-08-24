# H-Index Mastery Flashcards

## 🃏 Problem Understanding Cards

### Card 1: H-Index Definition
**Q:** What is the H-Index definition?
**A:** The maximum value of h such that the researcher has published **at least h papers** that have each been cited **at least h times**.

### Card 2: Example Quick Check
**Q:** For citations `[3,0,6,1,5]`, what is the H-Index and why?
**A:** H-Index = 3. Because we have exactly 3 papers `[3,6,5]` with ≥3 citations each. Can't be 4 because we only have 2 papers with ≥4 citations.

### Card 3: Pattern Recognition
**Q:** What type of problem is H-Index? (NOT DP, Two Pointers, etc.)
**A:** **Sorting + Threshold Counting** pattern. We count items meeting a criteria, and sorting reveals the structure needed for efficient counting.

## 🔍 Algorithm Cards

### Card 4: Core Insight After Sorting
**Q:** After sorting citations, what does position `i` tell us?
**A:** At position `i`, we have exactly `n - i` papers with ≥ `citations[i]` citations (all papers from position i onwards).

### Card 5: The Key Condition
**Q:** What condition must be met at position `i` for a valid h-index?
**A:** `citations[i] ≥ (n - i)`. This means we have at least `n-i` papers with ≥`n-i` citations each.

### Card 6: Sorting Algorithm
**Q:** Write the sorting-based H-Index algorithm.
**A:**
```java
Arrays.sort(citations);
for (int i = 0; i < n; i++) {
    int papers = n - i;
    if (citations[i] >= papers) {
        return papers;
    }
}
return 0;
```

### Card 7: Why First Valid = Maximum
**Q:** Why do we return the first valid h-index we find (left to right)?
**A:** Because we scan from low to high citations. Later positions give smaller `n-i` values, so the first valid h-index is the maximum possible.

## 📊 Example Walkthrough Cards

### Card 8: Example Setup
**Q:** For `[3,0,6,1,5]`, what is the sorted array and what do we check?
**A:** 
```
Sorted: [0,1,3,5,6]
Check each position i: Is citations[i] ≥ (n-i)?
```

### Card 9: Example Trace
**Q:** Trace through `[0,1,3,5,6]` step by step.
**A:**
```
Position 0: 0 ≥ 5? ❌
Position 1: 1 ≥ 4? ❌  
Position 2: 3 ≥ 3? ✅ → H-Index = 3
```

### Card 10: Verification Check
**Q:** How do you verify H-Index = 3 for `[3,0,6,1,5]`?
**A:** Check that exactly 3 papers have ≥3 citations: `[3,6,5]` ✓, and remaining papers `[0,1]` have ≤3 citations ✓.

## 🚀 Optimization Cards

### Card 11: Bucket Sort Insight
**Q:** Why can we use bucket sort for H-Index optimization?
**A:** Because H-Index can never exceed `n` (total papers), so we only need buckets 0 to n. Citations higher than n can all go in bucket[n].

### Card 12: Bucket Sort Algorithm
**Q:** Write the bucket sort approach for H-Index.
**A:**
```java
int[] buckets = new int[n + 1];
for (int citation : citations) {
    buckets[Math.min(citation, n)]++;
}
int count = 0;
for (int i = n; i >= 0; i--) {
    count += buckets[i];
    if (count >= i) return i;
}
return 0;
```

### Card 13: Complexity Comparison
**Q:** Compare time/space complexity of all three approaches.
**A:**
- **Brute Force**: O(n²) time, O(1) space
- **Sorting**: O(n log n) time, O(1) space  
- **Bucket Sort**: O(n) time, O(n) space

## 🚨 Edge Cases & Pitfalls Cards

### Card 14: Common Mistake
**Q:** What's wrong with returning `i` instead of `n-i`?
**A:** `i` is the array position, `n-i` is the count of papers. We want to return the h-index value (count), not the position.

### Card 15: Edge Case - All Zeros
**Q:** What is the H-Index for `[0,0,0]`?
**A:** H-Index = 0. No papers have ≥1 citations, so we can't achieve any positive h-index.

### Card 16: Edge Case - Single High Paper
**Q:** What is the H-Index for `[100]`?
**A:** H-Index = 1 (not 100!). We have 1 paper with ≥1 citations, but we can't have 100 papers with ≥100 citations.

### Card 17: Boundary Condition
**Q:** What must you remember to include at the end of your algorithm?
**A:** `return 0;` - in case no valid h-index is found (all papers have 0 citations, etc.).

## 🎯 Interview Strategy Cards

### Card 18: Problem Approach Explanation  
**Q:** How do you explain your approach in an interview?
**A:** *"I'll sort the array to reveal structure. At each position i, I know exactly how many papers have at least citations[i] citations - it's n-i. So I need citations[i] ≥ n-i for a valid h-index."*

### Card 19: Optimization Discussion
**Q:** How do you transition to discussing optimization?
**A:** *"This sorting approach is O(n log n). If we need O(n), we could use bucket sort since h-index is bounded by the number of papers."*

### Card 20: Follow-up Questions
**Q:** What are common follow-up questions for H-Index?
**A:**
- "What if array is pre-sorted?" → Binary search (H-Index II)
- "Can you do O(n) time?" → Bucket sort approach
- "What if citations are very large?" → Bucket sort handles this naturally

## 💡 Insight Cards

### Card 21: Core Mental Model
**Q:** What's the best mental model for understanding H-Index?
**A:** **Achievement Ladder**: You're climbing rungs where rung h requires h papers with ≥h citations each. Find the highest rung you can reach.

### Card 22: Why Sorting Works
**Q:** In one sentence, why does sorting solve the H-Index problem?
**A:** Sorting transforms a complex counting problem into a simple position-checking problem by grouping papers by citation count.

### Card 23: Real-World Analogy
**Q:** Give a real-world analogy for H-Index.
**A:** Like a **restaurant rating system**: "Find the highest rating R where you have at least R dishes that each received at least R stars."

## 🔄 Pattern Recognition Cards

### Card 24: Similar Problem Pattern
**Q:** What other problems use the same "at least X items with at least X property" pattern?
**A:**
- Kth Largest Element
- Top K Frequent Elements  
- Meeting room scheduling problems
- Ranking and percentile problems

### Card 25: When to Use This Pattern
**Q:** What are the trigger words that suggest this pattern?
**A:**
- "at least X items with at least Y property"
- "maximum value such that condition holds"
- "threshold-based counting"
- "dual criteria optimization"

## 🎓 Mastery Check Cards

### Card 26: Quick Recognition Test
**Q:** You see a problem asking for "maximum k where at least k elements have property ≥ k". What's your immediate approach?
**A:** Sort the array, then check each position i where condition is `array[i] ≥ (n-i)`. Return first valid `n-i`.

### Card 27: Implementation Speed Test
**Q:** Can you write the core H-Index algorithm in under 10 lines?
**A:**
```java
public int hIndex(int[] citations) {
    Arrays.sort(citations);
    int n = citations.length;
    for (int i = 0; i < n; i++) {
        if (citations[i] >= n - i) {
            return n - i;
        }
    }
    return 0;
}
```

### Card 28: Complexity Analysis
**Q:** Without looking, what are the time/space complexities and why?
**A:** 
- **Time**: O(n log n) - dominated by sorting step
- **Space**: O(1) - only using constant extra variables
- **Optimization**: O(n) time with O(n) space using bucket sort

**Study Tip**: Review these cards daily until you can answer them all without hesitation!