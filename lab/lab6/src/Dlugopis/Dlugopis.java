package Dlugopis;

public class Dlugopis {
    private Wklad wklad;
    private boolean jestwPiorniku;

    public Dlugopis() {
        this.wklad = new Wklad();
        this.jestwPiorniku = true;
    }

    public Dlugopis(int kolor) {
        this.wklad = new Wklad(kolor);
        this.jestwPiorniku = true;
    }
    public String getStan() {
        return "Dlugopis - kolor: " + this.wklad.getKolor() + ", jestwPiorniku: " + this.jestwPiorniku;
    }

    public boolean getJestwPiorniku() {
        return this.jestwPiorniku;
    }
    public void setJestwPiorniku(boolean stan) {
        this.jestwPiorniku = stan;
    }

    public void wyjmijzPiornika() {
        this.setJestwPiorniku(false);
    }
    public void wlozdoPiornika() {
        this.setJestwPiorniku(true);
    }
    public Wklad getWklad() {
        return this.wklad;
    }
    public void setWklad(Wklad wklad) { this.wklad = wklad;}
}
