package leetcode.linkedlist.problems.DeleteMiddleNodeLinkedList;

/**
 * LeetCode 2095: Delete the Middle Node of a Linked List
 *
 * Pattern: Slow/Fast pointers to identify middle.
 * Difficulty: Medium
 */
public class DeleteMiddleNodeLinkedList {

    /**
     * Single-pass solution using slow/fast pointers.
     * Time: O(n) | Space: O(1)
     */
    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // slow now points at the middle node; prev is node before slow.
        prev.next = slow.next;
        return head;
    }

    /**
     * Two-pass variant: count nodes first, then delete middle.
     * Serves as an educational alternative.
     */
    public ListNode deleteMiddleTwoPass(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        int length = 0;
        ListNode node = head;
        while (node != null) {
            length++;
            node = node.next;
        }

        int middleIndex = length / 2;
        node = head;
        ListNode prev = null;
        for (int i = 0; i < middleIndex; i++) {
            prev = node;
            node = node.next;
        }
        prev.next = node.next;
        return head;
    }

    /** Helper to build a list for quick manual testing. */
    public ListNode buildList(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int value : values) {
            tail.next = new ListNode(value);
            tail = tail.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        DeleteMiddleNodeLinkedList solver = new DeleteMiddleNodeLinkedList();

        ListNode example1 = solver.buildList(1, 3, 4, 7, 1, 2, 6);
        System.out.println("Example 1: " + solver.toString(solver.deleteMiddle(example1))); // [1,3,4,1,2,6]

        ListNode example2 = solver.buildList(1, 2, 3, 4);
        System.out.println("Example 2: " + solver.toString(solver.deleteMiddle(example2))); // [1,2,4]

        ListNode example3 = solver.buildList(1);
        System.out.println("Example 3: " + solver.deleteMiddle(example3)); // null
    }

    /** Utility to print list content. */
    private String toString(ListNode head) {
        if (head == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        ListNode node = head;
        while (node != null) {
            sb.append(node.val);
            node = node.next;
            if (node != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /** Standard singly-linked list node. */
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode() {}

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
