package leetcode.arrays.problems.MajorityElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 169: Majority Element
 * Difficulty: Easy
 *
 * Problem: Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than ⌊n / 2⌋ times.
 * You may assume that the majority element always exists in the array.
 *
 * Pattern: Frequency Counting, Boyer-Moore Majority Vote
 * Time Complexity: O(n) for HashMap and Boyer-Moore approaches
 * Space Complexity: O(n) for HashMap, O(1) for Boyer-Moore
 */
public class MajorityElement {

    /**
     * Approach 1: HashMap Frequency Counting (Most Intuitive)
     * 
     * This is the straightforward approach that most people think of first.
     * Count frequencies and return element with count > n/2.
     * 
     * @param nums input array
     * @return majority element
     */
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int n = nums.length;
        
        // Count frequencies
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
            
            // Early termination optimization: return as soon as we find majority
            if (frequencyMap.get(num) > n / 2) {
                return num;
            }
        }
        
        // This should never be reached given problem constraints
        return -1;
    }

    /**
     * Approach 2: Boyer-Moore Majority Vote Algorithm (Space Optimal)
     * 
     * Key Insight: If we pair up different elements and remove them,
     * the majority element will be the one left standing.
     * 
     * Mental Model: Think of it as a "voting" process where different
     * candidates cancel each other out, but the majority survives.
     * 
     * @param nums input array
     * @return majority element
     */
    public int majorityElementBoyerMoore(int[] nums) {
        int candidate = nums[0];
        int count = 1;
        
        // Phase 1: Find potential candidate
        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                candidate = nums[i];
                count = 1;
            } else if (nums[i] == candidate) {
                count++;
            } else {
                count--;
            }
        }
        
        // Phase 2: Verify candidate (not needed given problem constraints)
        // Since problem guarantees majority element exists, we can skip verification
        return candidate;
    }

    /**
     * Approach 3: Sorting (Simple but Less Efficient)
     * 
     * Key Insight: After sorting, the majority element will always be
     * at position n/2 (since it appears more than n/2 times).
     * 
     * @param nums input array (will be modified)
     * @return majority element
     */
    public int majorityElementSorting(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    /**
     * Approach 4: Bit Manipulation (Educational)
     * 
     * For each bit position, count how many numbers have that bit set.
     * If majority element has that bit set, then majority of numbers will have it set.
     * 
     * @param nums input array
     * @return majority element
     */
    public int majorityElementBitManipulation(int[] nums) {
        int majority = 0;
        int n = nums.length;
        
        // Check each bit position (32 bits for integer)
        for (int bit = 0; bit < 32; bit++) {
            int count = 0;
            
            // Count how many numbers have this bit set
            for (int num : nums) {
                if (((num >> bit) & 1) == 1) {
                    count++;
                }
            }
            
            // If majority have this bit set, set it in result
            if (count > n / 2) {
                majority |= (1 << bit);
            }
        }
        
        return majority;
    }

    /**
     * Approach 5: Randomization (Monte Carlo Method)
     * 
     * Keep picking random elements until we find the majority.
     * Expected number of attempts is 2 (since majority > n/2).
     * 
     * @param nums input array
     * @return majority element
     */
    public int majorityElementRandomized(int[] nums) {
        int n = nums.length;
        
        while (true) {
            // Pick random element
            int candidate = nums[(int) (Math.random() * n)];
            
            // Count its occurrences
            int count = 0;
            for (int num : nums) {
                if (num == candidate) {
                    count++;
                }
            }
            
            // Check if it's majority
            if (count > n / 2) {
                return candidate;
            }
        }
    }

    /**
     * Test method with comprehensive test cases
     */
    public static void main(String[] args) {
        MajorityElement solution = new MajorityElement();

        // Test Case 1: [3,2,3] -> 3
        int[] nums1 = {3, 2, 3};
        System.out.println("Test 1: " + Arrays.toString(nums1));
        System.out.println("HashMap: " + solution.majorityElement(nums1));
        System.out.println("Boyer-Moore: " + solution.majorityElementBoyerMoore(nums1));
        System.out.println("Expected: 3");
        System.out.println();

        // Test Case 2: [2,2,1,1,1,2,2] -> 2
        int[] nums2 = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("Test 2: " + Arrays.toString(nums2));
        System.out.println("HashMap: " + solution.majorityElement(nums2));
        System.out.println("Boyer-Moore: " + solution.majorityElementBoyerMoore(nums2));
        System.out.println("Expected: 2");
        System.out.println();

        // Test Case 3: Single element [5] -> 5
        int[] nums3 = {5};
        System.out.println("Test 3: " + Arrays.toString(nums3));
        System.out.println("HashMap: " + solution.majorityElement(nums3));
        System.out.println("Boyer-Moore: " + solution.majorityElementBoyerMoore(nums3));
        System.out.println("Expected: 5");
        System.out.println();

        // Test Case 4: All same elements [1,1,1,1] -> 1
        int[] nums4 = {1, 1, 1, 1};
        System.out.println("Test 4: " + Arrays.toString(nums4));
        System.out.println("HashMap: " + solution.majorityElement(nums4));
        System.out.println("Boyer-Moore: " + solution.majorityElementBoyerMoore(nums4));
        System.out.println("Expected: 1");
        System.out.println();

        // Test other approaches
        System.out.println("Testing other approaches:");
        int[] testArray = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("Sorting: " + solution.majorityElementSorting(testArray.clone()));
        System.out.println("Bit Manipulation: " + solution.majorityElementBitManipulation(testArray));
        System.out.println("Randomized: " + solution.majorityElementRandomized(testArray));
    }
}

/*
BOYER-MOORE ALGORITHM WALKTHROUGH:

Example: nums = [2, 2, 1, 1, 1, 2, 2]

Initial: candidate = 2, count = 1

i=1: nums[1] = 2, equals candidate, count = 2
i=2: nums[2] = 1, different from candidate, count = 1  
i=3: nums[3] = 1, different from candidate, count = 0
i=4: count = 0, so candidate = 1, count = 1
i=5: nums[5] = 2, different from candidate, count = 0
i=6: count = 0, so candidate = 2, count = 1

Final candidate: 2

WHY BOYER-MOORE WORKS:
1. The majority element appears > n/2 times
2. Even if we "cancel out" all non-majority elements with majority elements,
   there will still be majority elements left over
3. The algorithm essentially does this cancellation process
4. The surviving candidate must be the majority element

INTUITION:
Think of it like a battle between different armies (elements).
The largest army (majority element) will win because it has more soldiers
than all other armies combined.

HASHMAP APPROACH COMPARISON:
- HashMap: Direct counting, easy to understand
- Boyer-Moore: Clever elimination, space-optimal
- Both are O(n) time, but Boyer-Moore is O(1) space

WHEN TO USE WHICH:
- Interview: Start with HashMap for clarity, then mention Boyer-Moore for optimization
- Production: Boyer-Moore if space is critical, HashMap if readability matters
- Follow-up: Boyer-Moore shows algorithmic sophistication

RELATED PATTERNS:
- Frequency counting with HashMap
- Elimination algorithms
- Mathematical properties exploitation
- Space-time trade-offs
*/