public abstract class KartaPracownika {
    protected int numer;
    protected String nazwisko;

    public KartaPracownika() {
        this.numer = 0;
        this.nazwisko = null;
    }

    public KartaPracownika(int numer, String nazwisko) {
        this.numer = numer;
        this.nazwisko = nazwisko;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " nr wyplaty " + numer + " " + nazwisko;
    }

    public abstract float premia();

    public int getNumer() {
        return numer;
    }

    public String getNazwisko() {
        return nazwisko;
    }
}
