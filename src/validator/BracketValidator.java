package validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class BracketValidator {

    private int numOfOpenBkt;
    private int numOfCloseBkt;

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
            if (str.charAt(i) == '{' || str.charAt(i) == '(' || str.charAt(i) == '[') one.numOfOpenBkt++;
            else if (str.charAt(i) == '}' || str.charAt(i) == ')' || str.charAt(i) == ']') one.numOfCloseBkt++;
        }
        return one.numOfOpenBkt == one.numOfCloseBkt;
    }


    private static Boolean secondValidate(String str) {
        Map<Integer, Integer> openBktNumMap = new HashMap<>(); //<номер откнывающей скобки по порядку, номер скобки в массиве>
        Map<Integer, Character> valueMap = new HashMap<>(); // <номер откр. скобки по порядку, тип скобки>
        BracketValidator two = new BracketValidator();
        two.numOfOpenBkt = 0;
        two.numOfCloseBkt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '{' || str.charAt(i) == '[' || str.charAt(i) == '(') {
                openBktNumMap.put(two.numOfOpenBkt, i);
                valueMap.put(two.numOfOpenBkt, str.charAt(i));
                two.numOfOpenBkt++;
            }
        }
        StringBuilder withoutBkt = new StringBuilder(str);
        String subStr;
        char openBkt;
        char closeBkt;
        int numCloseBktInStr;
        for (int j = two.numOfOpenBkt - 1; j >= 0; j--) {
            subStr = withoutBkt.substring(openBktNumMap.get(j));
            if (subStr.length() == 1) {
                return false;
            }
            openBkt = valueMap.get(j);
            closeBkt = getCloseBkt(openBkt);

            for (int k = 1; k < subStr.length(); k++) {
                char symbol = subStr.charAt(k);
                if (isBkt(symbol)) {
                    if (symbol == closeBkt) {
                        numCloseBktInStr = withoutBkt.length() - (subStr.length() - k);
                        withoutBkt.deleteCharAt(numCloseBktInStr);
                        withoutBkt.deleteCharAt(openBktNumMap.get(j));
                        break;
                    } else {
                        return false;
                    }
                }
            }
        }

        return true;
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
        for (char bkt : bkts) {
            if (bkt == symbol) return true;
        }
        return false;
    }
}