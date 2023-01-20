public class Prostopadloscian extends Bryla {
    private int a,b;
    private boolean jestSzescianem;

    public Prostopadloscian(String kolor, int h, int a, int b) {
        super(kolor, h);
        this.a = a;
        this.b = b;
        if(a == b && b == h) {
            jestSzescianem = true;
        }
    }

    @Override
    double Objetosc() {
        return a*b*getH();
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public boolean isJestSzescianem() {
        return jestSzescianem;
    }

    @Override
    public String toString() {
        return String.format("Prostopadloscian: a - %d, b - %d, h - %d, kolor: %s", a ,b ,getH(), getKolor());
    }
}
