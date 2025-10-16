package leetcode.linkedlist.problems.ReverseLinkedList;

/**
 * LeetCode 206: Reverse Linked List
 *
 * Pattern: Linked List Reversal
 * Difficulty: Easy
 *
 * Problem: Reverse a singly linked list and return the new head.
 *
 * This class includes multiple approaches (iterative, recursive, stack-based)
 * along with simple helpers to build and inspect lists for practice.
 */
public class ReverseLinkedList {

    /**
     * Iterative pointer-reversal solution.
     * Time: O(n) | Space: O(1)
     */
    public ListNode reverseListIterative(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode next = current.next; // Preserve remainder of list
            current.next = prev;          // Reverse pointer
            prev = current;               // Advance prev
            current = next;               // Move to next node
        }

        return prev;
    }

    /**
     * Recursive solution.
     * Time: O(n) | Space: O(n) for call stack.
     */
    public ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode reversedHead = reverseListRecursive(head.next);
        head.next.next = head; // Append current node to tail of reversed sublist
        head.next = null;      // Terminate current node
        return reversedHead;
    }

    /**
     * Tail-recursive helper to expose alternative style.
     * Still uses O(n) call stack depth due to recursion.
     */
    public ListNode reverseListTailRecursive(ListNode head) {
        return reverseTailHelper(head, null);
    }

    private ListNode reverseTailHelper(ListNode current, ListNode prev) {
        if (current == null) {
            return prev;
        }

        ListNode next = current.next;
        current.next = prev;
        return reverseTailHelper(next, current);
    }

    /**
     * Stack-based approach (educational, not optimal due to O(n) extra space).
     */
    public ListNode reverseListWithStack(ListNode head) {
        if (head == null) {
            return null;
        }

        java.util.Deque<ListNode> stack = new java.util.ArrayDeque<>();
        ListNode node = head;
        while (node != null) {
            stack.push(node);
            node = node.next;
        }

        ListNode newHead = stack.pop();
        ListNode current = newHead;

        while (!stack.isEmpty()) {
            current.next = stack.pop();
            current = current.next;
        }

        current.next = null;
        return newHead;
    }

    /**
     * Helper to create a linked list from an array of integers.
     */
    public ListNode buildList(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int value : values) {
            tail.next = new ListNode(value);
            tail = tail.next;
        }
        return dummy.next;
    }

    /**
     * Helper to convert a list into a string representation.
     */
    public String toString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

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

    /**
     * Basic test harness for quick manual verification.
     */
    public static void main(String[] args) {
        ReverseLinkedList solution = new ReverseLinkedList();

        ListNode list1 = solution.buildList(1, 2, 3, 4, 5);
        System.out.println("Original:  " + solution.toString(list1));
        System.out.println("Iterative: " + solution.toString(solution.reverseListIterative(list1)));

        ListNode list2 = solution.buildList(1, 2);
        System.out.println("Recursive: " + solution.toString(solution.reverseListRecursive(list2)));

        ListNode list3 = solution.buildList();
        System.out.println("Empty:     " + solution.toString(solution.reverseListIterative(list3)));

        ListNode list4 = solution.buildList(7, 8, 9);
        System.out.println("Tail Rec.: " + solution.toString(solution.reverseListTailRecursive(list4)));

        ListNode list5 = solution.buildList(10, 20, 30);
        System.out.println("Stack:     " + solution.toString(solution.reverseListWithStack(list5)));
    }

    /**
     * Minimal singly-linked list definition used in LeetCode.
     */
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}

