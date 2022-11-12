
public class Latarka {
    private int stan;
    private int kolor;
    public Latarka() {
        this.stan = 0;
        this.kolor = 0;

    }

    public void Wlacz() {
        this.stan = 1;
        this.kolor = 0;
    }
    public void Wylacz() {
        this.stan = 0;
    }
    public void ZmienKolor(int kolor) {
        this.kolor = kolor;
    }
    public void Drukuj() {
        if(this.stan == 1) {
            String nazwy_kolorow[] = {"bialym", "zielonym", "czerwonym"};

            System.out.println("Latarka jest wlaczona i swieci swiatlem " + nazwy_kolorow[this.kolor]);
        }
        else {
            System.out.println("Latarka jest wylaczona");

        }
    }

}
