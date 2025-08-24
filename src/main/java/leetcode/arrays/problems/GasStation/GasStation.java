package leetcode.arrays.problems.GasStation;

import java.util.Arrays;

public class GasStation {
    
    /**
     * Optimal Solution: One-pass Greedy Approach
     * Key Insights:
     * 1. If total gas >= total cost, there exists a solution
     * 2. If we can't reach station j from any station before i, then we can't reach j from station i either
     * 3. So if we fail at station j starting from i, we should try starting from j+1
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0, totalCost = 0;
        int currentGas = 0, startStation = 0;
        
        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            currentGas += gas[i] - cost[i];
            
            // If we can't proceed from current position
            if (currentGas < 0) {
                startStation = i + 1;  // Try starting from next station
                currentGas = 0;        // Reset gas for new start
            }
        }
        
        // If total gas < total cost, no solution exists
        return totalGas >= totalCost ? startStation : -1;
    }
    
    /**
     * Brute Force Solution (Your Original Approach - Fixed)
     * Time: O(n²), Space: O(1)
     */
    public int canCompleteCircuitBruteForce(int[] gas, int[] cost) {
        int n = gas.length;
        
        // Try each starting position
        for (int start = 0; start < n; start++) {
            int currentGas = 0;
            int stationsTraveled = 0;
            
            // Try to complete circuit from this start position
            for (int i = start; stationsTraveled < n; i = (i + 1) % n) {
                currentGas += gas[i];      // Fill up at current station
                currentGas -= cost[i];     // Pay cost to travel to next
                
                if (currentGas < 0) {      // Can't proceed
                    break;
                }
                
                stationsTraveled++;
            }
            
            // If we completed the full circuit
            if (stationsTraveled == n) {
                return start;
            }
        }
        
        return -1;  // No solution found
    }
    
    /**
     * Your Original Solution - With Issues Highlighted
     */
    public int yourOriginalSolution(int[] gas, int[] cost) {
        int totalStations = gas.length;
        int currentGasLevel = 0;

        for (int startStation = 0; startStation < totalStations; startStation++) {
            int nextStation = (startStation + 1) % totalStations;
            int currentStation = startStation;

            // ISSUE 1: nextStation is not needed and causes confusion
            // ISSUE 2: Loop condition is checking nextStation instead of progress
            while (currentGasLevel >= 0 && nextStation != startStation) {
                currentGasLevel += gas[currentStation];
                currentGasLevel -= cost[currentStation];

                currentStation = (currentStation + 1) % totalStations;
                nextStation = (nextStation + 1) % totalStations;  // ISSUE 3: Unnecessary
            }

            // ISSUE 4: This check happens after the loop, but the loop might exit early
            if (currentStation == startStation) 
                return startStation;

            currentGasLevel = 0;  // ISSUE 5: Reset should happen before trying new start
        }

        return -1;
    }

    public static void main(String[] args) {
        GasStation solution = new GasStation();
        
        // Test Case 1: Expected output = 3
        System.out.println("=== Test Case 1 ===");
        int[] gas1 = {1, 2, 3, 4, 5};
        int[] cost1 = {3, 4, 5, 1, 2};
        System.out.println("Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]");
        System.out.println("Optimal Solution: " + solution.canCompleteCircuit(gas1, cost1));
        System.out.println("Brute Force: " + solution.canCompleteCircuitBruteForce(gas1, cost1));
        System.out.println("Expected: 3");
        
        // Test Case 2: Expected output = -1
        System.out.println("\n=== Test Case 2 ===");
        int[] gas2 = {2, 3, 4};
        int[] cost2 = {3, 4, 3};
        System.out.println("Input: gas = [2,3,4], cost = [3,4,3]");
        System.out.println("Optimal Solution: " + solution.canCompleteCircuit(gas2, cost2));
        System.out.println("Brute Force: " + solution.canCompleteCircuitBruteForce(gas2, cost2));
        System.out.println("Expected: -1");
        
        // Test Case 3: Single station
        System.out.println("\n=== Test Case 3 ===");
        int[] gas3 = {5};
        int[] cost3 = {4};
        System.out.println("Input: gas = [5], cost = [4]");
        System.out.println("Optimal Solution: " + solution.canCompleteCircuit(gas3, cost3));
        System.out.println("Brute Force: " + solution.canCompleteCircuitBruteForce(gas3, cost3));
        System.out.println("Expected: 0");
        
        // Test Case 4: Edge case where solution exists at last station
        System.out.println("\n=== Test Case 4 ===");
        int[] gas4 = {3, 1, 1};
        int[] cost4 = {1, 2, 2};
        System.out.println("Input: gas = [3,1,1], cost = [1,2,2]");
        System.out.println("Optimal Solution: " + solution.canCompleteCircuit(gas4, cost4));
        System.out.println("Brute Force: " + solution.canCompleteCircuitBruteForce(gas4, cost4));
        System.out.println("Expected: 0");
        
        // Test Case 5: Your original failing case demo
        System.out.println("\n=== Demonstrating Issues with Original Solution ===");
        System.out.println("Original solution issues:");
        System.out.println("1. Loop exits early due to incorrect condition");
        System.out.println("2. nextStation pointer creates confusion");
        System.out.println("3. Gas level not properly managed across iterations");
    }
}