public class FutureMapState {
    public int[][] mapState;
    private double error;
    public int deltaXPosition;
    public int rotateTimes;
    private double[] weights;

    public double getMoveError(int[][] newMapState) {
        mapState = newMapState;
        countMoveError();

        return error;
    }

    public double getMoveError() {
        return error;
    }

    public void setMapState(int[][] newMapState, double[] newWeights) {
        mapState = newMapState;
        weights = newWeights.clone();
        countMoveError();
    }

    private void countMoveError() {
        error = countOutput(getErrorArray());
    }

    private double[] getErrorArray() {

        double[] separateScores = new double[4];

        separateScores[0] = FitnessTests.towerHeight(mapState);
        separateScores[1] = FitnessTests.roofMethod(mapState, 0);
        separateScores[2] = FitnessTests.roofMethod(mapState, 1);
        separateScores[3] = FitnessTests.roofMethod(mapState, 2);
        return separateScores;
    }

    private double countOutput(double[] inputsArray) {

        double output = 0;
        for (int i = 0; i < weights.length - 2; i++) {
            output += weights[i] * inputsArray[i];
        }

        // Last element is the bias
        output += weights[weights.length - 1];
        return output;
    }
}
