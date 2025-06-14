package leetcode.bitmanipulation.problems.SingleNumber;

public class SingleNumber {

    public int singleNumber(int[] nums) {
        int sol = 0;
        for (int num : nums) {
            sol ^= num;
        }
        return sol;
    }


    public static void main(String[] args) {
        SingleNumber singleNumber = new SingleNumber();
        int[] numbers = {1, 1, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 9, 9};
        System.out.println(singleNumber.singleNumber(numbers));
    }


}
