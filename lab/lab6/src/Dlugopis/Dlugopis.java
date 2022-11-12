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


    public boolean getStan() {
        return this.jestwPiorniku;
    }
    public void wyjmijzPiornika() {
        this.jestwPiorniku = false;
    }
    public void wlozdoPiornika() {
        this.jestwPiorniku = true;
    }
    public Wklad getWklad() {
        return this.wklad;
    }

}
