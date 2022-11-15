package Drugie;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Drugie {

    public static Towar[] createSklep(int n) {
        Towar[] sklep = new Towar[n];
        Random generator = new Random();
        for(int i = 0 ; i < n ; i ++) {

            int kod = generator.nextInt(1000000);
            double cena = generator.nextInt(100000) / 100.0;
            sklep[i] = new Towar(kod, cena);
        }

        return sklep;
    }

    public static void pokazTowary(Towar[] sklep) {
        for (Towar towar: sklep) {
            System.out.println(towar);
        }
    }

    public static int najdrozszyTowar(Towar[] sklep) {
        int c = 0;
        for(int i = 0; i < sklep.length; i++) {
            if(sklep[i].getCena() > sklep[c].getCena()) {
                c = i;
            }
        }
        return c;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("podaj liczbe towarow w sklepie: ");
        int n = scanner.nextInt();
        Towar[] sklep = createSklep(n);

        pokazTowary(sklep);

        int c = najdrozszyTowar(sklep);
        System.out.println("indeks najdrozszego towaru: "+ c);
        System.out.println(sklep[c]);

    }
}
