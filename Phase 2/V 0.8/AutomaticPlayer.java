<<<<<<< HEAD

public class AutomaticPlayer {
    public static double totalScore;
    private static double averageScore;

    private static int gameAmount = 100;

    public static void main(String[] args) {
        double[] newWeights = {10,30,3,-3000,0};
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
        BotPentis.playGames(gameAmount);
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
=======

public class AutomaticPlayer {
    public static double totalScore;
    private static double averageScore;

    static int gameAmount = 1000;

     // GA variables
     static int populationSize = 10;
     static double mutationRate = 0.2;
     static int selectionAmount = 2;

    public static void main(String[] args) {
        double[] newWeights = { 100, 10, 30, -100, 30, -50, -100 };

        System.out.println("..............................................................");

        // INITIALISE
        Individual[] population = new Individual[populationSize];
        population = GeneticAlgorithms.initialisePopulation(population.length);

       
        
        for (int i = 0 ; i < population.length ; i++) {

             // SELECTION
            population = GeneticAlgorithms.selectPop(population, selectionAmount);

            // CROSSOVER
            population = GeneticAlgorithms.crossover(population, populationSize);

            // MUTATION
            population = GeneticAlgorithms.mutation(population, mutationRate);
            
            // PLAY GAMES

            for (int j = 0 ; j < population[0].weights.length ; j++) {
                playGames(population[i].weights, gameAmount);
            }
            
        }

        //playGames(newWeights, gameAmount);
        
    }

    /**
     *
     * @param weights
     * @param gameAmount
     * @return the average score this bot achieved over that many games
     */
    public static double playGames(double[] weights, int gameAmount) {
        Bot.setWeights(weights);

        //System.out.println("Game playing");
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
>>>>>>> 8801ab492abbdd3ad941c90274520c131974e560
