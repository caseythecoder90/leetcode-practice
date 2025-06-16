package leetcode.backtracking.problems.LetterCombinations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LetterCombinations {

    private Map<Character, String> letters = Map.of(
            '2', "abc",
            '3', "def",
            '4', "ghi",
            '5', "jkl",
            '6', "mno",
            '7', "pqrs",
            '8', "tuv",
            '9', "wxyz"
    );
    private String inputDigits;
    private List<String> combinations = new ArrayList<>();

    public List<String> letterCombinations(String digits) {

        if (digits.isEmpty()) {
            return combinations;
        }

        inputDigits = digits;

        backtrack(0, new StringBuilder());

        return combinations;

    }

    public void backtrack(int index, StringBuilder path) {

        // base case
        if (path.length() == inputDigits.length()) {
            combinations.add(path.toString());
            return;
        }

        // get the letters (String for the current number) for the current index from the map
        String currentLetters = letters.get(inputDigits.charAt(index));

        for (char c : currentLetters.toCharArray()) {
            path.append(c);
            backtrack(index + 1, path);
            path.deleteCharAt(index);
        }
    }

    public static void main(String[] args) {

        System.out.println("Hello, world! We are solving a backtracking problem..");

        String inputStr = "23";
        LetterCombinations letterCombinations = new LetterCombinations();
        System.out.println(letterCombinations.letterCombinations(inputStr));

    }
}
