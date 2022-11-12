package Prog;

import Wielomian.Wielomian;

public class Main {

	public static void main(String[] args) {
		int x = 5;

		for(int i =0; i < 10; i ++) {
			System.out.println("Wartosc wielomianu dla n = "+ i+": " + Wielomian.Czebyszew(x, i));
			
		}
	}
	
}
