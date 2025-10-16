package leetcode.linkedlist.problems.RemoveDuplicatesFromSortedListII;

/**
 * LeetCode 82: Remove Duplicates from Sorted List II
 *
 * Problem: Given the head of a sorted linked list, delete all nodes that have
 * duplicate numbers, leaving only distinct numbers from the original list.
 *
 * Key Insight: We need to remove ALL occurrences of duplicated values, not just
 * the duplicates themselves. This requires tracking whether we found duplicates.
 *
 * Pattern: Two-pointer technique with dummy node for handling head deletion
 *
 * Time Complexity: O(n) - single pass through the list
 * Space Complexity: O(1) - only using pointers
 */
public class RemoveDuplicatesFromSortedListII {
    /**
     * ListNode class definition
     */
    public static class ListNode {
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
    }

    /**
     * CORRECT SOLUTION - Two-pointer approach with duplicate detection
     */
    public ListNode deleteDuplicates(ListNode head) {
        // Handle empty list or single node
        if (head == null || head.next == null) {
            return head;
        }

        // Dummy node to handle potential deletion of head
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // prev: last node before the duplicates
        // curr: current node being examined
        ListNode prev = dummy;
        ListNode curr = head;

        while (curr != null) {
            // Check if current node has duplicates ahead
            if (curr.next != null && curr.val == curr.next.val) {
                // Found duplicates - need to remove ALL nodes with this value
                int duplicateValue = curr.val;

                // Skip all nodes with the duplicate value
                while (curr != null && curr.val == duplicateValue) {
                    curr = curr.next;
                }

                // Connect prev to the first different node (or null)
                prev.next = curr;
            } else {
                // No duplicates for current node, move prev pointer
                prev = curr;
                curr = curr.next;
            }
        }

        return dummy.next;
    }

    /**
     * ALTERNATIVE SOLUTION - Using look-ahead to check for duplicates
     * This approach is slightly different but equally valid
     */
    public ListNode deleteDuplicatesAlternative(ListNode head) {
        if (head == null) return null;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        while (head != null) {
            // Look ahead to see if current value has duplicates
            if (head.next != null && head.val == head.next.val) {
                // Skip all nodes with same value
                int val = head.val;
                while (head != null && head.val == val) {
                    head = head.next;
                }
                prev.next = head;
            } else {
                // No duplicates, keep this node
                prev = head;
                head = head.next;
            }
        }

        return dummy.next;
    }

    /**
     * YOUR ORIGINAL SOLUTION - ANNOTATED WITH ISSUES
     */
    public ListNode yourSolution(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode curr = head;
        ListNode prev = dummy;

        while (curr != null && curr.next != null) {
            ListNode next = curr.next;
            int value = curr.val;

            // ISSUE 1: Checking next.val before next != null check
            // Should be: while (next != null && value == next.val)
            while (value == next.val && next != null) { // ❌ Wrong order
                // ISSUE 2: This logic doesn't properly remove duplicates
                prev.next = next;  // ❌ This keeps one duplicate
                curr.next = null;  // ❌ Unnecessary
                next = next.next;
            }
            // ISSUE 3: Not handling the case where we found duplicates
            // We should skip ALL nodes with the duplicate value
            prev = curr;
            curr = next;
        }

        return dummy.next;
    }

    /**
     * STEP-BY-STEP TRACE for understanding
     */
    public ListNode deleteDuplicatesWithTrace(ListNode head) {
        System.out.println("Starting list: " + listToString(head));

        if (head == null || head.next == null) {
            System.out.println("List has 0 or 1 nodes, returning as-is");
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode curr = head;

        System.out.println("Initial setup: dummy -> " + listToString(head));

        while (curr != null) {
            System.out.println("\nExamining node with value: " + curr.val);

            if (curr.next != null && curr.val == curr.next.val) {
                System.out.println("  Found duplicate! Value " + curr.val + " appears multiple times");
                int duplicateValue = curr.val;

                // Count how many duplicates
                int count = 0;
                ListNode temp = curr;
                while (temp != null && temp.val == duplicateValue) {
                    count++;
                    temp = temp.next;
                }
                System.out.println("  Total occurrences: " + count);

                // Skip all duplicates
                while (curr != null && curr.val == duplicateValue) {
                    curr = curr.next;
                }

                prev.next = curr;
                System.out.println("  After removal: " + listToString(dummy.next));
            } else {
                System.out.println("  No duplicates, keeping this node");
                prev = curr;
                curr = curr.next;
            }
        }

        System.out.println("\nFinal result: " + listToString(dummy.next));
        return dummy.next;
    }

    // Helper method to print list
    private String listToString(ListNode head) {
        if (head == null) return "null";
        StringBuilder sb = new StringBuilder();
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) sb.append(" -> ");
            curr = curr.next;
        }
        return sb.toString();
    }
}

