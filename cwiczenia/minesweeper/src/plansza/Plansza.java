package plansza;

import java.util.Random;
public class Plansza {


    enum Stan {
        Odkryta, Zflagowana, Zakryta,
    }

    private final double chance = 0.1;
    private boolean[][] pola;
    private int[][] near;
    private boolean[][] widoczne;

    private Plansza() {}
        public int donear(int x, int y){
            int temp = 0;
            for(int i=-1; i<=1; i++) {
                for(int j=-1; j<=1; j++) {
                    if(pola[ x+i ][ y+j ]) {
                        temp++;
                    }
                }
            }
            return temp;
        }

        public void GenerateNear(){
            for(int i=1; i<pola.length; i++) {
                for(int j=1; j<pola.length; j++) {
                    near[i][j] = donear(i, j);
                }
            }
        }

        public Plansza(int m, int n) {
            this.pola = new boolean[n][m];
            this.near = new int [n][m];
            this.widoczne = new boolean[n][m];
            //this.GenerateNear();
        }
        public void Drukowanie() {
            for (int i = 0; i < pola.length; i++) {
                for (int j = 0; j < pola.length; j++) {
                    if (i == 0 || i == pola.length || j == -0 || j == pola[0].length) {
                        System.out.print("#");
                    } else {
                        System.out.print(this.pola[i][j] ? "o" : "x");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }


        public void init(int bombCount) {
            Random generator = new Random();
            for (int i = 0; i < bombCount; i++)
            {
                int n,m = 0;
                do
                {
                    n = generator.nextInt(this.getN());
                    m = generator.nextInt(this.getM());

                }
                while (this.getCell(m,n) == true);
                this.setCell(m,n, true);
            }

    }

        public int getM() {
            return this.pola.length;
        }
        public int getN() {
            return this.pola[0].length;
        }
        public boolean getCell(int m, int n) {
            return this.pola[m][n];
        }
        public void setCell(int m, int n, boolean state) {
            this.pola[m][n] = state;
        }
    }

