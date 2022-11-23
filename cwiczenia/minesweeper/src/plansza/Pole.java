package plansza;
import plansza.Plansza;


public class Pole {
    private boolean isbomb;
    private Stan state;
    private int liczba_bomb;



    public Pole() {
        this.isbomb = false;
        this.state = Stan.Zakryta;
        this.liczba_bomb = 0;
    }

    public Pole(boolean isbomb, Stan state, int liczba_bomb) {
        this.isbomb = isbomb;
        this.state = state;
        this.liczba_bomb = liczba_bomb;
    }

    public boolean getBomb() {
        return isbomb;
    }
    public int getLiczba_bomb() {
        return liczba_bomb;
    }
    public Stan getState() {
        return state;
    }
    public void setBomb(boolean isbomb) {
        this.isbomb = isbomb;
    }
    public void setState(Stan state)
    {
        this.state = state;
    }
    public void setLiczba_bomb(int liczba_bomb){
        this.liczba_bomb = liczba_bomb;
    }





}