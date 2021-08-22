package prog;

import java.util.Scanner;

public class My {
    private My() {
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int nextInt(Scanner sc, String text, int min, int max){
        while(true) {
            System.out.print(text);
            String cmd = sc.next();

            if(isInteger(cmd)) {
                int num = Integer.parseInt(cmd);
                if(num < min) {
                    System.out.println("Число не может быть меньше " + min);
                }
                else if(num > max) {
                    System.out.println("Число не может быть больше " + max);
                }
                else {
                    return num;
                }
            }
        }
    }

    public static int nextInt(Scanner sc, String text){
        return nextInt(sc, text, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int nextIntFromArr(Scanner sc, String text, int... arr){
        while(true) {
            System.out.print(text);
            String cmd = sc.next();

            if(My.isInteger(cmd)) {
                int num = Integer.parseInt(cmd);
                for (int tmp : arr) {
                    if(num == tmp) {
                        return num;
                    }
                }
            }
        }
    }

    public static double nextDouble(Scanner sc, String text, double min, double max){
        while(true) {
            System.out.print(text);
            String cmd = sc.next();

            if(isDouble(cmd)) {
                double num = Double.parseDouble(cmd);
                if(num >= min && num <= max) {
                    return num;
                }
            }
        }
    }

    public static double nextDouble(Scanner sc, String text){
        return nextDouble(sc, text, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static char nextCharLowerCase(Scanner sc, String text, char... arr) {
        while(true) {
            System.out.print(text);
            String cmd = sc.next().toLowerCase();

            if(cmd.length() == 1) {
                char ch = cmd.charAt(0);
                ch = Character.toLowerCase(ch);
                for (char tmp : arr) {
                    tmp = Character.toLowerCase(tmp);
                    if(tmp == ch) {
                        return tmp;
                    }
                }
            }
        }
    }

    public static boolean cmpStr(String str1, String str2) {
        return (str1.compareToIgnoreCase(str2) == 0);
    }

    //пауза
    public static void sleep(int n){
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void sleepAnimation(int n, boolean on){
        n /= 500;
        for (int i = 0; i < n; i++) {
            if (on) {
                sleep(500);
                System.out.print(".");
            }
        }
    }

    public static void sleepAnimationLn(int n, boolean on){
        sleepAnimation(n, on);
        System.out.println();
    }

    public static void sleepAnimationLn(int n) {
        sleepAnimationLn(n, true);
    }

    public static String getLine(String str, char chFill) {
        String res = "";
        for (int i = 0; i < str.length(); i++) {
            res += chFill;
        }
        return res;
    }

    public static String getLine(String str) {
        return getLine(str, '-');
    }

    public static String getLine(int length, char chFill) {
        String str ="";
        for (int i = 0; i < length; i++) {
            str += chFill;
        }
        return str;
    }

    public static String getLine(int length) {
        return getLine(length, '-');
    }


    //замена символа в массиве string
    public static void changeCharInArr(String[] arr, char oldChar, char newChar ) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].replace(oldChar, newChar);
        }
    }

    public static char intToChar(int n) {
        return (char)(n + 48);
    }

    public static void printArr(int dist, String[]... arr) {
        String strDist = "%" + dist + "s";
        strDist = String.format(strDist,"");
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].length > max) {
                max = arr[i].length;
            }
        }

        for (int n = 0; n < max; n++) {
            for (int i = 0; i < arr.length; i++) {
                String[] pic = arr[i];
                System.out.print(pic[n] + strDist);
            }
            System.out.println();
        }
    }

    public static void printArr(String[]... arr) {
        printArr(3, arr);

    }


    //склеить несколько массивов в один
    public static String[] unityArr(int interval, String[]... arr) {
        String[] newArr = new String[arr[0].length];

        for (int i = 0; i < arr[0].length; i++)
        {
            newArr[i] = "";
            for (String[] tmp : arr)
            {
                newArr[i] += tmp[i] + getStrSpaces(interval);
            }
        }
        return newArr;
    }

    public static String[] unityArr(String[]... arr) {
        return unityArr(3, arr);
    }

    //возвращает заданное количество пробелов
    public static String getStrSpaces(int num) {
        if(num <= 0 ) {
            return "";
        }
        String format = "%" + num + "s";
        return String.format(format,"");
    }

    //возвращает строку, выровненную по центру
    public static String getStrAlignCenter(String str, int length) {
        int start = (length - str.length()) / 2;

        String format = "%" + start + "s" + "%-" + (length - start) + "s";

        return String.format(format,"",str);
    }

    //число попадает в диапазон?
    public static boolean isRange(int min, int max, int val) {
        return (min <= val && val <= max);
    }






}
