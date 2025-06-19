package leetcode.heapsandpriorityqueues.problems.KthLargestElement;

import java.util.PriorityQueue;

public class KthLargestElementInArray {

    private PriorityQueue<Integer> heap = new PriorityQueue<>();

    public int findKthLargestElement(int[] numbers, int k) {

        heap = new PriorityQueue<>();

        for (int num : numbers) {
            heap.add(num);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        assert !heap.isEmpty();
        return heap.peek();
    }

    public int findKthLargest(int[] numbers, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int num : numbers) {
            if (heap.size() < k) {
                heap.offer(num);
            }
            else {
                assert !heap.isEmpty();
                int min = heap.peek();
                if (num > min) {
                    heap.poll();
                    heap.offer(num);
                }
            }
        }

        assert !heap.isEmpty();
        return heap.peek();

    }
}
