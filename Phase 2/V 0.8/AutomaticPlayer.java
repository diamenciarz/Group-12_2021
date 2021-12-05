
public class AutomaticPlayer {
    public static double totalScore;
    private static double averageScore;

    static int gameAmount = 1000;

    public static void main(String[] args) {
        double[] newWeights = { 100, 10, 30, -100, 30, -50, -100 };
        playGames(newWeights, gameAmount);
    }

    /**
     *
     * @param weights
     * @param gameAmount
     * @return the average score this bot achieved over that many games
     */
    public static double playGames(double[] weights, int gameAmount) {
        Bot.setWeights(weights);

        System.out.println("Game playing");
        BotPentis.playGames(gameAmount, false);
        try {
            countAverageScore();
            printScore();
            return averageScore;
        } catch (Exception e) {
            System.err.println("Caught exception");
        }
        return 0;
    }

    public static void printScore() {
        System.out.println("Average score: " + (totalScore / gameAmount));
    }

    public static void addScore(double addScore) {
        totalScore += addScore;
    }

    private static double countAverageScore() {
        averageScore = totalScore / gameAmount;
        return averageScore;
    }
}
