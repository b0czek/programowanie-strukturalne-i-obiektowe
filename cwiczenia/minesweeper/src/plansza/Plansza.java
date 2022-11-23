package plansza;

import java.util.Random;
public class Plansza {

    private Pole[][] pola;
    private Difficulty selectedDifficulty;
    public int donear(int x, int y){
        int temp = 0;
        for(int i=-1; i<=1; i++) {
            for(int j=-1; j<=1; j++) {
                if(pola[ x+i ][ y+j ].getBomb()) {
                    temp++;
                }
            }
        }
        return temp;
    }

    public void GenerateNear(){
        for(int i=1; i<pola.length-1; i++) {
            for(int j=1; j<pola.length-1; j++) {
                pola[i][j].setState(Stan.Zakryta);
                pola[i][j].setLiczba_bomb(donear(i, j));

            }
        }
    }
    private Pole[][] createPola(int m, int n) {
        Pole[][] p = new Pole[m][n];
        for(int i = 0 ; i < m; i ++) {
            for(int j = 0 ; j < n ;j ++) {
                p[i][j] = new Pole();
            }
        }
        return p;

    }
    public Plansza() {
        this.selectedDifficulty = Difficulty.difficulties[0];
        this.pola = createPola(this.selectedDifficulty.getM(),this.selectedDifficulty.getN());
        //this.GenerateNear();
    }

    public void Drukowanie() {
        for (int i = 0; i < pola.length; i++) {
            for (int j = 0; j < pola.length; j++) {
                if (i == 0 || i == pola[0].length || j == 0 || j == pola.length) {  //współrzędne muszą się zgadzać (pierwsza to x, druga y)
                    System.out.print("#");       //pole krawędziowe (niewyświetlane)
                } else {
                    System.out.print(pola[j][i].getBomb() ? "o" : "x");
                }
            }
            System.out.println();
        }
        System.out.println();
    }



    public int getM() {
        return this.pola.length;
    }
    public int getN() {
        return this.pola[0].length;
    }
    public Pole getField(int m, int n) {
        return this.pola[m][n];
    }
    public void setCell(int m, int n, Stan state) {
        this.pola[m][n].setState(state);
    }

    public Difficulty getSelectedDifficulty() {
        return this.selectedDifficulty;
    }


    public void init( Difficulty trudnosc) {
        this.selectedDifficulty = trudnosc;
        this.pola = createPola(trudnosc.getM(), trudnosc.getN());
        Random generator = new Random();
        int bombCount = trudnosc.getBombsCount();

        for (int i = 0; i < bombCount; i++)
        {
            int n,m = 0;
            do
            {
                n = generator.nextInt(this.getN());
                m = generator.nextInt(this.getM());
                System.out.println(m +" "+ n);
            }
            while (this.getField(m,n).getBomb() == true);
            pola[m][n].setBomb(true);
        }
//        GenerateNear();
    }

    public int Odkrywanie(int x, int y, boolean first) {
        if (first) {
            while (pola[x][y].getBomb()) {

            }
        }

        if (x < 0 || x >= pola.length || y < 0 || y >= pola[0].length) {
            return 0;
        }
        pola[x][y].setState(Stan.Odkryta);
        if (pola[x][y].getLiczba_bomb() == 0) {
            if (y > 0 && pola[x][y - 1].getState() != Stan.Odkryta) {
                Odkrywanie(x, y - 1, false);
            }
            if (y < pola[0].length - 1 && pola[x][y + 1].getState() != Stan.Odkryta) {
                Odkrywanie(x, y + 1, false);
            }
            if (x > 0 && pola[x - 1][y].getState() != Stan.Odkryta) {
                Odkrywanie(x - 1, y, false);
            }
            if (x < pola.length - 1 && pola[x + 1][y].getState() != Stan.Odkryta) {
                Odkrywanie(x + 1, y, false);
            }
        }
        return 0;
    }
}

