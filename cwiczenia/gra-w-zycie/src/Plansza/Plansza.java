package Plansza;

import java.util.Random;

public class Plansza {
    private final double chance = 0.2;
    private final int resolution = 10;


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ALIVE_CELL = ANSI_YELLOW + "o" + ANSI_RESET;

    private boolean[][] pola;

    private Plansza() {}
    public Plansza(int m, int n) {
        // [y][x]
        this.pola = new boolean[n][m];
    }
    public void init() {
        Random generator = new Random();
        for (int i = 0; i < pola.length; i++) {
            for (int j = 0; j < pola[i].length; j++) {
                int random = generator.nextInt(this.resolution);

                this.pola[i][j] = ((double) random / this.resolution) < this.chance;
            }

        }
    }
    public void Drukowanie()
    {

        for ( int i=-1; i < pola.length + 1; i++ )
        {
            for (int j = -1; j < pola[0].length + 1; j++ )
            {
                if( i == -1 || i == pola.length  || j == -1 || j == pola[0].length )
                {
                    System.out.print("#");
                }

                else {
                    System.out.print(this.pola[i][j] ? ALIVE_CELL : "x");
                }
            }
            System.out.println();
        }
        System.out.println();

    }


    public void tick() {
        boolean[][] future = this.pola.clone();
        for(int i = 0 ; i < pola.length; i++) {
            for(int j = 0; j < pola[i].length; j ++) {
                    int aliveNeighbours = Komorka.getAliveNeighbours(this, i,j);
                    boolean cell = this.pola[i][j];
                    if(cell) {
                        if(aliveNeighbours != 2 && aliveNeighbours != 3) {
                            future[i][j] = false;
                        }
                    }
                    else if(aliveNeighbours == 3) {
                        future[i][j] = true;
                    }
                }
            }
        this.pola = future;
        }



    public boolean[][] getPola() {
        return this.pola;
    }
}


