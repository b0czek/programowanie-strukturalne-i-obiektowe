public class Walec extends Bryla {
    private int r;

    public Walec(String kolor, int h, int r) {
        super(kolor, h);
        this.r = r;
    }

    public int getR() {
        return r;
    }

    @Override
    double Objetosc() {
        return Math.PI * r * r * getH();
    }

    @Override
    public String toString() {
        return String.format("Walec: r - %d, h - %d, kolor: %s", r ,getH(), getKolor());

    }
}
