package bowling;

public class Tour {

    protected int premierLancer = -1;
    protected int secondLancer = -1;

    public boolean estStrike() {
        return premierLancer == 10;
    }

    public boolean estSpare() {
        return premierLancer >= 0 && secondLancer >= 0 && premierLancer + secondLancer == 10 && !estStrike();
    }

    public boolean estTermine() {
        return estStrike() || (premierLancer >= 0 && secondLancer >= 0);
    }

    public boolean enregistrerLancer(int quilles) {
        if (premierLancer == -1) {
            premierLancer = quilles;
        } else if (secondLancer == -1) {
            secondLancer = quilles;
        }
        return !estTermine(); // true → le tour continue
    }

    public int scoreDeBase() {
        int s = 0;
        if (premierLancer >= 0)
            s += premierLancer;
        if (secondLancer >= 0)
            s += secondLancer;
        return s;
    }
}
