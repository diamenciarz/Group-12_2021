public class FutureMapState {
    public int[][] mapState;
    private double error;
    public int deltaXPosition;
    public int rotateTimes;
    private double[] weights;

    private boolean doDebugMessages = false;

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

        double[] separateScores = new double[7];

        int[][] mapStateCopy = HelperMethods.getACopyOfThisMatrix(mapState);
        int[][] mapStateWithDeletedRows = HelperMethods.deleteRows(mapStateCopy, false);

        EnclosedAreasD d = new EnclosedAreasD();
        // I'm using two different map states as input on purpose
        // Some methods benefit from seeing the map state before rows were deleted
        // While other benefit from seeing the final map state
        separateScores[0] = FitnessTests.towerHeight(mapStateWithDeletedRows);
        separateScores[1] = d.countRoofs(mapStateWithDeletedRows);
        separateScores[2] = d.countEnclosedAreas(mapStateWithDeletedRows);
        separateScores[3] = HelperMethods.getDeletedRows(mapState);
        separateScores[4] = d.countBlocksNextToWalls(mapStateWithDeletedRows);
        separateScores[5] = d.countConnectedBlocks(mapState);
        separateScores[6] = d.countConnectionsBetweenBlocks(mapState);
        if (doDebugMessages) {
            HelperMethods.printMatrixContentsInChatUsingLetters(mapState);
            System.out.println("tower height: " + separateScores[0]);
            System.out.println("roofs: " + separateScores[1]);
            System.out.println("enclosed areas: " + separateScores[2]);
            System.out.println("deleted rows: " + separateScores[3]);
            System.out.println("blocks next to walls: " + separateScores[4]);
            System.out.println("connected blocks: " + separateScores[5]);
            System.out.println("block connections: " + separateScores[6]);
        }
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
