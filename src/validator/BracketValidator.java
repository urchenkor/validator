package validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class BracketValidator {

    private int left;
    private int right;

    public static void main(String[] args) throws IOException {
        System.out.println("Введите строку для проверки валидности скобок" +
                "\n" + "или exit, чтобы выйти");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String str = reader.readLine();
            if (str.equals("exit")) {
                break;
            }
            System.out.println(validate(str));
        }
    }

    public static boolean validate (String str) {
        if (!firstStep(str)) {
            return false;
        }
        return secondValidate(str);

    }

    private static boolean firstStep(String str) {
        BracketValidator one = new BracketValidator();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '{' || str.charAt(i) == '(' || str.charAt(i) == '[') one.left++;
            else if (str.charAt(i) == '}' || str.charAt(i) == ')' || str.charAt(i) == ']') one.right++;
        }
        return one.left == one.right;
    }


    private static Boolean secondValidate(String str) {
        Map<Integer, Integer> numMap = new HashMap<>(); //<номер откнывающей скобки по порядку, номер скобки в массиве>
        Map<Integer, Character> valueMap = new HashMap<>(); // <номер откр. скобки по порядку, тип скобки>
        BracketValidator two = new BracketValidator();
        two.left = 0;
        two.right = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '{' || str.charAt(i) == '[' || str.charAt(i) == '(') {
                numMap.put(two.left, i);
                valueMap.put(two.left, str.charAt(i));
                two.left++;
            }
        }
        String subStr;
        String withoutBktStr = "" + str;
        Character openBkt;
        char closeBkt;
        int numCloseBktInStr = 0;
        for (int j = two.left - 1; j >= 0; j--) {
            subStr = withoutBktStr.substring(numMap.get(j));
            if (subStr.length() == 1) {
                return false;
            }
            openBkt = valueMap.get(j);
            closeBkt = getCloseBkt(openBkt);

            for (int k = 1; k < subStr.length(); k++) {
                char symbol = subStr.charAt(k);
                if (isBkt(symbol)) {
                    if (symbol == closeBkt) {
                        numCloseBktInStr = withoutBktStr.length() - (subStr.length() - k);
                        break;
                    } else {
                        return false;
                    }
                }
            }
            withoutBktStr = getStrWithoutBkt(withoutBktStr, numMap.get(j), numCloseBktInStr);
        }

        return true;
    }

    private static String getStrWithoutBkt(String str, int openBkt, int closeBkt) {
        String strWithoutBkt = "";
        for (int i = 0; i < str.length(); i++) {
            if (i != openBkt && i != closeBkt)
                strWithoutBkt += str.charAt(i);
        }
        return strWithoutBkt;
    }

    private static Character getCloseBkt(Character openBkt) {
        if (openBkt.equals('{')) {
            return '}';
        } else if (openBkt.equals('[')) {
            return ']';
        } else
            return ')';
    }

    private static boolean isBkt(char symbol) {
        char[] bkts = {'(', ')', '[', ']', '{', '}'};
        for (int i = 0; i < bkts.length; i++) {
            if (bkts[i] == symbol) return true;
        }
        return false;
    }


}