package Przychodnia;

public class Wizyta {
    private int kodChoroby;



    private int cenaWizyty;
    private boolean wystawionoZwolnienie, wystawionoRecepte;

    public Wizyta() {
        this.kodChoroby = 0;
        this.cenaWizyty = 0;
        this.wystawionoZwolnienie = false;
        this.wystawionoRecepte = false;
    }
    public Wizyta(int kod, int cena, boolean zwolnienie, boolean recepta) {
        this.kodChoroby = kod;
        this.cenaWizyty = cena;
        this.wystawionoZwolnienie = zwolnienie;
        this.wystawionoRecepte = recepta;
    }

    public String toString() {
        return "Wizyta{" +
                "kodChoroby=" + kodChoroby +
                ", cenaWizyty=" + cenaWizyty +
                ", wystawionoZwolnienie=" + wystawionoZwolnienie +
                ", wystawionoRecepte=" + wystawionoRecepte +
                '}';
    }
    public int getCenaWizyty() {
        return cenaWizyty;
    }
}
