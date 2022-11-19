import Figury.Figura;
import Figury.Okrag;
import Figury.Prostokat;

import java.util.Scanner;

public class Rysunki {
    static Figura[] figury = new Figura[20];
    static int n = 0;

    public static void main(String[] args) {

        // wstaw do tablicy kilka okręgów oraz kilka prostokątów wykorzystując każdy z konstruktorów
        figury[n++] = new Prostokat();
        for(int i = 1; i <= 5; i ++) {
            figury[n++] = new Prostokat("p" + i,i * 3, i * 2);
        }

        figury[n++] = new Okrag();
        for(int i = 1; i <= 5; i ++) {
            figury[n++] = new Okrag("o" + i, i+3, i*2, i*3);
        }
        // sprawdź, która z figur ma największe pole
        int maxAreaIdx = 0;
        for(int i = 0; i < n; i ++) {
            if(figury[i].Pole() > figury[maxAreaIdx].Pole()) {
                maxAreaIdx = i;
            }
        }
        Figura m = figury[maxAreaIdx];
        // wyświetl informację czy jest to okrąg czy prostokąt wraz z
        // numerem pozycji, na której znajduje się ona w tablicy;
        System.out.printf("Najwieksze pole ma %s, %f - index %d\n", m.getClass().getSimpleName(), m.Pole(), maxAreaIdx);

        // wyświetl wszystkie figury w kolejności występowania w tablicy (wykorzystaj metodę toString());
        for(int i = 0 ; i < n; i ++) {
            System.out.println(figury[i].toString());
        }
        System.out.println();

        // wyświetl same okręgi
        for(int i = 0 ; i < n; i ++) {
            if(figury[i] instanceof Okrag) {
                System.out.println(figury[i].toString());
            }
        }
        System.out.println();

        // wyświetl same prostokaty
        for(int i = 0 ; i < n; i ++) {
            if(figury[i] instanceof Prostokat) {
                System.out.println(figury[i].toString());
            }
        }
        System.out.println();

        // policz ile jest kwadratów
        int p = 0;
        for(int i = 0 ; i < n; i ++) {
            if(figury[i] instanceof Prostokat && ((Prostokat) figury[i]).jestKwadratem()) {
                p++;
            }
        }
        System.out.println("Liczba kwadratow: " + p);

        // podaj współrzędne punktu i policz do ilu okręgów on należy
        Scanner scanner = new Scanner(System.in);
        System.out.print("x: ");
        int x = scanner.nextInt();
        System.out.print("y: ");
        int y = scanner.nextInt();

        int c = 0;
        for(int i = 0 ; i < n; i ++) {
            if(figury[i] instanceof Okrag && ((Okrag) figury[i]).nalezy(x, y)) {
                c++;
            }
        }
        System.out.printf("Punkt [%d, %d] nalezy do %d okregow", x ,y , c);
    }

}
