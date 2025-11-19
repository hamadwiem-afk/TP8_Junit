package bowling;

public class PartieMonoJoueur {

    private final Tour[] tours = new Tour[10];
    private int tourIndex = 0;
    private boolean terminee = false;

    public PartieMonoJoueur() {
        for (int i = 0; i < 9; i++) {
            tours[i] = new Tour();
        }
        tours[9] = new TourFinal();
    }

    public boolean enregistreLancer(int quilles) {
        if (terminee)
            throw new IllegalStateException("Partie terminée !");

        Tour t = tours[tourIndex];
        boolean tourContinue = t.enregistrerLancer(quilles);

        if (!tourContinue) {
            tourIndex++;
            if (tourIndex >= 10)
                terminee = true;
        }

        return tourContinue;
    }

    public int score() {
        int score = 0;

        for (int i = 0; i < 10; i++) {
            Tour t = tours[i];

            if (i < 9) {
                int first = t.premierLancer >= 0 ? t.premierLancer : 0;
                int second = t.secondLancer >= 0 ? t.secondLancer : 0;

                if (t.estStrike()) {
                    int bonus1 = 0, bonus2 = 0;
                    if (i + 1 < 10) {
                        Tour next = tours[i + 1];
                        bonus1 = next.premierLancer >= 0 ? next.premierLancer : 0;
                        if (next.estStrike() && i + 2 < 10) {
                            bonus2 = tours[i + 2].premierLancer >= 0 ? tours[i + 2].premierLancer : 0;
                        } else {
                            bonus2 = next.secondLancer >= 0 ? next.secondLancer : 0;
                        }
                    }
                    score += 10 + bonus1 + bonus2;
                } else if (t.estSpare()) {
                    int bonus = 0;
                    if (i + 1 < 10)
                        bonus = tours[i + 1].premierLancer >= 0 ? tours[i + 1].premierLancer : 0;
                    score += 10 + bonus;
                } else {
                    score += first + second;
                }

            } else if (t instanceof TourFinal tf) {
                int[] lancers = tf.getLancers();
                for (int lancer : lancers) {
                    if (lancer >= 0)
                        score += lancer;
                }
            }
        }

        return score;
    }

    public boolean estTerminee() {
        return terminee;
    }

    public int numeroTourCourant() {
        return terminee ? 0 : tourIndex + 1;
    }

    public int numeroProchainLancer() {
        if (terminee)
            return 0;

        Tour t = tours[tourIndex];
        if (t instanceof TourFinal tf) {
            if (tf.premierLancer == -1)
                return 1;
            if (tf.secondLancer == -1)
                return 2;
            return 3;
        } else {
            if (t.premierLancer == -1)
                return 1;
            return 2;
        }
    }
}
