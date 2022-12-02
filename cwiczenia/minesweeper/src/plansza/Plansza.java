package plansza;

import java.util.*;

public class Plansza {

    private Pole[][] pola;
    private Difficulty selectedDifficulty;

    private int revealedFieldsCount = 0;

    public Plansza() {
        this.selectedDifficulty = Difficulty.difficulties[0];
        this.pola = createPola(this.selectedDifficulty.getM(), this.selectedDifficulty.getN());

    }


    private int calcNear(int x, int y){
        int count = 0;
        for(int i = Math.max(x - 1, 0); i <= Math.min(x + 1, pola.length - 1); i++) {
            for(int j = Math.max(y - 1, 0); j <= Math.min(y + 1, pola[i].length - 1); j++) {
                count += pola[i][j].getIsBomb() ? 1 : 0;
            }
        }
        return count;
    }

    private void calculateNearBombs(){
        for(int i=0; i<pola.length; i++) {
            for(int j=0; j<pola[i].length; j++) {
                pola[i][j].setNearBombsCount(calcNear(i, j));

            }
        }
    }
    private Pole[][] createPola(int m, int n) {
        Pole[][] p = new Pole[m][n];

        for(int i = 0 ; i < m; i ++) {
            for(int j = 0 ; j < n ;j ++) {
                p[i][j] = new Pole(i,j);
            }
        }
        return p;

    }


    public void Drukowanie() {
        for (int i = -1; i <= pola.length; i++) {
            for (int j = -1; j <= pola[0].length; j++) {
                if (i == -1 || i == pola.length || j == -1 || j == pola[0].length) {  //współrzędne muszą się zgadzać (pierwsza to x, druga y)
                    System.out.print("#");       //pole krawędziowe (niewyświetlane)
                } else {
                    System.out.print(pola[i][j].getIsBomb() ? "o" : "x");
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

    public int getRevealedFieldsCount() {
        return this.revealedFieldsCount;
    }

    public Optional<Pole> placeFlag(Pole pole) {

        if(pole.getState() != Stan.Zakryta) {
            return Optional.empty();
        }
        pole.setState(Stan.Zflagowana);
        return Optional.of(pole);
    }
    public Optional<Pole> removeFlag(Pole pole) {
        if(pole.getState() != Stan.Zflagowana) {
            return Optional.empty();
        }
        pole.setState(Stan.Zakryta);
        return Optional.of(pole);
    }
    public Optional<Pole> toggleFlag(Pole pole) {
        if(pole.getState() == Stan.Zakryta) {
            return placeFlag(pole);
        }
        else {
            return removeFlag(pole);
        }
    }
    // return optional of all affected fields
    public Optional<Pole[]> revealField(Pole pole) {
        if(pole.getState() != Stan.Zakryta) {
            return Optional.empty();
        }

        // if bomb is hit, game over
        if(pole.getIsBomb()) {
            Pole[] affectedFields = Arrays.stream(this.pola).flatMap(Arrays::stream).toArray(Pole[]::new);
            for (Pole field: affectedFields) {
                field.setState(Stan.Odkryta);
            }
            return Optional.of(affectedFields);
        }
        // if its first revealed field, then generate bombs
        if(this.revealedFieldsCount == 0) {
            this.placeBombs(pole.getM(), pole.getN());
        }
        Pole[] cascadedFields = this.cascadeFields(pole).toArray(Pole[]::new);
        revealedFieldsCount += cascadedFields.length;

        return Optional.of(cascadedFields);


    }

    public Difficulty getSelectedDifficulty() {
        return this.selectedDifficulty;
    }

    private void placeBombs(int x, int y) {
        Random generator = new Random();
        int bombCount = this.selectedDifficulty.getBombsCount();

        for (int i = 0; i < bombCount; i++)
        {
            int n,m;
            do
            {
                n = generator.nextInt(this.getN());
                m = generator.nextInt(this.getM());
            }
            // don't place bombs in 3x3 rectangle near field at x,y
            while (
                    (m >= Math.max(x - 1, 0)
                            && m <= Math.min(x + 1, pola.length)
                            && n >= Math.max(y - 1 , 0)
                            && n <= Math.min(y + 1, pola[0].length - 1))
                    || this.getField(m,n).getIsBomb());
            pola[m][n].setIsBomb(true);
        }
        this.calculateNearBombs();

    }


    public void init( Difficulty trudnosc) {
        this.selectedDifficulty = trudnosc;
        this.pola = createPola(trudnosc.getM(), trudnosc.getN());
        this.revealedFieldsCount = 0;

    }

    private Set<Pole> cascadeFields(Pole pole) {
        Set<Pole> cascadedFields = new HashSet<>();

        Stack<Pole> fieldsToVisit = new Stack<>();
        fieldsToVisit.push(pole);

        while(!fieldsToVisit.empty()) {
            Pole f = fieldsToVisit.pop();

            if(cascadedFields.add(f)){
                f.setState(Stan.Odkryta);
            }

            if(f.getNearBombsCount() != 0) {
                continue;
            }

            int m = f.getM();
            int n = f.getN();

            for(int i = Math.max(m - 1, 0); i <= Math.min(m + 1, pola.length - 1); i++) {
                for(int j = Math.max(n - 1, 0); j <= Math.min(n + 1, pola[0].length - 1); j++) {
                    Pole adj = pola[i][j];
                    if(adj.getState() != Stan.Odkryta) {
                        fieldsToVisit.push(adj);
                    }
                }
            }
        }


        return cascadedFields;
    }

    public int Odkrywanie(int x, int y, boolean first) {
        if (first) {
            while (pola[x][y].getIsBomb()) {

            }
        }

        if (x < 0 || x >= pola.length || y < 0 || y >= pola[0].length) {
            return 0;
        }
        pola[x][y].setState(Stan.Odkryta);
        if (pola[x][y].getNearBombsCount() == 0) {
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

