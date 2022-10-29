public class pierwsze {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Latarka latarka = new Latarka();
        latarka.Drukuj();
        latarka.Wlacz();
        latarka.Drukuj();
        latarka.ZmienKolor(2);
        latarka.Drukuj();
        latarka.Wylacz();
        latarka.Drukuj();
    }
}