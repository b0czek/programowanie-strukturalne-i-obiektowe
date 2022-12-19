package SwiateczneCiasto;

import SwiateczneCiasto.AlgorytmyPrzygotowaniaJablek.Cwiartki;
import SwiateczneCiasto.AlgorytmyPrzygotowaniaJablek.Kostka;
import SwiateczneCiasto.AlgorytmyPrzygotowaniaSliwek.Polowki;
import SwiateczneCiasto.AlgorytmyPrzygotowaniaSliwek.Zmiksowane;
import SwiateczneCiasto.Wypieki.Ciasto;
import SwiateczneCiasto.Wypieki.PieczeBabcia;
import SwiateczneCiasto.Wypieki.PieczeDziadek;

import java.util.Scanner;

public class Main {

    public static Ciasto osobaPieczaca;
    private static PieczeBabcia pieczeBabcia;
    private static PieczeDziadek pieczeDziadek;

    public static void zmienOsobaPieczaca(Scanner in) {
        System.out.print("Ktora osoba ma piec? d - dziadek, b - babcia: ");
        String answer = in.next();
        switch (answer) {
            case "b" -> osobaPieczaca = pieczeBabcia;
            case "d" -> osobaPieczaca = pieczeDziadek;
        }

    }
    public static void zmienSposobSzarlotka(Scanner in) {
        System.out.print("W jaki sposob osoba ma przygotowywac szarlotke? c - cwiartki, k - kostka");
        String answer = in.next();
        switch (answer) {
            case "c" -> {
                osobaPieczaca.ustawSzarlotke(new Cwiartki());
                osobaPieczaca.setAlgorytmInfoJablko("Cwiartki");
            }
            case "k" -> {
                osobaPieczaca.ustawSzarlotke(new Kostka());
                osobaPieczaca.setAlgorytmInfoJablko("Kostka");
            }
        }
    }
    public static void zmienSposobCiastoSliwkowe(Scanner in) {
        System.out.print("W jaki sposob osoba ma przygotowywac ciasto sliwkowe? p - polowki, z - zmiksowane");
        String answer = in.next();
        switch (answer) {
            case "p" -> {
                osobaPieczaca.ustawCiastoSliwkowe(new Polowki());
                osobaPieczaca.setAlgorytmInfoSliwka("Polowki");
            }
            case "z" -> {
                osobaPieczaca.ustawCiastoSliwkowe(new Zmiksowane());
                osobaPieczaca.setAlgorytmInfoSliwka("Zmiksowane");
            }
        }
    }
    public static void main(String[] args) {
        pieczeBabcia = new PieczeBabcia();
        pieczeBabcia.wyswietlInfo();
        osobaPieczaca = pieczeBabcia;

        pieczeDziadek = new PieczeDziadek();
        pieczeDziadek.wyswietlInfo();

        Scanner in = new Scanner(System.in);

        while(true) {
            System.out.println("Wybierz operacje: ");
            System.out.println("1. Wybierz osobe pieczącą - " + osobaPieczaca.getClass().getSimpleName());
            System.out.println("2. Niech osoba upiecze szarlotke");
            System.out.println("3. Niech osoba upiecze ciasto śliwkowe");
            System.out.println("4. Wybierz sposób przygotowania szarlotki - " + osobaPieczaca.getAlgorytmInfoJablko());
            System.out.println("5. Wybierz sposób przygotowania ciasta śliwkowego - " + osobaPieczaca.getAlgorytmInfoSliwka());
            System.out.println("6. Wyjdz");

            int answer = in.nextInt();

            switch (answer) {
                case 1 -> zmienOsobaPieczaca(in);
                case 2 -> {
                    String szarlotka = osobaPieczaca.wykonajSzarlotke();
                    System.out.println(szarlotka);
                }
                case 3 -> {
                    String ciastoSliwkowe = osobaPieczaca.wykonajCiastoSliwkowe();
                    System.out.println(ciastoSliwkowe);
                }
                case 4 -> zmienSposobSzarlotka(in);
                case 5 -> zmienSposobCiastoSliwkowe(in);
                case 6 -> System.exit(0);
            }
        }
    }
}