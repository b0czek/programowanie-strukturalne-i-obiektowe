import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.IntStream;

public class BrylyGeometryczne {
    private ArrayList<Bryla> bryly;

    private Comparator<Bryla> colorComparator;
    private Comparator<Bryla> heightComparator;
    private Comparator<Bryla> colorHeightComparator;

    public BrylyGeometryczne() {
        this.bryly = new ArrayList<>();
    }

    private class ColorComparator implements Comparator<Bryla> {

        @Override
        public int compare(Bryla bryla, Bryla t1) {
            return String.CASE_INSENSITIVE_ORDER.compare(bryla.getKolor(), t1.getKolor());
        }
    }
    private class HeightComparator implements Comparator<Bryla> {

        @Override
        public int compare(Bryla bryla, Bryla t1) {
            return Integer.compare(bryla.getH(), t1.getH());
        }
    }
    private class ColorHeightComparator implements Comparator<Bryla> {

        @Override
        public int compare(Bryla bryla, Bryla t1) {
            int c = String.CASE_INSENSITIVE_ORDER.compare(bryla.getKolor(), t1.getKolor());
            if(c == 0) {
                return Integer.compare(bryla.getH(), t1.getH());
            }
            return c;
        }
    }

    public void dodajBryle(Bryla bryla) {
        bryly.add(bryla);
    }

    public void dodajWalec(String kolor, int h, int r) {
        dodajBryle(new Walec(kolor, h ,r));
    }
    public void dodajProstopadloscian(String kolor, int h, int a, int b) {
        dodajBryle(new Prostopadloscian(kolor, h, a, b));
    }

    public void wyswietlBryly() {
        bryly.forEach(System.out::println);
    }
    public void createComparators() {
        this.colorComparator = new ColorComparator();
        this.heightComparator = new HeightComparator();
        this.colorHeightComparator = new ColorHeightComparator();
    }
    public void sortByColor () {
        this.bryly.sort(this.colorComparator);
    }

    public void sortByHeight () {
        this.bryly.sort(this.heightComparator);
    }
    public void sortByColorHeight() {
        this.bryly.sort(this.colorHeightComparator);
    }

    public void printMaxV() {
        int idx = 0;
        double vol = 0;
        for(int i = 0 ; i < bryly.size(); i ++) {
            double v = bryly.get(i).Objetosc();
            if(v > vol) {
                vol = v;
                idx = i;
            }
        }
        System.out.println("max objetosc: " +vol + " idx - " + idx);
    }
    private static Scanner in = new Scanner(System.in);
    private static BrylyGeometryczne brylyGeometryczne;
    private static void walecWizard() {
        System.out.print("r: ");
        int r = in.nextInt();
        System.out.print("h: ");
        int h = in.nextInt();
        System.out.print("kolor: ");
        String kolor = in.next();
        brylyGeometryczne.dodajWalec(kolor, h, r);
    }

    private static void prostopadloscianWizard() {
        System.out.print("a: ");
        int a = in.nextInt();
        System.out.print("b: ");
        int b = in.nextInt();
        System.out.print("h: ");
        int h = in.nextInt();
        System.out.print("kolor: ");
        String kolor = in.next();
        brylyGeometryczne.dodajProstopadloscian(kolor, h, a, b);
    }

    public static void main(String[] args){
        brylyGeometryczne = new BrylyGeometryczne();
        brylyGeometryczne.createComparators();

        String[] prompts = new String[] {"Dodaj walec", "Dodaj prostopadloscian", "Sortuj bryly po kolorze", "Sortuj bryly po wysokosci", "Sortuj bryly po kolorze i wysokosci", "Wyswietl bryly", "Wyswietl bryle o najwiekszej objetosci", "Wyjdz"};
        Runnable[] runnables = new Runnable[] { BrylyGeometryczne::walecWizard, BrylyGeometryczne::prostopadloscianWizard, brylyGeometryczne::sortByColor, brylyGeometryczne::sortByHeight, brylyGeometryczne::sortByColorHeight, brylyGeometryczne::wyswietlBryly, brylyGeometryczne::printMaxV, () -> System.exit(0) };
        while(true) {
            IntStream.range(0, prompts.length).forEach(i -> System.out.println((i+1) + ". " + prompts[i]));
            int c = in.nextInt();
            runnables[c-1].run();
        }

    }


}
