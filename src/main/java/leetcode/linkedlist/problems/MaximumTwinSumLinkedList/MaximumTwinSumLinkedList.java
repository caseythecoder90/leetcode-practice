package leetcode.linkedlist.problems.MaximumTwinSumLinkedList;

/**
 * LeetCode 2130: Maximum Twin Sum of a Linked List
 *
 * Pattern: Reverse second half + twin traversal
 * Difficulty: Medium
 *
 * Given a list of even length, find the maximum sum of values of twin nodes.
 */
public class MaximumTwinSumLinkedList {

    /**
     * Optimal solution: reverse the second half in-place, then walk halves in sync.
     * Time: O(n)  |  Space: O(1)
     */
    public int pairSum(ListNode head) {
        if (head == null || head.next == null) {
            return head == null ? 0 : head.val;
        }

        // Step 1: find the start of the second half using slow/fast pointers
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: reverse the second half so twins align with first half nodes
        ListNode secondHalf = reverse(slow);

        // Step 3: walk both halves together, tracking maximum twin sum
        int maxSum = 0;
        ListNode firstHalf = head;
        ListNode secondPtr = secondHalf;
        while (secondPtr != null) {
            maxSum = Math.max(maxSum, firstHalf.val + secondPtr.val);
            firstHalf = firstHalf.next;
            secondPtr = secondPtr.next;
        }

        // Optional Step 4: restore list by reversing second half again if desired
        // reverse(secondHalf);

        return maxSum;
    }

    /** Helper that reverses a list segment in-place. */
    private ListNode reverse(ListNode node) {
        ListNode prev = null;
        ListNode curr = node;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /**
     * Alternative approach: copy values into an array.
     * Time: O(n) | Space: O(n)
     */
    public int pairSumArray(ListNode head) {
        java.util.List<Integer> values = new java.util.ArrayList<>();
        ListNode node = head;
        while (node != null) {
            values.add(node.val);
            node = node.next;
        }

        int left = 0;
        int right = values.size() - 1;
        int max = 0;
        while (left < right) {
            max = Math.max(max, values.get(left) + values.get(right));
            left++;
            right--;
        }
        return max;
    }

    /**
     * Stack-based variant for clarity.
     * Time: O(n) | Space: O(n)
     */
    public int pairSumStack(ListNode head) {
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        ListNode slow = head;
        ListNode fast = head;

        // Push first half values
        while (fast != null && fast.next != null) {
            stack.push(slow.val);
            slow = slow.next;
            fast = fast.next.next;
        }

        int max = 0;
        while (slow != null) {
            max = Math.max(max, slow.val + stack.pop());
            slow = slow.next;
        }
        return max;
    }

    /** Helper to build a list quickly for testing. */
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
        MaximumTwinSumLinkedList solver = new MaximumTwinSumLinkedList();

        ListNode sample1 = solver.buildList(5, 4, 2, 1);
        System.out.println("Sample 1 (expect 6): " + solver.pairSum(sample1));

        ListNode sample2 = solver.buildList(4, 2, 2, 3);
        System.out.println("Sample 2 (expect 7): " + solver.pairSum(sample2));

        ListNode sample3 = solver.buildList(1, 100000);
        System.out.println("Sample 3 (expect 100001): " + solver.pairSum(sample3));
    }

    /** Minimal singly-linked list definition. */
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

