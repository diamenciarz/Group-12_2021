<<<<<<< HEAD
<<<<<<< HEAD
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
=======
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
>>>>>>> d158b094497fd2b9a6a8ebd45edec8b27ff7387d
=======
import java.util.Random;
/**
 * This file contains:
 * 1. a method to initialise a poputlation of Individuals
 * 2. a method to select Individuals 
 * 3. a method to crossover selected Individuals
 * 4. a method to mutated the crossed-over Individuals
 */
public class GeneticAlgorithms {

    /**
     * Initialise population
     * @return population as Individual[]
     */
    static Individual[] initialisePopulation(int populationSize) {

        System.out.println("Population size: " + populationSize);

        // number of weights
        int numberOfWeights = 7;

        // population of individuals
        Individual[] population = new Individual[populationSize];

        // random variable and maximum double value 
        Random random = new Random();
        double maximumDouble = Double.MAX_VALUE;

        // Initialising the population with random double values (+- maximum double value)
		for (int i = 0; i < populationSize; i++) {
			double[] weights = new double[numberOfWeights];
			for (int j = 0; j < weights.length; j++) {
                if (random.nextBoolean()) {
                    weights[j] = random.nextDouble()*maximumDouble; //random double positive
                } else {
                    weights[j] = -random.nextDouble()*maximumDouble; //random double negative
                }
			}
			population[i] = new Individual(weights);
            
            // temporary random fitness
            //population[i].setFitness(random.nextInt(20));

            // Setting fitness
            population[i].setFitness(AutomaticPlayer.playGames(population[i].weights, 1));
            
		}

        return population;
    }


    /**
     * Selection method
     * @param i input population Individual[]
     * @param amount how many individuals
     * @return survived individuals
     */
    public static Individual[] selectPop(Individual[] i, int amount){
        
        Individual[] survivors = new Individual[amount];
        HeapSort.sort(i);
        for (int j = 0; j < amount; j++) {
            survivors[j] = i[j];
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

                //System.out.println("p1: " + p1Selection);
                //System.out.println("p2: " + p2Selection);
            
                int genome = random.nextInt(p1.weights.length); //0,1,2,3,4
                //System.out.println(genome);
        
                // copy from parent 1
                Individual child = p1;
                //System.out.println("child: " + Arrays.toString(child.weights));

                // parent 1 material to offspring
                // for (int j = 0 ; j < genome ; i++) {
                //     child.weights[j] = p1.weights.length ;
                // }

                // parent 2 material to offspring
                for (int j = genome ; j < p2.weights.length ; j++) { // 10<11
                    child.weights[j] = p2.weights[j];
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
    public static  Individual[] mutation(Individual[] newPopulation, double mutationRate){ 

        Random random = new Random();
        double maximumDouble = Double.MAX_VALUE;
        
        Individual[] mutatedpopulation = newPopulation;

        for (int i = 0; i < newPopulation.length; i++){

            for( int j = 0 ; j < mutatedpopulation[0].weights.length ; j++){
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
>>>>>>> 8801ab492abbdd3ad941c90274520c131974e560
