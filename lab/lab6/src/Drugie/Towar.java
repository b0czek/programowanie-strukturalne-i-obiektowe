package Drugie;

public class Towar {
    private int kod_towaru;
    private double cena;

    public Towar() {
        this.kod_towaru = 1234567890;
        this.cena = 1.0;
    }
    public Towar(int kod_towaru, double cena) {
        this.kod_towaru = kod_towaru;
        this.cena = cena;
    }

    public int getKodTowaru() {
        return this.kod_towaru;
    }

    public void setKodTowaru(int kod_towaru) {
        this.kod_towaru = kod_towaru;
    }

    public double getCena() {
        return this.cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String toString() {
        return "Towar - kod: " + kod_towaru + ", cena: " + cena;
    }
}
