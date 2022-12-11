import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Firma {
    public static Wyplata[] wyplaty = new Wyplata[100];
    private static final String[] nazwiska = new String[]{"Nowak", "Kowalski", "Wiśniewska", "Kamińska", "Kozłowska", "Mazur", "Zając", "Król", "Kaczmarek", "Wieczorek", "Walczak", "Baran", "Jankowski", "Wójcik", "Kowalczyk", "Wisniewski"};

    private static void dodajWyplate(int pensja, KartaPracownika kartaPracownika) {
        wyplaty[Wyplata.getNumerWyplaty()] = new Wyplata(pensja, kartaPracownika);
    }

    private static void dodajJednaWyplate(Scanner scanner) {
        System.out.print("Pensja: ");
        int pensja = scanner.nextInt();
        System.out.print("Nazwisko: ");
        String nazwisko = scanner.next();
        
        KartaPracownika karta = null;
        do {

            System.out.print("Rodzaj karty (k - Kierownika, p - Personelu, d - Dyrektora): ");
            String k = scanner.next();

            switch(k) {
                case "k":
                    karta = new KartaKierownika(Wyplata.getNumerWyplaty(), nazwisko);
                    break;
                case "p":
                    karta = new KartaPersonelu(Wyplata.getNumerWyplaty(), nazwisko);
                    break;
                case "d":
                    karta = new KartaDyrektora(Wyplata.getNumerWyplaty(), nazwisko);
                    break;
                default:
                    System.out.println("nieprawidlowa karta");
                    break;

            }
        }
        while(karta == null);

        dodajWyplate(pensja, karta);
    }

    private static void wyswietlWyplaty() {
        IntStream.range(0, Wyplata.getNumerWyplaty()).forEach(i -> System.out.println(wyplaty[i]));

    }

    private static long wyplatZKartaKierownika() {
        return IntStream
                .range(0,Wyplata.getNumerWyplaty()).
                filter(i -> wyplaty[i].getKarta() instanceof KartaKierownika)
                .count();
    }

    private static Wyplata personelZNajnizszaWyplata() {
        return IntStream
                .range(0,Wyplata.getNumerWyplaty())
                .mapToObj(i -> wyplaty[i])
                .filter(w -> w.getKarta() instanceof KartaPersonelu)
                .min(Comparator.comparingDouble(Wyplata::pensjaZPremia))
                .orElseGet(() -> null);
    }

    private static boolean zapiszWyplaty() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("wyplaty.txt"))) {
            String output = IntStream.range(0, Wyplata.getNumerWyplaty())
                    .mapToObj(i -> wyplaty[i])
                    .filter(wyplata -> wyplata.getKarta() instanceof KartaPersonelu)
                    .map(wyplata -> {
                        KartaPersonelu karta = (KartaPersonelu) wyplata.getKarta();
                        return String.format("pensja: %.2f, Karta: numer %d: nazwisko %s - premia %d%s", wyplata.getPensja(), karta.getNumer(), karta.getNazwisko(), (int)(karta.premia() * 100), "%");
                    }).collect(Collectors.joining("\n"));

            writer.write(output);
            return true;
        }
        catch(IOException e) {
            return false;
        }
    }

    private static void stworzTablice() {
        dodajWyplate(  15000, new KartaDyrektora(Wyplata.getNumerWyplaty(), nazwiska[Wyplata.getNumerWyplaty()]));
        for(int i = 0; i < 3; i ++) {
            dodajWyplate(8000, new KartaKierownika(Wyplata.getNumerWyplaty(), nazwiska[Wyplata.getNumerWyplaty()]));
        }
        for(int i = 0; i < 10; i ++) {
            dodajWyplate(5000 + ((i % 2 == 0) ? -1 : 1) * i * 100, new KartaPersonelu(Wyplata.getNumerWyplaty(), nazwiska[Wyplata.getNumerWyplaty()]));

        }
    }

    public static void main(String[] args)  {

        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Wybierz następną operację");
            System.out.println("1.Wyswietlenie utworzonych wyplat");
            System.out.println("2.Dodanie jednej wyplaty");
            System.out.println("3.Wyswietlenie ile bylo wyplat z karta kierownika");
            System.out.println("4.Wyswietlenie personelu z najnizsza wyplata");
            System.out.println("5.Zapisanie wyplat personelu do pliku");
            System.out.println("6. Stworz tablice");
            System.out.println("7.Wyjdz");
            switch(in.nextInt()) {
                case 1:
                    wyswietlWyplaty();
                    break;
                case 2:
                    dodajJednaWyplate(in);
                    break;
                case 3:
                    System.out.println(wyplatZKartaKierownika());
                    break;
                case 4:
                    System.out.println(personelZNajnizszaWyplata());
                    break;
                case 5:
                    if(zapiszWyplaty()) {
                        System.out.println("zapisano pomyslnie");
                    }
                    else {
                        System.out.println("nie udalo sie zapisac");
                    }
                    break;
                case 6:
                    stworzTablice();
                    break;
                case 7:
                    System.exit(0);
                    break;

            }


        }

    }
}