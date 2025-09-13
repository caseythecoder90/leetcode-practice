package leetcode.twopointers.problems.ContainerWithMostWater;

public class ContainerWithMostWater {
    
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            int width = right - left;
            int minHeight = Math.min(height[left], height[right]);
            int currentArea = width * minHeight;
            
            maxArea = Math.max(maxArea, currentArea);
            
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
    
    public static void main(String[] args) {
        ContainerWithMostWater solution = new ContainerWithMostWater();
        
        int[] test1 = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("Test 1: " + solution.maxArea(test1) + " (Expected: 49)");
        
        int[] test2 = {1, 1};
        System.out.println("Test 2: " + solution.maxArea(test2) + " (Expected: 1)");
        
        int[] test3 = {4, 3, 2, 1, 4};
        System.out.println("Test 3: " + solution.maxArea(test3) + " (Expected: 16)");
        
        int[] test4 = {1, 2, 1};
        System.out.println("Test 4: " + solution.maxArea(test4) + " (Expected: 2)");
        
        int[] test5 = {2, 3, 4, 5, 18, 17, 6};
        System.out.println("Test 5: " + solution.maxArea(test5) + " (Expected: 17)");
    }
}