package Plansza;

public class Komorka {
    public static Pair[] getNeighbours(Plansza p, int m, int n) {
        Pair[] pairs = new Pair[8];
        int c = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                boolean[][] pola = p.getPola();

                int x = (pola.length + m + i) % pola.length;
                int y = (pola[0].length + n + j) % pola[0].length;
                pairs[c] = new Pair(x,y);
                c++;
            }
        }
        return pairs;
    }

    public static int getAliveNeighbours(Plansza p, int m, int n) {
        int alive = 0;
        Pair[] x = getNeighbours(p, m, n);
        boolean[][] pola = p.getPola();
        for (int i = 0; i < x.length; i++) {
            Pair coord = x[i];
            if (pola[coord.m][coord.n]) {
                alive += 1;

            }

        }
        return alive;
    }

}