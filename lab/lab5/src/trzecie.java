import java.util.Scanner;

public class trzecie {
    public static int[][] createArray(int n) {
        int[][] a = new int[n][];
        for(int i = 0; i < n; i++) {
            a[i] = new int[n - i];
        }
        return a;
    }
    public static void fillArray(int[][] arr) {
        int counter = 1;
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0 ; j < arr[i].length ; j ++) {
                arr[i][j] = counter++;
            }
        }
    }


    public static void printArray(int[][] arr) {
        for(int i = 0 ; i < arr.length; i++) {
            System.out.print("[");
            for(int j = 0; j < arr[i].length; j ++) {
                System.out.printf(" %3d ", arr[i][j]);

            }
            System.out.println("]");

        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj n: ");
        int n = scanner.nextInt();
        int[][] arr = createArray(n);
        fillArray(arr);
        printArray(arr);

    }

}
