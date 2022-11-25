package plansza;

public class Difficulty {
    private int m,n,bombsCount;
    private String difficultyName;

    public Difficulty(String difficultyName, int m, int n, int bombsCount) {
        this.m = m;
        this.n = n;
        this.bombsCount = bombsCount;
        this.difficultyName = difficultyName;
    }

    public int getM(){
        return this.m;
    }

    public int getN(){
        return this.n;
    }
    public int getBombsCount(){
        return this.bombsCount;
    }
    public String getDifficultyName(){
        return this.difficultyName;
    }

    public static Difficulty[] difficulties = {
            new Difficulty("Poczatkujacy", 8,8, 10),
            new Difficulty("Zaawansowany", 16,16, 40),
            new Difficulty("Ekspert", 30,16, 99),

    };
}
