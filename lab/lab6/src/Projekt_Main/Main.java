package Projekt_Main;

import Dlugopis.Dlugopis;
import Uczen.Uczen;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Dlugopis[] createPiornik(int n) {
        Dlugopis[] piornik = new Dlugopis[n];
        Random generator = new Random();
        for(int i = 0; i < n ; i++) {
            piornik[i] = new Dlugopis(generator.nextInt(4) + 1);
        }
        return piornik;
    }
    public static void printPiornik(Dlugopis[] piornik) {
        for(int i = 0 ; i < piornik.length ; i ++) {
            Dlugopis dlugopis = piornik[i];
            System.out.printf("Długopis o indeksie=%d kolor=%d jestwPiorniku=%b\n", i, dlugopis.getWklad().getKolor(), dlugopis.getStan());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Podaj n: ");
        int n = in.nextInt();
        Dlugopis[] piornik = createPiornik(n);
        printPiornik(piornik);

        // wyjmij 3 ostatnie dlugopisy
        for(int i = Math.max(n - 3, 0); i < n; i++) {
            Uczen.wyjmijDlugopis(piornik[i]);
        }

        // wloz ostatni dlugopis z powrotem
        Uczen.wlozDlugopis(piornik[n -1]);

        printPiornik(piornik);
    }
}