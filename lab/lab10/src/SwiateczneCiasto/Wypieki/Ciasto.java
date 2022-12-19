package SwiateczneCiasto.Wypieki;

import SwiateczneCiasto.AlgorytmyPrzygotowaniaJablek.Jablko;
import SwiateczneCiasto.AlgorytmyPrzygotowaniaSliwek.Sliwka;

public abstract class Ciasto {

    protected String algorytmInfoJablko;
    protected Jablko szarlotka;
    protected String algorytmInfoSliwka;
    protected Sliwka ciastoSliwkowe;


    public String wykonajSzarlotke() {
        return "Szarlotka z jablkami: " + szarlotka.przygotowanieJablka();
    }
    public void ustawSzarlotke(Jablko szarlotka) {
        this.szarlotka = szarlotka;
    }

    public String wykonajCiastoSliwkowe() {
        return "Ciasto Sliwkowe z sliwkami: " + ciastoSliwkowe.przygotowanieSliwki();
    }
    public void ustawCiastoSliwkowe(Sliwka ciastoSliwkowe) {
        this.ciastoSliwkowe = ciastoSliwkowe;
    }



    public String getAlgorytmInfoJablko() {
        return algorytmInfoJablko;
    }

    public void setAlgorytmInfoJablko(String algorytmInfoJablko) {
        this.algorytmInfoJablko = algorytmInfoJablko;
    }

    public String getAlgorytmInfoSliwka() {
        return algorytmInfoSliwka;
    }

    public void setAlgorytmInfoSliwka(String algorytmInfoSliwka) {
        this.algorytmInfoSliwka = algorytmInfoSliwka;
    }

}
