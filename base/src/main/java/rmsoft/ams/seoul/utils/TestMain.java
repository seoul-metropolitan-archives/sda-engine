package rmsoft.ams.seoul.utils;

import java.util.UUID;

public class TestMain {
    public static void main(String[] args){

//        for(int i = 1000000 ; i< 1000100;  i++) {
//            String result = PrinterUtils.convertSequenceTo5Char(i);
//            System.out.println(result);
//        }
    }
    public static String convertSeqTo36Number(int num){

        String code = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String text = "";
        int j = (int)Math.ceil(Math.log(num)/Math.log(code.length()));
        for(int i = 0; i < j; i++){
            //i goes to log base code.length() of num (using change of base formula)
            text += code.charAt(num%code.length());
            num /= code.length();
        }

        return text;
    }
    public static String toAlphabetic(int i) {
        if( i<0 ) {
            return "-"+toAlphabetic(-i-1);
        }

        int quot = i/26;
        int rem = i%26;
        char letter = (char)((int)'A' + rem);
        if( quot == 0 ) {
            return ""+letter;
        } else {
            return toAlphabetic(quot-1) + letter;
        }
    }
    public static String abcBase36(int i) {

        // 원래 0부터 시작 했지만, '00' 이라는 값이 출력되면 '0' 값과 동일 하므로 빼버림.
        char[] ALPHABET = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int quot = i / 35;
        int rem = i % 35;

        char letter = ALPHABET[rem];
        if (quot == 0) {
            return "" + letter;
        } else {
            return abcBase36(quot - 1) + letter;
        }
    }
    /* public static String to_alphabetic(int i) {
       int base=36;
        String letter= "";

        int quot = i / base;
        int rem = i % base
        if (rem< 26) {
            letter = chr(ord("A") + rem);
        }
        else if( rem <36) {
            letter = str(rem - 26);
        }
    else:
        letter = chr(ord("a") + rem - 36)
        if quot == 0:
        return letter
    else:
        return to_alphabetic(quot - 1, base) + letter
    }*/
}
