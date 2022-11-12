package GraWZycie;

import Plansza.Plansza;

// grupa - Natalia Marszałek, Dariusz Majnert

public class Main {
    // n,m rozmiar tablicy
    // żywa, martwa komórka w danej turze i ma 2 lub 3 sasiadow zywych
    // moze powstac zycie ,gdy sa trojkaty jezeli martwa komorka ma 3 zywych sasiadow to zyje
    // klasa plasnsza, przyjmuje argumenty ,metoda losowego rozmieszczania komorek zywych i martwych
    public static void main(String[] args) {
        Plansza p = new Plansza(10,9);
        p.init();
        for(int i = 0 ; i < 20 ; i ++) {
            p.Drukowanie();
            p.tick();
        }

    }
}