package Przychodnia;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lekarz extends Osoba {
    private String s;
    private int stawka;
    private int[] t;

    public Lekarz() {
        super();
        this.s = "brak";
        this.stawka = 0;
        this.t = null;
    }
    public Lekarz(String nazwisko, String pesel, String spec, int stawka) {
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.s = spec;
        this.stawka = stawka;
        this.t = genT();

    }

    public double Oblicz() {
        return this.stawka * Arrays.stream(this.t).sum();
    }

    public String toString() {
        return String.format("Lekarz: %s %s [specjalizacja=%s, stawka=%d]", nazwisko, pesel, s, stawka);
    }
    public void wyswietlLiczbePacjentow() {

        String s = IntStream.range(0, 5)
                .mapToObj(num -> String.format("%d: %d", num, this.t == null ? 0 : t[num]))
                .collect(Collectors.joining(", "));
        System.out.println(s);
    }

    public boolean jestInternista() {
        return this.s.equals("Internista");
    }

    public int getStawka() {
        return stawka;
    }


    private int[] genT() {
        int[] t = new int[5];
        Random generator = new Random();
        for(int i = 0; i < 5; i++) {
            t[i] = generator.nextInt(11) + 20;
        }

        return t;
    }
}
