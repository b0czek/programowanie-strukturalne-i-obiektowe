import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Firma {
    public static Wyplata[] wyplaty = new Wyplata[100];
    private static final String[] nazwiska = new String[]{"Nowak", "Kowalski", "Wiśniewska", "Kamińska", "Kozłowska", "Mazur", "Zając", "Król", "Kaczmarek", "Wieczorek", "Walczak", "Baran", "Jankowski", "Wójcik", "Kowalczyk", "Wisniewski"};
    private static int n = 0;
    public static void main(String[] args) throws IOException {

        wyplaty[n++] = new Wyplata(  15000, new KartaDyrektora(n, nazwiska[n]));
        for(int i = 0; i < 3; i ++, n++) {
            wyplaty[n] = new Wyplata(8000, new KartaKierownika(n, nazwiska[n]));
        }
        for(int i = 0; i < 10; i ++, n++) {
            wyplaty[n] = new Wyplata(5000 + ((i % 2 == 0) ? -1 : 1) * i*100, new KartaPersonelu(n, nazwiska[n]));
        }

        IntStream.range(0, n).forEach(i -> System.out.println(wyplaty[i]));

        wyplaty[n++] = new Wyplata(5000, new KartaPersonelu(n, nazwiska[n]));

        long c = IntStream.range(0,n).filter(i -> wyplaty[i].getKarta() instanceof KartaKierownika).count();
        System.out.println("wyplat z karta kierownika: " + c);

        Wyplata najnizsza = IntStream
                .range(0,n)
                .mapToObj(i -> wyplaty[i])
                .filter(w -> w.getKarta() instanceof KartaPersonelu)
                .min(Comparator.comparingDouble(Wyplata::pensjaZPremia))
                .get();
        System.out.println("pracownik z karta personelu z najnizsza wyplata: " + najnizsza);


        BufferedWriter writer = new BufferedWriter(new FileWriter("wyplaty.txt"));
        String output = IntStream.range(0, n)
                .mapToObj(i -> wyplaty[i])
                .filter(wyplata -> wyplata.getKarta() instanceof KartaPersonelu)
                .map(wyplata -> {
                    KartaPersonelu karta = (KartaPersonelu) wyplata.getKarta();
                    return String.format("pensja: %.2f, Karta: numer %d: nazwisko %s - premia %d%s", wyplata.getPensja(), karta.getNumer(), karta.getNazwisko(), (int)(karta.premia() * 100), "%");
        }).collect(Collectors.joining("\n"));

        writer.write(output);
        writer.close();

    }
}