package Figury;

public class Okrag extends Figura {
    int r,x,y;

    public Okrag() {
        super("Bazowy");
        this.r = 1;
        this.x = 0;
        this.y = 0;
    }

    public Okrag(String nazwa, int r, int x, int y) {
        super(nazwa);
        this.r = r;
        this.x = x;
        this.y = y;
    }

    public double Obwod() {
        return 2 * Math.PI * r;
    }
    public double Pole() {
        return Math.PI * r * r;
    }
    public String toString() {
        return String.format("Okrag: %s [r=%d, x=%d, y=%d]", nazwa, r , x , y);
    }
    public boolean nalezy(int x, int y) {
        // (x-a)^2 + (y+b)^2 < r^2
        return (Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2)) < this.r*this.r;
    }
}
