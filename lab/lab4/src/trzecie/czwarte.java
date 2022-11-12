package trzecie;
import java.util.Scanner;

public class Main {

	static double S(int n, double x) {
		if(x < -1) {
			return 2f/3f;
		}
		else if(x >= -1 && x <= 1) {
			return Math.sin(x) + x*x;
		}
		else {
			double suma = 0;
			long silnia = 1;
			for(int i = 1; i <= n; i++) {
				silnia *= i;
				double el = ((x+i)/silnia);
				suma += el;
			}
			return suma;
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("n: ");
		int n = in.nextInt();

		System.out.println("x: ");
		double x = in.nextDouble();
		System.out.println(S(n,x));
		
	}

}
