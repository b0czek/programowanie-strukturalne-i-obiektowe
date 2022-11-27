package Przychodnia;

public abstract class Osoba {
    protected String nazwisko, pesel;

    public Osoba() {
        this.nazwisko = "";
        this.pesel = "";

    }

    public Osoba(String nazwisko, String pesel) {
        this.nazwisko = nazwisko;
        this.pesel = pesel;
    }
    public String toString() {
        return this.nazwisko + " " + this.pesel;
    }
    public abstract double Oblicz();


}
