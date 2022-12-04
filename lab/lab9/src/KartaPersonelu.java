public class KartaPersonelu extends KartaPracownika {
    public KartaPersonelu() { super(); }
    public KartaPersonelu(int numer, String nazwisko) {super(numer, nazwisko);}
    public float premia() {
        return 0.2f;
    }
}
