public abstract class Bryla {
    private String kolor;
    private int h;

    public Bryla(String kolor, int h) {
        this.kolor = kolor;
        this.h = h;
    }

    public String getKolor() {
        return kolor;
    }

    public int getH() {
        return h;
    }

    abstract double Objetosc();
}
