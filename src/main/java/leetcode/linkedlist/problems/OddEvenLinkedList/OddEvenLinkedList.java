package leetcode.linkedlist.problems.OddEvenLinkedList;

/**
 * LeetCode 328: Odd Even Linked List
 *
 * Pattern: Re-link nodes by index parity.
 * Difficulty: Medium
 */
public class OddEvenLinkedList {

    /**
     * Optimal in-place solution using two pointers.
     * Time: O(n) | Space: O(1)
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even; // Keep start of even list to attach later

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;

            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;
        return head;
    }

    /**
     * Alternative: build separate odd/even lists using dummy nodes.
     * Slightly longer but sometimes clearer.
     */
    public ListNode oddEvenListWithDummies(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode oddDummy = new ListNode(0);
        ListNode evenDummy = new ListNode(0);
        ListNode oddTail = oddDummy;
        ListNode evenTail = evenDummy;
        ListNode current = head;
        boolean isOdd = true;

        while (current != null) {
            if (isOdd) {
                oddTail.next = current;
                oddTail = oddTail.next;
            } else {
                evenTail.next = current;
                evenTail = evenTail.next;
            }
            current = current.next;
            isOdd = !isOdd;
        }

        evenTail.next = null;
        oddTail.next = evenDummy.next;
        return oddDummy.next;
    }

    /** Helper to build lists quickly. */
    public ListNode buildList(int... values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int value : values) {
            tail.next = new ListNode(value);
            tail = tail.next;
        }
        return dummy.next;
    }

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

    public static void main(String[] args) {
        OddEvenLinkedList solver = new OddEvenLinkedList();

        ListNode list1 = solver.buildList(1, 2, 3, 4, 5);
        System.out.println("Example 1: " + solver.toString(solver.oddEvenList(list1))); // [1,3,5,2,4]

        ListNode list2 = solver.buildList(2, 1, 3, 5, 6, 4, 7);
        System.out.println("Example 2: " + solver.toString(solver.oddEvenList(list2))); // [2,3,6,7,1,5,4]
    }

    /** Basic singly-linked list node definition. */
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

