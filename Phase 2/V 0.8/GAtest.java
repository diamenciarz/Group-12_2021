import java.util.Arrays;
//import java.util.Random;

public class GAtest {

    // Changeable variables
    static int populationSize = 10;
    static double mutationRate = 0.2;
    static int selectionAmount = 5;

    public static void main(String[] args) {

        // empty pop array
        Individual[] population = new Individual[populationSize];

        System.out.println("..............................................................");

        // INITIALISE
        population = GeneticAlgorithms.initialisePopulation(populationSize);
        //System.out.println("\nInital population");
        //printPopulation(population);

        // SELECTION
        population = GeneticAlgorithms.selectPop(population, selectionAmount);
        //System.out.println("\nSelection");
        //printPopulation(population);

        // CROSSOVER
        population = GeneticAlgorithms.crossover(population, populationSize);
        //System.out.println("\nCrossover");
        //printPopulation(population);

        // MUTATION
        population = GeneticAlgorithms.mutation(population, mutationRate);
        //System.out.println("\nMutation");
        //printPopulation(population);

    }

    /**
     * Printing out population
     * @param population Individual[]
     */
    private static void printPopulation(Individual[] population) {
        // Printing out population
        System.out.println();
		for (int i = 0; i < population.length; i++) {
			System.out.println(Arrays.toString(population[i].getWeights()) + "\t" + population[i].getFitness());
		}
    }

    
}
