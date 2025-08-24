package leetcode.arrays.problems.HIndex;

import java.util.Arrays;

public class HIndex {
    
    public int hIndex(int[] citations) {
        return hIndexSorting(citations);
    }
    
    public int hIndexSorting(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        
        for (int i = 0; i < n; i++) {
            int papersWithAtLeastThisCitations = n - i;
            if (citations[i] >= papersWithAtLeastThisCitations) {
                return papersWithAtLeastThisCitations;
            }
        }
        
        return 0;
    }
    
    public int hIndexBucketSort(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];
        
        for (int citation : citations) {
            if (citation >= n) {
                buckets[n]++;
            } else {
                buckets[citation]++;
            }
        }
        
        int count = 0;
        for (int i = n; i >= 0; i--) {
            count += buckets[i];
            if (count >= i) {
                return i;
            }
        }
        
        return 0;
    }
    
    public int hIndexBruteForce(int[] citations) {
        int n = citations.length;
        int maxH = 0;
        
        for (int h = 1; h <= n; h++) {
            int count = 0;
            for (int citation : citations) {
                if (citation >= h) {
                    count++;
                }
            }
            if (count >= h) {
                maxH = h;
            }
        }
        
        return maxH;
    }
    
    public int hIndexSortingDescending(int[] citations) {
        Arrays.sort(citations);
        int n = citations.length;
        
        for (int i = n - 1; i >= 0; i--) {
            int position = n - i;
            if (citations[i] >= position) {
                continue;
            } else {
                return position - 1;
            }
        }
        
        return n;
    }
    
    private void printArrayAnalysis(int[] citations, String approach) {
        System.out.println("\n🔍 " + approach.toUpperCase() + " APPROACH ANALYSIS");
        System.out.println("=".repeat(50));
        System.out.println("Input: " + Arrays.toString(citations));
        
        if (approach.equals("sorting")) {
            int[] sorted = citations.clone();
            Arrays.sort(sorted);
            System.out.println("Sorted: " + Arrays.toString(sorted));
            System.out.println();
            
            System.out.println("H-Index Analysis:");
            System.out.println("Position | Citation | Papers >= Citation | H-Index Check");
            System.out.println("-".repeat(55));
            
            int n = sorted.length;
            for (int i = 0; i < n; i++) {
                int papersWithAtLeast = n - i;
                boolean isHIndex = sorted[i] >= papersWithAtLeast;
                System.out.printf("%8d | %8d | %18d | %s%n", 
                    i, sorted[i], papersWithAtLeast, 
                    isHIndex ? "✅ H-Index = " + papersWithAtLeast : "❌");
                
                if (isHIndex) {
                    System.out.println("\n🎯 Found H-Index: " + papersWithAtLeast);
                    break;
                }
            }
        }
    }
    
    public int hIndexWithTrace(int[] citations) {
        System.out.println("\n📊 H-INDEX DETAILED TRACE");
        System.out.println("=".repeat(40));
        System.out.println("Problem: Find maximum h where researcher has ≥h papers with ≥h citations each");
        System.out.println("Input: " + Arrays.toString(citations));
        
        int[] sorted = citations.clone();
        Arrays.sort(sorted);
        System.out.println("Sorted: " + Arrays.toString(sorted));
        System.out.println();
        
        System.out.println("💡 Key Insight: After sorting, for position i:");
        System.out.println("   • Papers with ≥ citations[i] citations = n - i");
        System.out.println("   • If citations[i] ≥ (n - i), then h-index = n - i");
        System.out.println();
        
        System.out.println("Step-by-step analysis:");
        System.out.println("Position | Citation | Papers ≥ Citation | Valid H-Index?");
        System.out.println("-".repeat(55));
        
        int n = sorted.length;
        for (int i = 0; i < n; i++) {
            int papersWithAtLeast = n - i;
            boolean isValid = sorted[i] >= papersWithAtLeast;
            
            System.out.printf("%8d | %8d | %17d | ", i, sorted[i], papersWithAtLeast);
            
            if (isValid) {
                System.out.println("✅ YES → H-Index = " + papersWithAtLeast);
                System.out.println("\n🏆 RESULT: H-Index = " + papersWithAtLeast);
                System.out.println("📋 Verification: " + papersWithAtLeast + " papers have ≥" + papersWithAtLeast + " citations each");
                return papersWithAtLeast;
            } else {
                System.out.println("❌ NO (" + sorted[i] + " < " + papersWithAtLeast + ")");
            }
        }
        
        System.out.println("\n🏆 RESULT: H-Index = 0 (no valid h-index found)");
        return 0;
    }
    
    public static void main(String[] args) {
        HIndex solution = new HIndex();
        
        System.out.println("=".repeat(60));
        System.out.println("               H-INDEX COMPREHENSIVE TESTING");
        System.out.println("=".repeat(60));
        
        int[][] testCases = {
            {3, 0, 6, 1, 5},
            {1, 3, 1},
            {100},
            {0, 0},
            {1, 1},
            {10, 8, 5, 4, 3},
            {25, 8, 5, 3, 3}
        };
        
        int[] expected = {3, 1, 1, 0, 1, 4, 3};
        
        for (int i = 0; i < testCases.length; i++) {
            int[] citations = testCases[i];
            System.out.println("\n" + "=".repeat(40));
            System.out.println("Test Case " + (i + 1) + ": " + Arrays.toString(citations));
            System.out.println("Expected: " + expected[i]);
            
            long startTime = System.nanoTime();
            int resultSorting = solution.hIndexSorting(citations.clone());
            long sortingTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            int resultBucket = solution.hIndexBucketSort(citations.clone());
            long bucketTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            int resultBrute = solution.hIndexBruteForce(citations.clone());
            long bruteTime = System.nanoTime() - startTime;
            
            System.out.println("\n📊 RESULTS:");
            System.out.println("Sorting Approach:    " + resultSorting + " (Time: " + sortingTime/1000 + "μs)");
            System.out.println("Bucket Sort:         " + resultBucket + " (Time: " + bucketTime/1000 + "μs)");
            System.out.println("Brute Force:         " + resultBrute + " (Time: " + bruteTime/1000 + "μs)");
            System.out.println("Expected:            " + expected[i]);
            
            boolean correct = (resultSorting == expected[i] && 
                             resultBucket == expected[i] && 
                             resultBrute == expected[i]);
            System.out.println("Status: " + (correct ? "✅ PASS" : "❌ FAIL"));
            
            if (citations.length <= 5) {
                solution.hIndexWithTrace(citations.clone());
            }
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎓 ALGORITHM COMPARISON SUMMARY");
        System.out.println("=".repeat(60));
        System.out.println("1. Sorting Approach:");
        System.out.println("   • Time: O(n log n), Space: O(1)");
        System.out.println("   • Pros: Intuitive, easy to understand");
        System.out.println("   • Cons: Not optimal time complexity");
        System.out.println();
        System.out.println("2. Bucket Sort Approach:");
        System.out.println("   • Time: O(n), Space: O(n)");
        System.out.println("   • Pros: Optimal time complexity");
        System.out.println("   • Cons: Uses extra space, more complex");
        System.out.println();
        System.out.println("3. Brute Force:");
        System.out.println("   • Time: O(n²), Space: O(1)");
        System.out.println("   • Pros: Most straightforward");
        System.out.println("   • Cons: Inefficient for large inputs");
        System.out.println();
        System.out.println("💡 For interviews: Start with sorting approach,");
        System.out.println("   then optimize to bucket sort if asked!");
    }
}