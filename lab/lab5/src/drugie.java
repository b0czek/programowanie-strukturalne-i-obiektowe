import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class drugie {

    public static int[] createArray(int n) {
        Random generator = new Random();
        return IntStream.range(0,n).map(i -> generator.nextInt(100)).toArray();
    }

    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));

    }

    public static int maxArray(int[] arr) {
        return Arrays.stream(arr).max().getAsInt();
    }

    public static double avgArray(int[] arr) {
        return Arrays.stream(arr).average().getAsDouble();
    }

    public static long evenCountArray(int[] arr) {
        return Arrays.stream(arr).filter(i -> i % 2 == 0).count();
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        System.out.print("Podaj liczbe n: ");
        int n = in.nextInt();
        int arr[] = createArray(n);
        printArray(arr);
        System.out.println(maxArray(arr));
        System.out.println(avgArray(arr));
        System.out.println(evenCountArray(arr));

    }
}
