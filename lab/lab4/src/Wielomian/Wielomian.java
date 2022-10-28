package Wielomian;

public class Wielomian {
	public static long Czebyszew(int x, int n) {
		if(n == 0) {
			return 1;
			
		}
		long minus2 = 1;
		long minus1 = x;
		for(int i = 2; i <= n; i++) {
			long j = (2 * x * minus1) - minus2;
			minus2 = minus1;
			minus1 = j;
		}	
		return minus1;
		
	}
}
