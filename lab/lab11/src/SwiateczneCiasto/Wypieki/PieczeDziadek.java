package SwiateczneCiasto.Wypieki;

import SwiateczneCiasto.AlgorytmyPrzygotowaniaJablek.Cwiartki;
import SwiateczneCiasto.AlgorytmyPrzygotowaniaSliwek.Polowki;
import SwiateczneCiasto.Wypieki.Ciasto;

public class PieczeDziadek extends Ciasto {
    public PieczeDziadek() {
        this.ustawSzarlotke(new Cwiartki());
        this.setAlgorytmInfoJablko("Cwiartki");

        this.ustawCiastoSliwkowe(new Polowki());
        this.setAlgorytmInfoSliwka("Polowki");

    }

    public void wyswietlInfo() {
        System.out.println("Piecze Dziadek: szarlotka: " + this.getAlgorytmInfoJablko() + " ciasto jablkowe: " + this.getAlgorytmInfoSliwka());
    }
}
