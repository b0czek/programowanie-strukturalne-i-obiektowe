package Przychodnia;

import java.util.Random;

public class Przychodnia {
    private static Osoba[] osoby = new Osoba[20];
    private static int n = 0;

    public static String[] nazwiska = new String[]{"Nowak", "Kowalski", "Wiśniewska", "Kamińska", "Kozłowska", "Mazur", "Zając", "Król", "Kaczmarek", "Wieczorek", "Walczak", "Baran", "Jankowski", "Wójcik", "Kowalczyk", "Wisniewski"};
    public static String[] specjalizacje = new String[] {"Internista", "Pediatra", "Neurolog"};
    public static String genPesel() {
        Random gen = new Random();
        return Long.toString(gen.nextLong(10000000000L) + 90000000000L);
    }

    public static void main(String[] args){
        osoby[n++] = new Pacjent();
        for(int i = 0; i < 10; i ++, n++) {
            osoby[n] = new Pacjent(nazwiska[i], genPesel(), Math.max(i-5, 2));
        }

        osoby[n++] = new Lekarz();
        for(int i = 0 ; i < 3; i++, n++) {
            osoby[n] = new Lekarz(nazwiska[n], genPesel(), specjalizacje[i],(i+1)* 100);
        }

        for(int i = 0; i < n; i++) {
            System.out.println(osoby[i].toString());
        }
        System.out.println();

        for(int i = 0 ; i < n; i++) {
            if(osoby[i] instanceof Lekarz) {
                System.out.println(osoby[i]);
                ((Lekarz) osoby[i]).wyswietlLiczbePacjentow();
            }
        }
        System.out.println();

        for(int i = 0 ; i < n; i++) {
            if(osoby[i] instanceof Pacjent) {
                System.out.println(osoby[i]);
                ((Pacjent) osoby[i]).wyswietlWizyty();
            }
        }
        System.out.println();


        int internisci = 0;
        for(int i = 0 ; i < n; i++) {
            if(osoby[i] instanceof Lekarz) {
                if(((Lekarz) osoby[i]).jestInternista()) {
                    internisci++;
                }
            }
        }
        System.out.println("liczba lekarzy internistow w przychodni:" + internisci);
        System.out.println();

        int m = 0;
        for(int i = 0 ; i < n; i++) {
            if(!(osoby[m] instanceof Lekarz)) {
                m = i;
            }
            if(osoby[i] instanceof Lekarz) {
                if(((Lekarz) osoby[i]).Oblicz() > ((Lekarz) osoby[m]).Oblicz()) {
                    m = i;
                }
            }
        }
        System.out.println("lekarz ktory najwiecej zarobil: " + osoby[m]);

    }
}
