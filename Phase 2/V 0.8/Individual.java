public class Individual {
      
    double[] weights;
    double fitness;

    public Individual(double[] weights) {
        this.weights = weights;
        this.fitness = 0;
    }

    public void setFitness(double fitness) {
		this.fitness = fitness;
	}

    public double getFitness() {
		return fitness;
	}

    public void setWeights(double[] weights) {
		this.weights = weights;
	}

    public double[] getWeights() {
		return weights;
	}

    public Individual clone() {
		double[] clone = new double[weights.length];
		for(int i = 0; i < weights.length; i++) {
			clone[i] = weights[i];
		}
		return new Individual(clone);
	}
    
}
