public class AutomaticPlayer {
    public static double totalScore;
    private static double averageScore;

    static int gameAmount = 500;

     // GA variables
     static int populationSize = 10;
     static double mutationRate = 0.2;
     static int selectionAmount = 2;

    public static void main(String[] args) {
        double[] newWeights = { 100, 10, 30, -100, 30, -50, -100 };

        // System.out.println("..............................................................");

        // // INITIALISE
        // Individual[] population = new Individual[populationSize];
        // population = GAHelperMethods.initialisePopulation(population.length);

       
        
        // for (int i = 0 ; i < population.length ; i++) {

        //      // SELECTION
        //     population = GAHelperMethods.selectPop(population, selectionAmount);

        //     // CROSSOVER
        //     population = GAHelperMethods.crossover(population, populationSize);

        //     // MUTATION
        //     population = GAHelperMethods.mutation(population, mutationRate);
            
        //     // PLAY GAMES

        //     for (int j = 0 ; j < population[0].weights.length ; j++) {
        //         playGames(population[i].weights, gameAmount);
        //     }
            
        //}

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
            printScore();
            countAverageScore();
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
        totalScore=0;
        return averageScore;
    }
}