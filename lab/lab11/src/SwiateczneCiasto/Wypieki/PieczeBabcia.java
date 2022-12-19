package SwiateczneCiasto.Wypieki;

import SwiateczneCiasto.AlgorytmyPrzygotowaniaJablek.Kostka;
import SwiateczneCiasto.AlgorytmyPrzygotowaniaSliwek.Zmiksowane;
import SwiateczneCiasto.Wypieki.Ciasto;

public class PieczeBabcia extends Ciasto {
    public PieczeBabcia() {
        this.ustawSzarlotke(new Kostka());
        this.setAlgorytmInfoJablko("Kostka");

        this.ustawCiastoSliwkowe(new Zmiksowane());
        this.setAlgorytmInfoSliwka("Zmiksowane");

    }
    public void wyswietlInfo() {
        System.out.println("Piecze Babcia: szarlotka: " + this.getAlgorytmInfoJablko() + " ciasto jablkowe: " + this.getAlgorytmInfoSliwka());
    }
}
