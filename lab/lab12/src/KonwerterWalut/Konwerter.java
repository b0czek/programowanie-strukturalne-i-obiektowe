package KonwerterWalut;

public class Konwerter {
    private static final double przelicznik = 4.29;

    private static double roundTo2Decimal(double d1) {
        return Math.round(d1* 100) / 100d;
    }

    public static double EuroToPLN(double euro) {
        return roundTo2Decimal(euro * przelicznik);
    }

    public static double PLNToEuro(double pln) {
        return roundTo2Decimal(pln / przelicznik);
    }
}
