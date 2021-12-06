import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Random;

public class GABotTrainer {

    // Changeable variables
    static int populationSize = 20;
    static double mutationRate = 0.2;
    static int selectionAmount = 5;
    private static int runs = 50;
    static double mutationRange = 30;

    public static void main(String[] args) throws IOException {


        // text file
        FileWriter writer = new FileWriter("fittest2.txt");
		PrintWriter out = new PrintWriter(writer);

        // Arralist
        ArrayList<Individual> fittestList = new ArrayList<Individual>();

        // empty pop array
        Individual[] population = new Individual[populationSize];

        System.out.println("..............................................................");

        // INITIALISE
        population = GAHelperMethods.initialisePopulation(populationSize);
        //population = GAHelperMethods.crossover(population, populationSize);
        // population = GAHelperMethods.mutation(population, mutationRate, mutationRange);
        // for (int ind = 0 ; ind < population.length ; ind++) {
        //     population[ind].setFitness(AutomaticPlayer.playGames(population[ind].weights, AutomaticPlayer.gameAmount));
        // }

        //System.out.println("\nInital population");
        //printPopulation(population);

        for (int i = 0 ; i < runs ; i++) {

            // WHAT DOES MY POP LOOK LIKE
            System.out.println("\nGen "+ i);
            // printPopulation(population);

            if (i == 0 ) {
                population = GAHelperMethods.mutation(population, mutationRate, mutationRange);
                for (int ind = 0 ; ind < population.length ; ind++) {
                    population[ind].setFitness(AutomaticPlayer.playGames(population[ind].weights, AutomaticPlayer.gameAmount));
                }
                 // Sorting pop
                HeapSort.sort(population);
            } else {

                // SELECTION
            Individual[] theSelected = new Individual[selectionAmount];
            theSelected = GAHelperMethods.selectPop(population, selectionAmount);
            System.out.println("\nSelection");
            printPopulation(theSelected);

            // CROSSOVER
            population = GAHelperMethods.crossover(theSelected, populationSize);
            System.out.println("\nCrossover");
            printPopulation(population);

             // MUTATION
             population = GAHelperMethods.mutation(population, mutationRate, mutationRange);
             System.out.println("\nMutation");
             printPopulation(population);

            for (int ind = 0 ; ind < population.length ; ind++) {
                population[ind].setFitness(AutomaticPlayer.playGames(population[ind].weights, AutomaticPlayer.gameAmount));
            }

            // Sorting pop
            HeapSort.sort(population);

            }

            fittestList.add(population[0]);

            out.println("Fittest individual of gen " + i);
            out.println(Arrays.toString(population[0].getWeights()) + "\t" + population[0].getFitness());
            
            System.out.println("Fittest individual of gen " + i);
            System.out.println(Arrays.toString(population[0].getWeights()) + "\t" + population[0].getFitness());
        }

        writer.close();

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
