package plansza;


public class Pole {
    private int m,n;
    private boolean isBomb;
    private Stan state;
    private int nearBombsCount;



    public Pole(int m, int n) {
        this.m = m;
        this.n = n;
        this.isBomb = false;
        this.state = Stan.Zakryta;
        this.nearBombsCount = 0;
    }

//    public Pole(boolean isBomb, Stan state, int nearBombsCount) {
//        this.isBomb = isBomb;
//        this.state = state;
//        this.nearBombsCount = nearBombsCount;
//    }

    public void setIsBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }

    public boolean getIsBomb() {
        return isBomb;
    }

    public void setState(Stan state)
    {
        this.state = state;
    }
    public Stan getState() {
        return state;
    }

    public void setNearBombsCount(int nearBombsCount){
        this.nearBombsCount = nearBombsCount;
    }

    public int getNearBombsCount() {
        return nearBombsCount;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }
}