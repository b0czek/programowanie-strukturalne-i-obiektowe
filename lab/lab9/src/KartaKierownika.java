public class KartaKierownika extends KartaPracownika {
    public KartaKierownika() { super(); }
    public KartaKierownika(int numer, String nazwisko) {super(numer, nazwisko);}
    public float premia() {
        return 0.5f;
    }
}
