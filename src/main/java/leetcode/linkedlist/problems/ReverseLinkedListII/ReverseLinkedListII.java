package leetcode.linkedlist.problems.ReverseLinkedListII;

/**
 * LeetCode 92 - Reverse Linked List II
 *
 * Difficulty: Medium
 * Pattern: Linked List - Partial In-Place Reversal
 *
 * Problem: Reverse nodes of a linked list from position left to right.
 *
 * Approach: Use dummy node + standard reversal technique
 * Time Complexity: O(n) - visit each node at most once
 * Space Complexity: O(1) - constant extra space
 *
 * Key Insight: Use dummy node to handle edge case of reversing from head (left = 1)
 */
public class ReverseLinkedListII {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * Main solution: Reverse sublist from position left to right
     *
     * Algorithm:
     * 1. Create dummy node to handle left = 1 edge case
     * 2. Navigate to node BEFORE left position
     * 3. Save starting node (becomes tail after reversal)
     * 4. Reverse the sublist using standard reversal
     * 5. Reconnect both sides of the reversed section
     *
     * @param head The head of the linked list
     * @param left Starting position to reverse (1-indexed)
     * @param right Ending position to reverse (1-indexed)
     * @return The head of the modified list
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // Edge case: no reversal needed
        if (head == null || head.next == null || left == right) {
            return head;
        }

        // Step 1: Create dummy node to simplify edge case handling
        // This allows us to always have a node before the reversal section
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // Step 2: Navigate to the node BEFORE position left
        // We need this to reconnect after reversal
        ListNode beforeReverse = dummy;
        for (int i = 1; i < left; i++) {
            beforeReverse = beforeReverse.next;
        }

        // Step 3: Save connection points
        // startReverse will become the tail of the reversed section
        ListNode startReverse = beforeReverse.next;

        // Step 4: Reverse exactly (right - left + 1) nodes
        // Use standard linked list reversal algorithm
        ListNode prev = null;
        ListNode curr = startReverse;

        for (int i = 0; i <= right - left; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // After reversal:
        // - prev points to the new head of the reversed section
        // - curr points to the first node after the reversed section
        // - startReverse points to what is now the tail

        // Step 5: Reconnect the list
        beforeReverse.next = prev;        // Connect to new head of reversed section
        startReverse.next = curr;         // Connect tail of reversed section to rest

        return dummy.next;  // Skip the dummy node
    }

    /**
     * Alternative solution: Without dummy node (more complex)
     * Only included for educational purposes - dummy node approach is cleaner
     */
    public ListNode reverseBetweenNoDummy(ListNode head, int left, int right) {
        if (head == null || head.next == null || left == right) {
            return head;
        }

        // Special case: reversing from head
        if (left == 1) {
            ListNode prev = null;
            ListNode curr = head;
            ListNode tail = head;  // Will become new tail

            for (int i = 0; i <= right - left; i++) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            tail.next = curr;
            return prev;
        }

        // Normal case: find node before reversal section
        ListNode beforeReverse = head;
        for (int i = 1; i < left - 1; i++) {
            beforeReverse = beforeReverse.next;
        }

        ListNode startReverse = beforeReverse.next;
        ListNode prev = null;
        ListNode curr = startReverse;

        for (int i = 0; i <= right - left; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        beforeReverse.next = prev;
        startReverse.next = curr;

        return head;
    }

    // ==================== Helper Methods ====================

    /**
     * Create a linked list from an array of values
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
     * Convert linked list to string for easy visualization
     */
    public static String listToString(ListNode head) {
        if (head == null) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        ListNode curr = head;

        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) {
                sb.append(", ");
            }
            curr = curr.next;
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * Run a test case and print results
     */
    public static void runTest(int[] input, int left, int right, int[] expected) {
        ReverseLinkedListII solution = new ReverseLinkedListII();

        ListNode head = createList(input);
        System.out.println("Input:    " + listToString(head) +
                ", left=" + left + ", right=" + right);

        ListNode result = solution.reverseBetween(head, left, right);
        System.out.println("Output:   " + listToString(result));
        System.out.println("Expected: " + listToString(createList(expected)));
        System.out.println();
    }

    // ==================== Main Test Cases ====================

    public static void main(String[] args) {
        System.out.println("=== LeetCode 92: Reverse Linked List II ===\n");

        // Test Case 1: Example from problem (middle section)
        System.out.println("Test 1: Reverse middle section");
        runTest(
                new int[]{1, 2, 3, 4, 5},
                2, 4,
                new int[]{1, 4, 3, 2, 5}
        );

        // Test Case 2: Single node reversal (no change)
        System.out.println("Test 2: Single node (left = right)");
        runTest(
                new int[]{5},
                1, 1,
                new int[]{5}
        );

        // Test Case 3: Reverse from head
        System.out.println("Test 3: Reverse from head (left = 1)");
        runTest(
                new int[]{1, 2, 3, 4, 5},
                1, 3,
                new int[]{3, 2, 1, 4, 5}
        );

        // Test Case 4: Reverse to tail
        System.out.println("Test 4: Reverse to tail (right = n)");
        runTest(
                new int[]{1, 2, 3, 4, 5},
                3, 5,
                new int[]{1, 2, 5, 4, 3}
        );

        // Test Case 5: Reverse entire list
        System.out.println("Test 5: Reverse entire list");
        runTest(
                new int[]{1, 2, 3, 4, 5},
                1, 5,
                new int[]{5, 4, 3, 2, 1}
        );

        // Test Case 6: Two nodes
        System.out.println("Test 6: Two nodes");
        runTest(
                new int[]{1, 2},
                1, 2,
                new int[]{2, 1}
        );

        // Test Case 7: Three nodes, reverse last two
        System.out.println("Test 7: Three nodes, reverse last two");
        runTest(
                new int[]{1, 2, 3},
                2, 3,
                new int[]{1, 3, 2}
        );

        // Test Case 8: Longer list
        System.out.println("Test 8: Longer list");
        runTest(
                new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                3, 7,
                new int[]{1, 2, 7, 6, 5, 4, 3, 8, 9, 10}
        );

        System.out.println("=== All Tests Complete ===");

        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] largeArray = new int[500];
        for (int i = 0; i < 500; i++) {
            largeArray[i] = i + 1;
        }

        ReverseLinkedListII solution = new ReverseLinkedListII();
        ListNode largeList = createList(largeArray);

        long startTime = System.nanoTime();
        solution.reverseBetween(largeList, 100, 400);
        long endTime = System.nanoTime();

        System.out.println("Reversed 301 nodes in list of 500: " +
                (endTime - startTime) / 1000 + " microseconds");
    }
}

/*
===========================================
         DETAILED ALGORITHM TRACE
===========================================

Example: head = [1,2,3,4,5], left = 2, right = 4

STEP-BY-STEP EXECUTION:

Initial State:
  1 → 2 → 3 → 4 → 5 → null

Step 1: Create dummy
  dummy → 1 → 2 → 3 → 4 → 5 → null

Step 2: Navigate to position before left (left - 1 = 1)
  Loop: i = 1, move beforeReverse

  dummy → 1 → 2 → 3 → 4 → 5 → null
          ↑
     beforeReverse

Step 3: Save starting point
  startReverse = beforeReverse.next = node(2)

  dummy → 1 → 2 → 3 → 4 → 5 → null
          ↑   ↑
    beforeReverse
           startReverse

Step 4: Reverse sublist (3 nodes: 2, 3, 4)

  Iteration 0 (i = 0):
    prev = null, curr = 2
    next = 3
    2.next = null
    prev = 2, curr = 3

  Iteration 1 (i = 1):
    prev = 2, curr = 3
    next = 4
    3.next = 2
    prev = 3, curr = 4

  Iteration 2 (i = 2):
    prev = 3, curr = 4
    next = 5
    4.next = 3
    prev = 4, curr = 5

  After loop:
    prev = 4 (new head of reversed section)
    curr = 5 (first node after reversed section)

  Reversed section looks like: 4 → 3 → 2 → null
  Original remaining: 5 → null

Step 5: Reconnect
  beforeReverse.next = prev     // 1 → 4
  startReverse.next = curr      // 2 → 5

  Final state:
  dummy → 1 → 4 → 3 → 2 → 5 → null

  Return dummy.next = node(1)

Result: [1, 4, 3, 2, 5] ✓

===========================================
           KEY INSIGHTS
===========================================

1. DUMMY NODE ADVANTAGE:
   - Eliminates special case for left = 1
   - Provides consistent "node before reversal" logic
   - Simplifies reconnection

2. ITERATION COUNT:
   - To reverse from position `left` to `right`
   - Need exactly (right - left + 1) iterations
   - Example: positions 2-4 = 3 iterations

3. CONNECTION POINTS:
   - beforeReverse.next → new head (prev after reversal)
   - startReverse.next → remaining list (curr after reversal)
   - Both connections are REQUIRED

4. POINTER TRACKING:
   - startReverse becomes tail after reversal
   - prev becomes new head after reversal
   - curr points to first node after reversed section

===========================================
         COMMON PITFALLS
===========================================

❌ Using left as loop counter:
   for (int i = left; left <= right; left++)

✅ Use separate counter:
   for (int i = 0; i <= right - left; i++)

❌ Forgetting second reconnection:
   beforeReverse.next = prev; // Only this

✅ Both reconnections:
   beforeReverse.next = prev;
   startReverse.next = curr;

❌ Wrong iteration count:
   for (int i = left; i < right; i++)

✅ Correct count:
   for (int i = 0; i <= right - left; i++)
*/