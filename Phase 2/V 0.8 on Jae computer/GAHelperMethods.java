import java.util.Random;
/**
 * This file contains:
 * 1. a method to initialise a poputlation of Individuals
 * 2. a method to select Individuals 
 * 3. a method to crossover selected Individuals
 * 4. a method to mutated the crossed-over Individuals
 */
public class GAHelperMethods {

    /**
     * Initialise population
     * @return population as Individual[]
     */
    static Individual[] initialisePopulation(int populationSize) {

        System.out.println("Population size: " + populationSize);

        // number of weights
        //int numberOfWeights = Bot.weights.length;

        // population of individuals
        Individual[] population = new Individual[populationSize];

        // random variable and maximum double value 
        Random random = new Random();
        double maximumDouble = Double.MAX_VALUE;
        //double maximumDouble = 100.0;

        // Initialising the population with initial weights
		for (int i = 0; i < populationSize; i++) {
			//double[] weights = new double[Bot.weights.length];
            double[] newWeights = { 84.10121375849604, 86.8366044121229, 20.267241278251184, -125.82869475777727, -87.02514848624804, -21.456272600441245, -133.80621677127533 };

			// for (int j = 0; j < weights.length; j++) {
            //     if (random.nextBoolean()) {
            //         weights[j] = random.nextDouble()*maximumDouble; //random double positive
            //     } else {
            //         weights[j] = -random.nextDouble()*maximumDouble; //random double negative
            //     }
			// }
			population[i] = new Individual(newWeights);
            
            // temporary random fitness
            //population[i].setFitness(random.nextInt(20));

            // Setting fitness
            //population[i].setFitness(AutomaticPlayer.playGames(population[i].weights, 1));
            
		}

        return population;
    }


    /**
     * Selection method
     * @param population input population Individual[]
     * @param amount how many individuals
     * @return survived individuals
     */
    public static Individual[] selectPop(Individual[] population, int amount){

        Individual[] survivors = new Individual[amount];

        HeapSort.sort(population);
        
        for (int j = 0; j < amount; j++) {
            survivors[j] = population[j];
        }
        return survivors;
    }



  



    /**
     * Crossover method
     * @param selectedPopulation the selected individuals as input
     * @return crossed over individuals as Individual[]
     */
    public static Individual[] crossover(Individual[] selectedPopulation, int popSize){
        
        Random random = new Random();
    
        Individual[] crossedoverPop= new Individual[popSize];
    
            for(int i = 0 ; i < popSize ; i++){
        
                int p1Selection = random.nextInt(selectedPopulation.length);
                int p2Selection = random.nextInt(selectedPopulation.length);
                while(p1Selection == p2Selection){
                    p2Selection = random.nextInt(selectedPopulation.length);
                }
        
                Individual p1 = selectedPopulation[p1Selection];
                Individual p2 = selectedPopulation[p2Selection];

                // System.out.println("p1: " + p1Selection);
                // System.out.println("p2: " + p2Selection);
            
                //int genome = random.nextInt(p1.weights.length); //0,1,2,3,4
                //System.out.println(genome);
        
                // copy from parent 1
                Individual child = new Individual(HelperMethods.getACopyOfThisArray(p1.weights));
                //System.out.println("child: " + Arrays.toString(child.weights));

                // parent 1 material to offspring
                // for (int j = 0 ; j < genome ; i++) {
                //     child.weights[j] = p1.weights.length ;
                // }

                // parent 2 material to offspring
                for (int j = 0 ; j < p2.weights.length ; j++) { // 10<11
                    if (random.nextBoolean()) {
                        child.weights[j] = p2.weights[j];
                    }
                    
                }

                // for(int j = 0; j < 5 ; j++){
                //     if(j>=genome){
        
                //         child.weights[j]=child2.weights[j];
                //     }
                // } 
                crossedoverPop[i] = child;
        
            }
            return crossedoverPop;
    
        }


    /**
     * Mutation method
     * @param newPopulation the population that should have been crossed over as input
     * @return mutated individuals as Individual[]
     */
    public static  Individual[] mutation(Individual[] newPopulation, double mutationRate, double mutationRange){ 

        Random random = new Random();
       // double maximumDouble = 0;
        
        Individual[] mutatedpopulation = newPopulation;

        for (int i = 0; i < newPopulation.length; i++){

            for( int j = 0 ; j < mutatedpopulation[0].weights.length ; j++){
                //double mutationrate=0.5;
			    //double[] tempWeights = new double[5];

                if(Math.random()<= mutationRate){
                    mutatedpopulation[i].weights[j] += mutationRange *(random.nextDouble() * 2 - 1);
                }
		    }
        }

        return mutatedpopulation;
    }


    
}
