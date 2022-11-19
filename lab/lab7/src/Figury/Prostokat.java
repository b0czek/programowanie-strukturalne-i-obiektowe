package Figury;

public class Prostokat extends Figura {
    private int a,b;

    public Prostokat() {
        super("Bazowy");
        this.a = 1;
        this.b = 1;
    }
    public Prostokat(String nazwa, int a, int b) {
        super(nazwa);
        this.a = a;
        this.b = b;
    }

    public double Obwod() {
        return 2*a+2*b;
    }
    public double Pole() {
        return a *b;
    }
    public String toString() {
        return String.format("Prostokat: %s [a=%d, b=%d]", nazwa, a ,b);
    }
    public boolean jestKwadratem() {
        return a == b;
    }
}
