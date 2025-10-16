# LinkedList Pattern - Cheat Sheet

## Quick Pattern Recognition

| Problem Keywords | Pattern | Template |
|-----------------|---------|----------|
| "reverse", "reverse order" | Reversal | Iterative with prev/current |
| "middle", "center" | Two Pointers | Fast/slow pointers |
| "cycle", "loop" | Cycle Detection | Floyd's algorithm |
| "nth from end" | Runner Technique | Two pointers with gap |
| "remove", "delete" | Deletion | Dummy node + pointer manipulation |
| "merge", "combine" | Merging | Two pointers + comparison |
| "odd/even", "rearrange" | Rearrangement | Multiple pointers |

## Essential Templates

### 1. Standard ListNode Definition
```java
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
```

### 2. Basic Traversal Template
```java
public void traverse(ListNode head) {
    ListNode current = head;
    
    while (current != null) {
        // Process current node
        System.out.println(current.val);
        current = current.next;
    }
}
```

### 3. Iterative Reversal Template (MUST KNOW)
```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;
    
    while (current != null) {
        ListNode nextTemp = current.next; // Store next
        current.next = prev;              // Reverse link
        prev = current;                   // Move prev
        current = nextTemp;               // Move current
    }
    
    return prev; // New head
}
```

### 4. Two Pointers - Fast/Slow Template
```java
public ListNode findMiddle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;
    
    // Fast moves 2 steps, slow moves 1 step
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    return slow; // Middle node
}
```

### 5. Cycle Detection Template (Floyd's Algorithm)
```java
public boolean hasCycle(ListNode head) {
    if (head == null || head.next == null) return false;
    
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        
        if (slow == fast) {
            return true; // Cycle detected
        }
    }
    
    return false;
}
```

### 6. Dummy Node Template
```java
public ListNode removeElements(ListNode head, int val) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode current = dummy;
    
    while (current.next != null) {
        if (current.next.val == val) {
            current.next = current.next.next; // Skip node
        } else {
            current = current.next;
        }
    }
    
    return dummy.next; // Return real head
}
```

### 7. Runner Technique Template (Gap of n)
```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode first = dummy;
    ListNode second = dummy;
    
    // Create gap of n+1 between pointers
    for (int i = 0; i <= n; i++) {
        first = first.next;
    }
    
    // Move both until first reaches end
    while (first != null) {
        first = first.next;
        second = second.next;
    }
    
    // Remove the target node
    second.next = second.next.next;
    return dummy.next;
}
```

### 8. Merge Two Lists Template
```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            current.next = l1;
            l1 = l1.next;
        } else {
            current.next = l2;
            l2 = l2.next;
        }
        current = current.next;
    }
    
    // Attach remaining nodes
    current.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

## Problem-Specific Quick Solutions

### Delete Middle Node (LeetCode 2095)
```java
public ListNode deleteMiddle(ListNode head) {
    if (head == null || head.next == null) return null;
    
    ListNode slow = head, fast = head, prev = null;
    
    while (fast != null && fast.next != null) {
        prev = slow;
        slow = slow.next;
        fast = fast.next.next;
    }
    
    prev.next = slow.next; // Delete middle
    return head;
}
```

### Odd Even Linked List (LeetCode 328)
```java
public ListNode oddEvenList(ListNode head) {
    if (head == null || head.next == null) return head;
    
    ListNode odd = head;
    ListNode even = head.next;
    ListNode evenHead = even;
    
    while (even != null && even.next != null) {
        odd.next = even.next;  // Connect odd nodes
        odd = odd.next;
        even.next = odd.next;  // Connect even nodes  
        even = even.next;
    }
    
    odd.next = evenHead; // Connect odd tail to even head
    return head;
}
```

### Maximum Twin Sum (LeetCode 2130)
```java
public int pairSum(ListNode head) {
    // Method 1: Convert to array
    List<Integer> values = new ArrayList<>();
    ListNode current = head;
    
    while (current != null) {
        values.add(current.val);
        current = current.next;
    }
    
    int maxSum = 0;
    int n = values.size();
    
    for (int i = 0; i < n / 2; i++) {
        maxSum = Math.max(maxSum, values.get(i) + values.get(n - 1 - i));
    }
    
    return maxSum;
}
```

## Common Edge Cases Checklist

### Always Check These:
```java
// 1. Null head
if (head == null) return null; // or appropriate value

// 2. Single node
if (head.next == null) return head; // or appropriate action

// 3. Two nodes
if (head.next.next == null) {
    // Handle two-node case
}

// 4. Before accessing next.next
if (fast != null && fast.next != null) {
    fast = fast.next.next;
}
```

## Time/Space Complexity Quick Reference

| Operation | Time | Space | Notes |
|-----------|------|-------|-------|
| Traversal | O(n) | O(1) | Single pass |
| Reversal (iterative) | O(n) | O(1) | Single pass |
| Reversal (recursive) | O(n) | O(n) | Call stack |
| Find middle | O(n) | O(1) | Two pointers |
| Cycle detection | O(n) | O(1) | Floyd's algorithm |
| Remove nth from end | O(n) | O(1) | Runner technique |
| Merge two lists | O(n+m) | O(1) | Two pointers |

## Decision Tree for Approach Selection

```
LinkedList Problem?
├─ Need to reverse? → Use iterative reversal template
├─ Need to find position (middle, nth from end)? → Use two pointers
├─ Head might change? → Use dummy node
├─ Need to detect cycle? → Use Floyd's algorithm
├─ Rearranging nodes? → Use multiple pointers with careful tracking
└─ Combining lists? → Use merge template
```

## Interview Coding Tips

### 1. Start with Edge Cases
```java
// Template start for most problems
if (head == null) return null;
if (head.next == null) return head; // or handle appropriately
```

### 2. Use Descriptive Variable Names
```java
// ✅ Clear naming
ListNode slow = head, fast = head, prev = null;

// ❌ Confusing naming  
ListNode p1 = head, p2 = head, p3 = null;
```

### 3. Draw and Trace
```
Before: [1] -> [2] -> [3] -> [4] -> null
After:  [4] -> [3] -> [2] -> [1] -> null

Step by step for reversal:
prev=null, curr=[1]
prev=[1], curr=[2]  // [1]->null, [2]->[3]->[4]
prev=[2], curr=[3]  // [2]->[1]->null, [3]->[4]
...
```

### 4. Common Mistake Patterns to Avoid
```java
// ❌ Losing reference
current.next = newNode; // Lost rest of list!

// ✅ Store first
ListNode temp = current.next;
current.next = newNode;
newNode.next = temp;

// ❌ Null pointer dereference
while (current.next != null) // If current is null...

// ✅ Safe checking
while (current != null && current.next != null)
```

## Memory Aids

- **"Prev, Current, Next"** - Standard reversal pattern
- **"Fast twice, slow once"** - Two pointer technique
- **"Dummy simplifies"** - Use dummy when head changes
- **"Store before modify"** - Always preserve references
- **"Draw then code"** - Visualize before implementing

## Problem Categories Quick Reference

| Category | Key Problems | Pattern |
|----------|-------------|---------|
| Basic Operations | 206, 237, 203 | Traversal + manipulation |
| Two Pointers | 876, 141, 19 | Fast/slow or runner |
| Rearrangement | 328, 86, 24 | Multiple pointer tracking |
| Merging | 21, 148, 23 | Comparison + pointer movement |
| Advanced | 138, 146, 2 | Combine with other structures |