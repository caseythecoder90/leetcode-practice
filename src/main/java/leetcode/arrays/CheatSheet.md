# Arrays Cheat Sheet

## Java Array Operations

```java
// Declaring arrays
int[] arr = new int[10];  // Size 10 array initialized with default values (0)
int[] arr = {1, 2, 3, 4, 5};  // Initialize with values

// Getting length
int length = arr.length;

// Sorting
Arrays.sort(arr);

// Binary search (on sorted array)
int index = Arrays.binarySearch(arr, target);

// Copy array
int[] copy = Arrays.copyOf(arr, arr.length);
int[] partialCopy = Arrays.copyOfRange(arr, startInclusive, endExclusive);

// Fill array
Arrays.fill(arr, value);

// Convert to list
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
```

## Common Array Patterns

### Two Pointers
```java
int left = 0;
int right = arr.length - 1;
while (left < right) {
    // Process arr[left] and arr[right]
    left++;
    right--;
}
```

### Sliding Window
```java
int left = 0;
for (int right = 0; right < arr.length; right++) {
    // Add arr[right] to window
    
    while (/* window needs shrinking */) {
        // Remove arr[left] from window
        left++;
    }
    
    // Process current window
}
```

### Prefix Sum
```java
int[] prefix = new int[arr.length + 1];
for (int i = 0; i < arr.length; i++) {
    prefix[i + 1] = prefix[i] + arr[i];
}
// Sum from index i to j (inclusive): prefix[j+1] - prefix[i]
```

### Kadane's Algorithm (Maximum Subarray)
```java
int maxSoFar = arr[0];
int maxEndingHere = arr[0];

for (int i = 1; i < arr.length; i++) {
    maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
    maxSoFar = Math.max(maxSoFar, maxEndingHere);
}
// maxSoFar is the answer
```
