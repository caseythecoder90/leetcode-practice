package leetcode.bitmanipulation.practice;

public class BinaryPractice {

    public String convertDecimalToBinary(int n) {
        if (n == 0) return "0";
        StringBuilder binaryStr = new StringBuilder();
        while (n > 0) {
            binaryStr.insert(0, n % 2);
            n /= 2;
        }
        return binaryStr.toString();
    }

    public int binaryToDecimal(String binary) {
        int decimal = 0;
        int power = 0;

        for (int i = binary.length() - 1; i >= 0; i--) {
            if (binary.charAt(i) == '1') {
                decimal += (int) Math.pow(2, power);
            }
            power++;
        }

        return decimal;
    }

    public static void main(String[] args) {

        BinaryPractice practice = new BinaryPractice();

        int twelve = 12;
        System.out.println(practice.convertDecimalToBinary(twelve));

        int thirtySeven = 37;
        System.out.println(practice.convertDecimalToBinary(thirtySeven));

    }
}
