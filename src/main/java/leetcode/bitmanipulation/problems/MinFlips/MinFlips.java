package leetcode.bitmanipulation.problems.MinFlips;

public class MinFlips {

    public int minFlips(int a, int b, int c) {

        int minFlips = 0;

        boolean stopIterating = stopIterating(a, b, c);
        while (!stopIterating) {
            int aVal = a & 1;
            int bVal = b & 1;
            int cVal = c & 1;
            if (cVal == 0) {
                minFlips += (aVal + bVal);
            }
            else {
                if (aVal == 0 && bVal == 0) {
                    minFlips += 1;
                }
            }
            a >>= 1;
            b >>= 1;
            c >>= 1;
            stopIterating = stopIterating(a, b, c);
        }

        return minFlips;

    }

    private static boolean stopIterating(int a, int b, int c) {
        return (a == 0) && (b == 0) && (c == 0);
    }

    public static void main(String[] args) {

        

    }
}
