import java.util.Arrays;

public class Main {

    static int[] ciag(int n, int start, int krok, int mnoznik) {
        int elementy[] = new int[n];

        int val = start;
        for(int i = 1; i <=n ; i++) {
            elementy[i - 1] = (int) * val;
            val+= krok;

        }

        return elementy;
    }

    static int[] licznik(int n) {
        return ciag(n, 2, 3, -1);
    }

    static int[] mianownik(int n) {
        return ciag(n, 3, 2, 1);
    }

    static double wyrazenie(int n) {
        int elementy_licznika[] = licznik(n);
        System.out.println("Liczby w ciagu licznika: " + Arrays.toString(elementy_licznika));
        double suma_licznika = Arrays.stream(elementy_licznika).sum();

        int elementy_mianownika[] = mianownik(n);
        System.out.println("Liczby w ciagu mianownika: " + Arrays.toString(elementy_mianownika));
        double produkt_mianownika = Arrays.stream(elementy_mianownika).reduce(1, (i,j) -> i*j);

        return suma_licznika / produkt_mianownika;
    }

    public static void main(String[] args) {
        int n = 5;
        int m = 99;
        System.out.printf("Liczby z przedzialu [%d, %d] podzielne przez 3: ", n , m);
        int reszta = n % 3 == 0 ? 0 : 3 - n % 3;
        for(int i = n + reszta; i <= m; i+=3) {
            System.out.print(" " + i);
        }
        System.out.println();


        n = 5;
        System.out.println("Wynik wyrazenia: " + wyrazenie(n));

    }
}