package leetcode.linkedlist.problems.RotateList; /**
 * LeetCode 61: Rotate List
 * Difficulty: Medium
 *
 * Problem: Given the head of a linked list, rotate the list to the right by k places.
 *
 * This file contains:
 * 1. ListNode class definition
 * 2. Approach 1: Two-Pointer (Fast/Slow) Technique - YOUR SOLUTION ⭐
 * 3. Approach 2: Circular List + Break
 * 4. Approach 3: Brute Force (for comparison)
 * 5. Comprehensive testing and examples
 *
 * Key Insights:
 * - Always normalize k using k % n to handle k > n
 * - Check if k == 0 after modulo to avoid unnecessary work
 * - Two pointers with k-step gap naturally find the new tail
 * - Three-step reconnection: identify new head, connect tail to head, break cycle
 */

/**
 * Definition for singly-linked list node.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode curr = this;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(" -> ");
            }
            curr = curr.next;
        }
        sb.append(" -> null");
        return sb.toString();
    }
}

/**
 * Main solution class with multiple approaches for rotating a linked list.
 */
public class RotateList {

    // ==================== APPROACH 1: TWO-POINTER TECHNIQUE ==================== //
    // This is YOUR solution - the optimal approach! ⭐

    /**
     * Rotates the linked list to the right by k places using two-pointer technique.
     *
     * Algorithm:
     * 1. Count the length of the list
     * 2. Normalize k using modulo (k = k % n)
     * 3. Position 'last' pointer k steps ahead of 'curr'
     * 4. Move both pointers together until 'last' reaches the end
     * 5. Reconnect: newHead = curr.next, last.next = head, curr.next = null
     *
     * Time Complexity: O(n) - two passes through the list
     * Space Complexity: O(1) - only using pointer variables
     *
     * @param head The head of the linked list
     * @param k The number of positions to rotate right
     * @return The head of the rotated list
     */
    public ListNode rotateRightTwoPointer(ListNode head, int k) {
        // Edge case 1: Empty list or single node - no rotation needed
        if (head == null || head.next == null) {
            return head;
        }

        // Step 1: Count the length of the list
        int n = 0;
        ListNode curr = head;
        while (curr != null) {
            n++;
            curr = curr.next;
        }

        // Step 2: Normalize k (handle k > n)
        // After n rotations, we're back to the original list
        k = k % n;

        // Edge case 2: No rotation needed (k is a multiple of n)
        if (k == 0) {
            return head;
        }

        // Step 3: Position 'last' pointer k steps ahead
        // This creates a k-step gap between curr and last
        ListNode last = head;
        for (int i = 0; i < k; i++) {
            last = last.next;
        }

        // Step 4: Move both pointers together until last reaches the end
        // When last points to the last node, curr points to the new tail
        curr = head;
        while (last.next != null) {
            curr = curr.next;
            last = last.next;
        }

        // Step 5: Reconnect the pointers
        // curr is at position (n-k-1), which becomes the new tail
        // curr.next is at position (n-k), which becomes the new head
        ListNode newHead = curr.next;  // The node after new tail is new head
        last.next = head;              // Connect old tail to old head
        curr.next = null;              // Break the cycle at new tail

        return newHead;
    }


    // ==================== APPROACH 2: CIRCULAR LIST + BREAK ==================== //

    /**
     * Rotates the linked list using circular list technique.
     *
     * Algorithm:
     * 1. Count length and find the tail in one pass
     * 2. Connect tail to head (make circular)
     * 3. Calculate new tail position: n - k - 1
     * 4. Walk to new tail position
     * 5. Break the circle at new tail
     *
     * Pros:
     * - Slightly fewer total operations (~1.5 passes vs 2 passes)
     * - Conceptually elegant (make circle, break circle)
     *
     * Cons:
     * - Creates temporary cycle (might feel risky)
     * - Still O(n) time, so not fundamentally better
     *
     * Time Complexity: O(n) - about 1.5 passes through the list
     * Space Complexity: O(1) - only using pointer variables
     *
     * @param head The head of the linked list
     * @param k The number of positions to rotate right
     * @return The head of the rotated list
     */
    public ListNode rotateRightCircular(ListNode head, int k) {
        // Edge case: Empty list or single node
        if (head == null || head.next == null) {
            return head;
        }

        // Step 1: Find length and tail simultaneously
        int n = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            n++;
        }

        // Step 2: Normalize k
        k = k % n;
        if (k == 0) {
            return head;  // No rotation needed
        }

        // Step 3: Make the list circular
        tail.next = head;

        // Step 4: Find the new tail position
        // New tail is at position (n - k - 1)
        int stepsToNewTail = n - k - 1;
        ListNode newTail = head;
        for (int i = 0; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }

        // Step 5: Break the circle
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }


    // ==================== APPROACH 3: BRUTE FORCE (DON'T USE!) ==================== //

    /**
     * Rotates the list by performing k individual rotations.
     *
     * Algorithm:
     * 1. For each rotation (repeat k times):
     *    - Find the tail
     *    - Move tail to front
     *
     * WARNING: This is inefficient! Only included for educational purposes.
     *
     * Time Complexity: O(n * k) - could be O(n²) if k is close to n
     * Space Complexity: O(1)
     *
     * Why it's bad:
     * - Repeats work unnecessarily
     * - Doesn't use k % n optimization
     * - Much slower for large k
     *
     * @param head The head of the linked list
     * @param k The number of positions to rotate right
     * @return The head of the rotated list
     */
    public ListNode rotateRightBruteForce(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        // Should normalize k, but brute force doesn't even do this efficiently
        // Perform k individual rotations
        for (int i = 0; i < k; i++) {
            head = rotateByOne(head);
        }

        return head;
    }

    /**
     * Helper method: Rotate the list by exactly one position to the right.
     */
    private ListNode rotateByOne(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // Find the second-to-last node
        ListNode curr = head;
        while (curr.next.next != null) {
            curr = curr.next;
        }

        // Move last node to front
        ListNode last = curr.next;
        curr.next = null;
        last.next = head;

        return last;
    }


    // ==================== HELPER METHODS ==================== //

    /**
     * Creates a linked list from an array of values.
     */
    public static ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }

        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        for (int val : values) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }

        return dummy.next;
    }

    /**
     * Converts a linked list to array for easy comparison.
     */
    public static int[] listToArray(ListNode head) {
        // Count length
        int length = 0;
        ListNode curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }

        // Convert to array
        int[] result = new int[length];
        curr = head;
        int i = 0;
        while (curr != null) {
            result[i++] = curr.val;
            curr = curr.next;
        }

        return result;
    }

    /**
     * Prints a linked list in a readable format.
     */
    public static void printList(String label, ListNode head) {
        System.out.print(label + ": ");
        if (head == null) {
            System.out.println("null");
            return;
        }
        System.out.println(head.toString());
    }

    /**
     * Compares two lists for equality.
     */
    public static boolean listsEqual(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) {
                return false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == null && l2 == null;
    }


    // ==================== MAIN METHOD WITH EXAMPLES ==================== //

    public static void main(String[] args) {
        RotateList solution = new RotateList();

        System.out.println("=".repeat(70));
        System.out.println("LeetCode 61: Rotate List - Comprehensive Testing");
        System.out.println("=".repeat(70));

        // Test Case 1: Standard rotation
        System.out.println("\n--- Test Case 1: Standard Rotation ---");
        ListNode test1 = createList(new int[]{1, 2, 3, 4, 5});
        printList("Original", test1);
        ListNode result1 = solution.rotateRightTwoPointer(test1, 2);
        printList("After rotating right by 2", result1);
        System.out.println("Expected: 4 -> 5 -> 1 -> 2 -> 3 -> null");

        // Test Case 2: k > n
        System.out.println("\n--- Test Case 2: k > n (k=7, n=3) ---");
        ListNode test2 = createList(new int[]{0, 1, 2});
        printList("Original", test2);
        ListNode result2 = solution.rotateRightTwoPointer(test2, 4);
        printList("After rotating right by 4 (4%3=1)", result2);
        System.out.println("Expected: 2 -> 0 -> 1 -> null");

        // Test Case 3: k = 0
        System.out.println("\n--- Test Case 3: No Rotation (k=0) ---");
        ListNode test3 = createList(new int[]{1, 2, 3});
        printList("Original", test3);
        ListNode result3 = solution.rotateRightTwoPointer(test3, 0);
        printList("After rotating right by 0", result3);
        System.out.println("Expected: 1 -> 2 -> 3 -> null");

        // Test Case 4: k = n (full rotation)
        System.out.println("\n--- Test Case 4: Full Rotation (k=n) ---");
        ListNode test4 = createList(new int[]{1, 2, 3});
        printList("Original", test4);
        ListNode result4 = solution.rotateRightTwoPointer(test4, 3);
        printList("After rotating right by 3", result4);
        System.out.println("Expected: 1 -> 2 -> 3 -> null (back to original)");

        // Test Case 5: Single node
        System.out.println("\n--- Test Case 5: Single Node ---");
        ListNode test5 = createList(new int[]{1});
        printList("Original", test5);
        ListNode result5 = solution.rotateRightTwoPointer(test5, 99);
        printList("After rotating right by 99", result5);
        System.out.println("Expected: 1 -> null");

        // Test Case 6: Empty list
        System.out.println("\n--- Test Case 6: Empty List ---");
        ListNode test6 = null;
        printList("Original", test6);
        ListNode result6 = solution.rotateRightTwoPointer(test6, 5);
        printList("After rotating right by 5", result6);
        System.out.println("Expected: null");

        // Test Case 7: Large k
        System.out.println("\n--- Test Case 7: Very Large k ---");
        ListNode test7 = createList(new int[]{1, 2});
        printList("Original", test7);
        ListNode result7 = solution.rotateRightTwoPointer(test7, 1000000);
        printList("After rotating right by 1,000,000 (1000000%2=0)", result7);
        System.out.println("Expected: 1 -> 2 -> null (no change)");

        // Compare approaches
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Comparing All Three Approaches");
        System.out.println("=".repeat(70));

        int[] testData = {1, 2, 3, 4, 5};
        int k = 2;

        ListNode list1 = createList(testData);
        ListNode list2 = createList(testData);
        ListNode list3 = createList(testData);

        long start1 = System.nanoTime();
        ListNode result_approach1 = solution.rotateRightTwoPointer(list1, k);
        long time1 = System.nanoTime() - start1;

        long start2 = System.nanoTime();
        ListNode result_approach2 = solution.rotateRightCircular(list2, k);
        long time2 = System.nanoTime() - start2;

        long start3 = System.nanoTime();
        ListNode result_approach3 = solution.rotateRightBruteForce(list3, k);
        long time3 = System.nanoTime() - start3;

        System.out.println("\nInput: [1,2,3,4,5], k=2\n");

        printList("Approach 1 (Two-Pointer)", result_approach1);
        System.out.println("Time: " + time1 + " ns");

        printList("Approach 2 (Circular)", result_approach2);
        System.out.println("Time: " + time2 + " ns");

        printList("Approach 3 (Brute Force)", result_approach3);
        System.out.println("Time: " + time3 + " ns");

        boolean isEqual = listsEqual(result_approach1, result_approach2) && listsEqual(result_approach2, result_approach3);

        System.out.println("\nAll approaches produce same result: " + isEqual);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("Recommendation: Use Approach 1 (Two-Pointer) - Most intuitive ⭐");
        System.out.println("Alternative: Approach 2 (Circular) - Slightly fewer operations");
        System.out.println("Avoid: Approach 3 (Brute Force) - O(n*k) complexity is too slow!");
        System.out.println("=".repeat(70));
    }
}

/*
 * DETAILED WALKTHROUGH
 * ====================
 *
 * Example: head = [1,2,3,4,5], k = 2
 *
 * Step 1: Count length
 * ---------------------
 * Traverse: 1 → 2 → 3 → 4 → 5 → null
 * Result: n = 5
 *
 * Step 2: Normalize k
 * -------------------
 * k = 2 % 5 = 2 (no change)
 * k != 0, so continue
 *
 * Step 3: Position last pointer k=2 steps ahead
 * ----------------------------------------------
 * Initial:  1 → 2 → 3 → 4 → 5 → null
 *           ↑
 *          last
 *
 * After i=0: 1 → 2 → 3 → 4 → 5 → null
 *                ↑
 *               last
 *
 * After i=1: 1 → 2 → 3 → 4 → 5 → null
 *                    ↑
 *                   last
 *
 * Step 4: Move both pointers together
 * -----------------------------------
 * Initial:  1 → 2 → 3 → 4 → 5 → null
 *           ↑       ↑
 *         curr    last
 *
 * Iteration 1 (last.next = 4, not null):
 *           1 → 2 → 3 → 4 → 5 → null
 *               ↑       ↑
 *             curr    last
 *
 * Iteration 2 (last.next = 5, not null):
 *           1 → 2 → 3 → 4 → 5 → null
 *                   ↑       ↑
 *                 curr    last
 *
 * Stop (last.next = null)
 *
 * Step 5: Reconnect
 * -----------------
 * newHead = curr.next = node 4
 *
 * last.next = head:
 *           5 → 1 (connect old tail to old head)
 *
 * curr.next = null:
 *           3 → null (break at new tail)
 *
 * Final Result: 4 → 5 → 1 → 2 → 3 → null
 *
 *
 * EDGE CASES HANDLED
 * ==================
 *
 * 1. Empty list: head = null, k = 5
 *    - Returns null immediately
 *
 * 2. Single node: head = [1], k = 100
 *    - Returns [1] immediately (no rotation possible)
 *
 * 3. k = 0: head = [1,2,3], k = 0
 *    - Returns original list (no rotation)
 *
 * 4. k = n: head = [1,2,3], k = 3
 *    - k % n = 0, returns original list (full rotation)
 *
 * 5. k > n: head = [1,2,3], k = 10
 *    - k % n = 10 % 3 = 1, rotates by 1
 *    - Result: [3,1,2]
 *
 * 6. k = 1: head = [1,2,3,4,5], k = 1
 *    - Simplest rotation case
 *    - Result: [5,1,2,3,4]
 *
 *
 * COMPLEXITY ANALYSIS
 * ===================
 *
 * Time Complexity: O(n)
 * - Count length: O(n)
 * - Position last pointer: O(k) ≤ O(n) after k % n
 * - Move both pointers: O(n-k) ≤ O(n)
 * - Reconnect: O(1)
 * - Total: O(n) + O(n) + O(n) + O(1) = O(n)
 *
 * Space Complexity: O(1)
 * - Only using a constant number of pointer variables (curr, last, newHead)
 * - No additional data structures
 * - No recursion (no stack space)
 *
 *
 * ALTERNATIVE APPROACHES
 * ======================
 *
 * Approach 2: Circular List + Break
 * ----------------------------------
 * 1. Make the list circular by connecting tail to head
 * 2. Find the new tail position: n - k - 1
 * 3. Break the circle at the new tail
 *
 * Pros: Slightly cleaner conceptually
 * Cons: Same O(n) complexity, creates temporary cycle
 *
 *
 * COMMON MISTAKES TO AVOID
 * ========================
 *
 * 1. Forgetting k % n:
 *    ❌ for (int i = 0; i < k; i++) // k could be > n!
 *    ✅ k = k % n; then iterate
 *
 * 2. Wrong loop condition:
 *    ❌ while (last != null) // goes too far
 *    ✅ while (last.next != null) // stops at last node
 *
 * 3. Forgetting to break cycle:
 *    ❌ Missing curr.next = null // creates infinite loop
 *    ✅ Always set curr.next = null
 *
 * 4. Off-by-one in positioning:
 *    ❌ for (int i = 0; i <= k; i++) // k+1 steps
 *    ✅ for (int i = 0; i < k; i++) // k steps
 *
 * 5. Not checking k == 0:
 *    ❌ Doing unnecessary work when k % n == 0
 *    ✅ if (k == 0) return head;
 *
 *
 * PATTERN RECOGNITION
 * ===================
 *
 * This problem uses the "Two-Pointer with Fixed Gap" pattern.
 *
 * Related problems using the same pattern:
 * - Remove Nth Node From End (LC 19)
 * - Middle of Linked List (LC 876) - uses fast/slow with 2x speed
 * - Reorder List (LC 143) - uses similar pointer manipulation
 * - Linked List Cycle II (LC 142) - Floyd's cycle detection
 *
 * Key insight: When you need to find a position relative to the end
 * of a linked list, consider using two pointers with a controlled gap.
 */