# Maximum Twin Sum - Flashcards

**Q:** How do you locate the start of the second half in an even-length list?  
**A:** Use slow/fast pointers; when fast hits the end, slow sits at the first node of the second half.

**Q:** Why reverse the second half instead of using a stack or array?  
**A:** Reversal moves each twin next to its partner while keeping O(1) extra space.

**Q:** What is the time and space complexity of the optimal solution?  
**A:** O(n) time, O(1) additional space.

**Q:** What happens after reversing the second half?  
**A:** Traverse both halves together, updating `maxSum = max(maxSum, first.val + second.val)`.

**Q:** Do you need to restore the original list?  
**A:** Not required by the problem, but you can reverse the second half again to show thoroughness.

**Q:** What edge case ensures the loop terminates safely?  
**A:** The list length is even, so the second half pointer always reaches null.

**Q:** Which alternative approach is simplest but less optimal?  
**A:** Copy values into an array and use two indices; costs O(n) extra space.

**Q:** Why avoid recursive reversal here?  
**A:** Depth could be up to 1e5 nodes, exceeding call stack limits.

