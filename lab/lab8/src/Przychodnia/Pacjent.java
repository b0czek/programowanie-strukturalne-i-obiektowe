package Przychodnia;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Pacjent extends Osoba {
    private int n;
    private Wizyta[] wizyty;

    public Pacjent() {
        super();
        this.n = 0;
        this.wizyty = null;
    }
    public Pacjent(String nazwisko, String pesel, int n) {
        super( nazwisko, pesel);
        this.n = n;
        this.wizyty = new Wizyta[n];

        for(int i = 0; i < n; i++) {
            wizyty[i] = new Wizyta(i + 10000 - n*i*241, ((new Random()).nextInt(3)+1)*100, i %2 ==0, (n-i)%2==0);
        }


    }

    public double Oblicz() {
        return IntStream.range(0, this.n).map(i -> wizyty[i].getCenaWizyty()).sum();
    }

    @Override
    public String toString() {
        return String.format("Pacjent: %s %s [n=%s]", this.nazwisko, this.pesel, this.n);
    }
    public void wyswietlWizyty() {
        if(this.wizyty != null)
            IntStream.range(0, this.n).forEach(i -> System.out.println(wizyty[i]));
    }
}
