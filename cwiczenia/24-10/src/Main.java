import java.util.Scanner;

// grupa - Dariusz Majnert, Natalia Marszałek

public class Main {

    static String symbols[] = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD","D", "CM", "M"};
    static int divisors[] = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Podaj liczbe rzymską: ");
        String n = in.next();
        System.out.println(s(n));

        System.out.println("Podaj liczbe arabska: ");
        int value = in.nextInt();
        System.out.println(l(value));
    }

    static int s(String n) {
        int i = symbols.length -1;
        int result = 0;
        while(n.length() > 0 && i >= 0) {
            String symbol = symbols[i];

            if(n.startsWith(symbol)) {
                result += divisors[i];
                n = n.substring(symbol.length());
            }
            else {
                i--;
            }
        }
        return result;
    }

    static String l(int value) {
        String result ="";
        int i = symbols.length - 1;

        while(value > 0) {
            if((float)value / (float)divisors[i] >= 1) {
                result += symbols[i];
                value -= divisors[i];
            }
            else {
                value %= divisors[i];
                i--;
            }
        }
        return result;
    }

}