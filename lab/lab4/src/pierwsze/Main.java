package pierwsze;

import java.util.Scanner;

public class Main {

	public static double trening(int liczbaKrokow, double dlugoscKroku) {
		return liczbaKrokow * dlugoscKroku;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Podaj numer dnia tygodnia: ");
		int n = in.nextInt();
		
		switch(n) {
		case 1:
			System.out.println("poniedzialek");
			break;	
		case 2:
			System.out.println("wtorek");
			System.out.print("Podaj liczbe krokow: ");
			int liczbaKrokow = in.nextInt();
			System.out.print("Podaj dlugosc kroku w metrach: ");
			double dlugoscKroku = in.nextDouble();
			double dystans = trening(liczbaKrokow, dlugoscKroku);
			System.out.printf("Przebyty dystans: %.2fm\n", dystans);
			
			break;
		case 3:
			System.out.println("sroda");
			break;
		case 4:
			System.out.println("czwartek");
			break;
		case 5:
			System.out.println("piatek");
			break;
		case 6:
			System.out.println("sobota");
			break;
		case 7:
			System.out.println("niedziela");			
			break;
		default:
			System.out.println("Nieprawidlowy numer dnia tygodnia");
		}
		
	}

}
