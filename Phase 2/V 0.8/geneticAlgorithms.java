import java.util.Random;
/**
 * This file contains:
 * 1. a method to initialise a poputlation of Individuals
 * 2. a method to select Individuals // yet to be done
 * 3. a method to crossover selected Individuals
 * 4. a method to mutated the crossed-over Individuals
 */
public class geneticAlgorithms {

    /**
     * Initialise population
     * @return population as Individual[]
     */
    static Individual[] initialisePopulation(int populationSize) {
        // population of individuals
        Individual[] population = new Individual[populationSize];

        // random variable and maximum double value 
        Random random = new Random();
        double maximumDouble = Double.MAX_VALUE;

        // Initialising the population with random double values (+- maximum double value)
		for (int i = 0; i < populationSize; i++) {
			double[] weights = new double[5];
			for (int j = 0; j < weights.length; j++) {
                if (random.nextBoolean()) {
                    weights[j] = random.nextDouble()*maximumDouble; //random double positive
                } else {
                    weights[j] = -random.nextDouble()*maximumDouble; //random double negative
                }
			}
			population[i] = new Individual(weights);
            setFitness(population[i]);
		}

        return population;
    }




    /**
     * Temporary method to give each individual a random fitness score
     * @param Individual
     */
    public static void setFitness(Individual Individual) {
        Random random = new Random();

        int randomScore = random.nextInt(20);
        // double averageScore = stansMethod (Individual.weights, int games)

        Individual.setFitness(randomScore);
    
    } 



    /**
     * Crossover method
     * @param selectedPopulation the selected individuals as input
     * @return crossed over individuals as Individual[]
     */
    public static Individual[] crossover(Individual[] selectedPopulation){
        
        Random random = new Random();
    
        Individual[] crossedoverPop= new Individual[GAtest.populationSize];
    
        for(int i = 0 ; i < GAtest.populationSize ; i++){
    
            int Parent1=random.nextInt(selectedPopulation.length);
            int Parent2=random.nextInt(selectedPopulation.length);
    
            while(Parent1==Parent2){
                Parent2=random.nextInt(selectedPopulation.length);
            }
    
            Individual child= selectedPopulation[Parent1];
            Individual child2= selectedPopulation[Parent2];
           
            int genome=random.nextInt(3);
    
            for(int j=0; j<5;j++ ){
    
                if(j>genome){
    
                    child.weights[j]=child2.weights[j];
      
                }
            } 
        
            crossedoverPop[i]=child;
    
        }
        return crossedoverPop;
    
        }


    /**
     * Mutation method
     * @param newPopulation the population that should have been crossed over as input
     * @return mutated individuals as Individual[]
     */
    public static  Individual[] mutation(Individual[] newPopulation, double mutationRate){ 

        Random random = new Random();
        double maximumDouble = Double.MAX_VALUE;
        
        Individual[] mutatedpopulation = newPopulation;

        for (int i = 0; i < GAtest.populationSize; i++){

            for( int j=0; j<5; j++){
                //double mutationrate=0.5;
			    //double[] tempWeights = new double[5];

                if(Math.random()<= mutationRate){
                    if (random.nextBoolean()) {
                        mutatedpopulation[i].weights[j] = random.nextDouble()*maximumDouble; //random double positive
                    } else {
                        mutatedpopulation[i].weights[j] = -random.nextDouble()*maximumDouble; //random double negative
                    }
                }
		    }
        }

        return mutatedpopulation;
    }


    
}
