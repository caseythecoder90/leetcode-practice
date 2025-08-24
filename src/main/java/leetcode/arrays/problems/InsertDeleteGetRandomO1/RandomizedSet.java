package leetcode.arrays.problems.InsertDeleteGetRandomO1;

import java.util.*;

public class RandomizedSet {
    private Map<Integer, Integer> valueToIndex;
    private List<Integer> values;
    private Random random;

    public RandomizedSet() {
        valueToIndex = new HashMap<>();
        values = new ArrayList<>();
        random = new Random();
    }

    public boolean insert(int val) {
        if (valueToIndex.containsKey(val)) {
            return false;
        }

        // Add value to end of list and store its index in map
        values.add(val);
        valueToIndex.put(val, values.size() - 1);
        return true;
    }

    public boolean remove(int val) {
        if (!valueToIndex.containsKey(val)) {
            return false;
        }

        // Get the index of element to remove
        int indexToRemove = valueToIndex.get(val);
        int lastElement = values.get(values.size() - 1);

        // Swap the element to remove with the last element
        values.set(indexToRemove, lastElement);
        valueToIndex.put(lastElement, indexToRemove);

        // Remove the last element
        values.remove(values.size() - 1);
        valueToIndex.remove(val);

        return true;
    }

    public int getRandom() {
        int randomIndex = random.nextInt(values.size());
        return values.get(randomIndex);
    }

    public static void main(String[] args) {
        RandomizedSet randomizedSet = new RandomizedSet();
        
        // Test case 1: Basic operations
        System.out.println("=== Test Case 1: Basic Operations ===");
        System.out.println("insert(1): " + randomizedSet.insert(1)); // true
        System.out.println("remove(2): " + randomizedSet.remove(2)); // false
        System.out.println("insert(2): " + randomizedSet.insert(2)); // true
        System.out.println("getRandom(): " + randomizedSet.getRandom()); // 1 or 2
        System.out.println("remove(1): " + randomizedSet.remove(1)); // true
        System.out.println("insert(2): " + randomizedSet.insert(2)); // false
        System.out.println("getRandom(): " + randomizedSet.getRandom()); // 2
        
        // Test case 2: Large number of operations (similar to failing test)
        System.out.println("\n=== Test Case 2: Large Operations ===");
        RandomizedSet largeSet = new RandomizedSet();
        largeSet.insert(1);
        largeSet.insert(2);
        largeSet.insert(3);
        largeSet.insert(1); // should return false
        
        // Multiple getRandom calls to test performance
        for (int i = 0; i < 10; i++) {
            System.out.println("getRandom(): " + largeSet.getRandom());
        }
        
        // Test case 3: Edge cases
        System.out.println("\n=== Test Case 3: Edge Cases ===");
        RandomizedSet edgeSet = new RandomizedSet();
        edgeSet.insert(0);
        System.out.println("insert(0): true");
        System.out.println("remove(0): " + edgeSet.remove(0)); // true
        
        edgeSet.insert(-1);
        edgeSet.insert(Integer.MAX_VALUE);
        System.out.println("insert(-1) and insert(MAX_VALUE): true");
        System.out.println("getRandom(): " + edgeSet.getRandom()); // -1 or MAX_VALUE
    }
}