public class Wyplata {
    private static int numerWyplaty = 0;
    private double pensja;
    private KartaPracownika karta;

    public Wyplata() {
        numerWyplaty++;
        this.pensja = 0;
        this.karta = null;
    }

    public Wyplata(double pensja, KartaPracownika karta) {
        numerWyplaty++;
        this.pensja = pensja;
        this.karta = karta;
    }

    public String toString() {
        return "pensja: " + pensja + " karta: " + this.karta.getClass().getSimpleName();
    }

    public double pensjaZPremia() {
        return this.pensja + this.karta.premia() * this.pensja;
    }

    public KartaPracownika getKarta() {
        return karta;
    }

    public int getNumerWyplaty() {
        return numerWyplaty;
    }

    public double getPensja() {
        return pensja;
    }
}
